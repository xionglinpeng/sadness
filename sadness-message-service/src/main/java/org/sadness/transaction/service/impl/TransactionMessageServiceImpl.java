package org.sadness.transaction.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.db.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.sadness.transaction.dto.FinishTimeoutMessageDTO;
import org.sadness.transaction.dto.PrepareTimeoutMessageDTO;
import org.sadness.transaction.dto.TransactionMessageDTO;
import org.sadness.transaction.entity.TransactionMessage;
import org.sadness.transaction.infrastructure.config.TransactionMessageProperties;
import org.sadness.transaction.infrastructure.enums.TransactionMessageStatus;
import org.sadness.transaction.infrastructure.helper.PageHelper;
import org.sadness.transaction.mapper.TransactionMessageMapper;
import org.sadness.transaction.service.IMQMessageService;
import org.sadness.transaction.service.ITransactionMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;

import static org.sadness.transaction.entity.TransactionMessage.*;
import static org.sadness.transaction.infrastructure.enums.TransactionMessageStatus.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 熊林鹏
 * @since 2021-12-17
 */
@Slf4j
@Service
public class TransactionMessageServiceImpl extends ServiceImpl<TransactionMessageMapper, TransactionMessage> implements ITransactionMessageService {

    @Autowired
    private IMQMessageService mqMessageService;

    @Autowired
    private TransactionMessageProperties transactionMessageProperties;

    @Override
    public Long prepareTransactionMessage(TransactionMessageDTO param) {
        TransactionMessage transactionMessage = new TransactionMessage();
        transactionMessage.setMessageSendTimes(0);
        transactionMessage.setDead(false);
        transactionMessage.setStatus(PREPARE);
        long currentTimeMillis = System.currentTimeMillis();
        transactionMessage.setCreateTime(currentTimeMillis);
        transactionMessage.setUpdateTime(currentTimeMillis);
        BeanUtil.copyProperties(param, transactionMessage);
        transactionMessage.insert();
        printDebugLog(transactionMessage, PREPARE);
        return transactionMessage.getMessageId();
    }

    @Override
    public Boolean commitTransactionMessage(Long messageId) {
        return commitTransactionMessage(getById(messageId));
    }

    @Override
    public Boolean commitTransactionMessage(TransactionMessage transactionMessage) {
        transactionMessage.setMessageId(transactionMessage.getMessageId());
        transactionMessage.setUpdateTime(System.currentTimeMillis());
        transactionMessage.setStatus(COMMIT);
        transactionMessage.setMessageSendTimes(transactionMessage.getMessageSendTimes() + 1);
        boolean result = updateById(transactionMessage);
        mqMessageService.send(transactionMessage);
        printDebugLog(transactionMessage, COMMIT);
        return result;
    }

    @Override
    public Boolean rollbackTransactionMessage(Long messageId) {
        return rollbackTransactionMessage(getById(messageId));
    }

    @Override
    public Boolean rollbackTransactionMessage(TransactionMessage transactionMessage) {
        transactionMessage.setUpdateTime(System.currentTimeMillis());
        transactionMessage.setStatus(ROLLBACK);
        boolean result = updateById(transactionMessage);
        printDebugLog(transactionMessage, ROLLBACK);
        return result;
    }

    @Override
    public Boolean finishTransactionMessage(Long messageId) {
        TransactionMessage transactionMessage = getById(messageId);
        transactionMessage.setMessageId(messageId);
        transactionMessage.setUpdateTime(System.currentTimeMillis());
        transactionMessage.setStatus(FINISH);
        boolean result = updateById(transactionMessage);
        printDebugLog(transactionMessage, FINISH);
        return result;
    }

    @Override
    public PageResult<TransactionMessage> queryPrepareTimeoutMessage(PrepareTimeoutMessageDTO param) {
        Page<TransactionMessage> pageResult = lambdaQuery()
                .eq(TransactionMessage::getStatus, PREPARE)
                .eq(TransactionMessage::getDead, false)
                .apply(CREATE_TIME + " + " + transactionMessageProperties.getConfirmTimeout() + " <=" + System.currentTimeMillis())
                .page(PageHelper.createQueryPage(param));
        return PageHelper.createPageResult(pageResult);
    }

    @Override
    public PageResult<TransactionMessage> queryFinishTimeoutMessage(FinishTimeoutMessageDTO param) {
        Page<TransactionMessage> pageResult = lambdaQuery()
                .eq(TransactionMessage::getStatus, COMMIT)
                .eq(TransactionMessage::getDead, false)
                .apply(UPDATE_TIME + " + " + transactionMessageProperties.getFinishTimeout() + " <=" + System.currentTimeMillis())
                .page(PageHelper.createQueryPage(param));
        return PageHelper.createPageResult(pageResult);
    }

    @Override
    public void addMessageSendTimes(Long messageId) {
        lambdaUpdate()
                .setSql(MESSAGE_SEND_TIMES + " = " + MESSAGE_SEND_TIMES + " + 1")
                .eq(TransactionMessage::getMessageId, messageId)
                .update();
        log.debug("Add message({}) send times.", messageId);
    }

    @Override
    public void addMessageConfirmTimes(Long messageId) {
        lambdaUpdate()
                .setSql(MESSAGE_CONFIRM_TIMES + " = " + MESSAGE_CONFIRM_TIMES + " + 1")
                .eq(TransactionMessage::getMessageId, messageId)
                .update();
        log.debug("Add message({}) confirm times.", messageId);
    }

    @Override
    public void killMessages(Set<Long> messageIds) {
        lambdaUpdate()
                .setSql(DEAD + " = 1")
                .in(TransactionMessage::getMessageId, messageIds)
                .update();
        log.debug("Kill messages in ({}).", Arrays.toString(messageIds.toArray()));
    }

    private void printDebugLog(TransactionMessage transactionMessage, TransactionMessageStatus status) {
        log.debug("The transaction id : {}, Phase {}, message : {}",
                transactionMessage.getMessageId(), status, transactionMessage);
    }
}
