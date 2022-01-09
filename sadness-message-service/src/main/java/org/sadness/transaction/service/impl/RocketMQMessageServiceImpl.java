package org.sadness.transaction.service.impl;

import cn.hutool.core.bean.BeanUtil;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.sadness.transaction.GlobalTransactionMessage;
import org.sadness.transaction.entity.TransactionMessage;
import org.sadness.transaction.service.IMQMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

/**
 * <p></p>
 *
 * @author xlp
 * @version 1.0.0
 * @since 2021/12/21 18:55
 */
@Service
public class RocketMQMessageServiceImpl implements IMQMessageService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public void send(TransactionMessage transactionMessage) {
        GlobalTransactionMessage message = new GlobalTransactionMessage();
        BeanUtil.copyProperties(transactionMessage, message);
        rocketMQTemplate.send(transactionMessage.getConsumerQueue(), new GenericMessage<>(message));
    }
}
