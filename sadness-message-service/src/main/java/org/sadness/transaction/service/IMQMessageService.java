package org.sadness.transaction.service;

import org.sadness.transaction.entity.TransactionMessage;

/**
 * <p></p>
 *
 * @author xlp
 * @version 1.0.0
 * @since 2021/12/21 18:54
 */
public interface IMQMessageService {

    void send(TransactionMessage transactionMessage);
}
