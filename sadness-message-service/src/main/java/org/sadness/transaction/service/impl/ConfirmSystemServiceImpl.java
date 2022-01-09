package org.sadness.transaction.service.impl;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.db.PageResult;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.sadness.transaction.dto.PrepareTimeoutMessageDTO;
import org.sadness.transaction.entity.ConfirmInfo;
import org.sadness.transaction.entity.TransactionMessage;
import org.sadness.transaction.infrastructure.config.TransactionMessageProperties;
import org.sadness.transaction.service.IConfirmLogService;
import org.sadness.transaction.service.IConfirmSystemService;
import org.sadness.transaction.service.ITransactionMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p></p>
 *
 * @author xlp
 * @version 1.0.0
 * @since 2021/12/20 22:24
 */
@Slf4j
@Service
public class ConfirmSystemServiceImpl implements IConfirmSystemService, ApplicationRunner, Runnable {

    @Autowired
    private ITransactionMessageService transactionMessageService;

    @Autowired
    private IConfirmLogService confirmLogService;

    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @Autowired
    private TransactionMessageProperties messageProperties;

    @Override
    public void run(ApplicationArguments args) {
        log.info("startup confirm system service.");
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(this, 5, 60, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        log.debug("Confirm transaction message.");
        PrepareTimeoutMessageDTO param = new PrepareTimeoutMessageDTO();
        param.setPageSize(Math.min(messageProperties.getConfirmBatchSize(), 1000)); //每次确认事务消息最多的数量
        long messageId = 0;
        Boolean confirmResult;
        Set<Long> surpassConfirmTimesMessage = new HashSet<>();
        try {
            PageResult<TransactionMessage> transactionMessages = transactionMessageService.queryPrepareTimeoutMessage(param);
            for (TransactionMessage transactionMessage : transactionMessages) {
                if (transactionMessage.getMessageConfirmTimes() >= messageProperties.getMessageConfirmRetryTimes()) {
                    surpassConfirmTimesMessage.add(transactionMessage.getMessageId());
                    log.trace("Transaction message '{}' surpass confitm times : {}'.", transactionMessage.getMessageId(), transactionMessage.getMessageSendTimes());
                    continue;
                }
                messageId = transactionMessage.getMessageId();
                ConfirmInfo confirmInfo = transactionMessage.getConfirmInfo();
                String url = "http://" + confirmInfo.getServerName() + "/" + confirmInfo.getPath();
                HttpMethod method = HttpMethod.resolve(confirmInfo.getMethod().toUpperCase());
                if (method == null) {
                    AdditionalProcessing(messageId, "回调方法错误：" + confirmInfo.getMethod());
                    return;
                }
                HttpEntity<String> entity = new HttpEntity<>(confirmInfo.getBody(), buildRequestHeaders(confirmInfo.getHeaders()));
                ResponseEntity<Boolean> exchange = restTemplate.exchange(url, method, entity, Boolean.class);
                if (BooleanUtil.isTrue(confirmResult = exchange.getBody()))
                    transactionMessageService.commitTransactionMessage(transactionMessage);
                else if (BooleanUtil.isFalse(confirmResult = exchange.getBody()))
                    transactionMessageService.rollbackTransactionMessage(transactionMessage);
                AdditionalProcessing(messageId, "确认成功:" + (confirmResult == null || !confirmResult ? "ROLLBACK" : "COMMIT"));
            }
        } catch (Exception e) {
            try {
                log.error("确认事务消息异常", e);
                AdditionalProcessing(messageId, e.toString());
            } catch (Exception e1) {
                log.error("保存确认日志异常", e1);
            }
        } finally {
            try {
                //杀死超过confirm次数的消息
                if (!surpassConfirmTimesMessage.isEmpty())
                    transactionMessageService.killMessages(surpassConfirmTimesMessage);
            } catch (Exception e) {
                log.error("杀死超过confirm次数的消息失败", e);
            }
        }
    }

    /**
     * 构建回调请求头。
     *
     * @param headers 请求头字符串
     * @return 请求头
     */
    private MultiValueMap<String, String> buildRequestHeaders(String headers) {
        MultiValueMap<String, String> headerMap = new LinkedMultiValueMap<>();
        if (!StringUtils.isEmpty(headers)) {
            Map<?, ?> map = JSONObject.parseObject(headers, HashMap.class);
            map.forEach((k, v) -> headerMap.put(k + "", v instanceof List ?
                    ((List<?>) v).stream().map(o -> o + "").collect(Collectors.toList()) :
                    Lists.newArrayList(v + "")));
        }
        return headerMap;
    }

    private void AdditionalProcessing(long messageId, String log) {
        confirmLogService.addConfirmLog(messageId, log);
        transactionMessageService.addMessageConfirmTimes(messageId);
    }
}
