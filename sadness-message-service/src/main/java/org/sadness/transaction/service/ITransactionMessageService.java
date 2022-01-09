package org.sadness.transaction.service;

import cn.hutool.db.PageResult;
import org.sadness.transaction.dto.FinishTimeoutMessageDTO;
import org.sadness.transaction.dto.PrepareTimeoutMessageDTO;
import org.sadness.transaction.dto.TransactionMessageDTO;
import org.sadness.transaction.entity.TransactionMessage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 熊林鹏
 * @since 2021-12-17
 */
public interface ITransactionMessageService extends IService<TransactionMessage> {

    Long prepareTransactionMessage(TransactionMessageDTO param);

    Boolean commitTransactionMessage(Long messageId);

    Boolean commitTransactionMessage(TransactionMessage transactionMessage);

    Boolean rollbackTransactionMessage(Long messageId);

    Boolean rollbackTransactionMessage(TransactionMessage transactionMessage);

    Boolean finishTransactionMessage(Long messageId);

    PageResult<TransactionMessage> queryPrepareTimeoutMessage(PrepareTimeoutMessageDTO param);

    PageResult<TransactionMessage> queryFinishTimeoutMessage(FinishTimeoutMessageDTO param);

    void addMessageSendTimes(Long messageId);

    void addMessageConfirmTimes(Long messageId);

    void killMessages(Set<Long> messageIds);
}
