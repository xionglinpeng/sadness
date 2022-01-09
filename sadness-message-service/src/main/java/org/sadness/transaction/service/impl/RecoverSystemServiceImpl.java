package org.sadness.transaction.service.impl;

import cn.hutool.db.PageResult;
import org.sadness.transaction.dto.FinishTimeoutMessageDTO;
import org.sadness.transaction.entity.TransactionMessage;
import org.sadness.transaction.infrastructure.config.TransactionMessageProperties;
import org.sadness.transaction.service.IMQMessageService;
import org.sadness.transaction.service.IRecoverSystemService;
import org.sadness.transaction.service.ITransactionMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <p></p>
 *
 * @author xlp
 * @version 1.0.0
 * @since 2021/12/21 21:12
 */
@Slf4j
@Service
public class RecoverSystemServiceImpl implements IRecoverSystemService, ApplicationRunner, Runnable {

    @Autowired
    private ITransactionMessageService transactionMessageService;

    @Autowired
    private IMQMessageService messageService;

    @Autowired
    private TransactionMessageProperties messageProperties;

    @Override
    public void run(ApplicationArguments args) {
        log.info("Startup recover system service.");
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(this, 5, 60, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        log.debug("Recover transaction message.");
        FinishTimeoutMessageDTO param = new FinishTimeoutMessageDTO();
        param.setPageSize(Math.min(messageProperties.getRecoverBatchSize(), 1000));
        long messageId = 0;
        Set<Long> surpassSendTimesMessage = new HashSet<>();
        try {
            PageResult<TransactionMessage> transactionMessages = transactionMessageService.queryFinishTimeoutMessage(param);
            for (TransactionMessage transactionMessage : transactionMessages) {
                if (transactionMessage.getMessageSendTimes() >= messageProperties.getMessageSendRetryTimes() + 1) {
                    surpassSendTimesMessage.add(transactionMessage.getMessageId());
                    log.trace("Transaction message '{}' surpass send times : {}'.", transactionMessage.getMessageId(), transactionMessage.getMessageSendTimes());
                    continue;
                }
                messageId = transactionMessage.getMessageId();
                //发送消息
                messageService.send(transactionMessage);
                //增加消息的发送次数
                transactionMessageService.addMessageSendTimes(transactionMessage.getMessageId());
                log.trace("Transaction message '{}' Recover retry.", transactionMessage.getMessageId());
            }
        } catch (Exception e) {
            try {
                log.error("恢复事务消息异常", e);
                //增加消息的发送次数
                transactionMessageService.addMessageSendTimes(messageId);
            } catch (Exception e1) {
                log.error("增加消息的发送次数异常", e1);
            }
        } finally {
            try {
                //杀死超过发送次数的消息
                if (!surpassSendTimesMessage.isEmpty())
                    transactionMessageService.killMessages(surpassSendTimesMessage);
            } catch (Exception e) {
                log.error("杀死超过发送次数的消息失败", e);
            }
        }

    }
}
