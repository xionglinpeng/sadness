package org.sadness.message.consumer;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.sadness.message.ThreadPoolHelper;
import org.sadness.message.remote.TransactionMessageClient;
import org.sadness.transaction.GlobalTransactionMessage;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.sadness.transaction.TransactionMessageConst.ROCKET_MQ_GROUP;
import static org.sadness.transaction.TransactionMessageConst.ROCKET_MQ_TOPIC;

/**
 * <p></p>
 *
 * @author xlp
 * @version 1.0.0
 * @since 2022/1/1 20:44
 */
@Component
@RocketMQMessageListener(topic = ROCKET_MQ_TOPIC, consumerGroup = ROCKET_MQ_GROUP)
public class RocketMQTransactionMessageListener implements RocketMQListener<GlobalTransactionMessage>, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private final TransactionMessageClient transactionMessageClient;


    public RocketMQTransactionMessageListener(TransactionMessageClient transactionMessageClient) {
        this.transactionMessageClient = transactionMessageClient;
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onMessage(GlobalTransactionMessage globalTransactionMessage) {
        System.out.println(globalTransactionMessage);
        applicationContext.publishEvent(new SadnessTransactionEvent(applicationContext, globalTransactionMessage));
        //执行完成，异步回调事务
        ThreadPoolHelper.executor.submit(() -> transactionMessageClient.finishTransactionMessage(globalTransactionMessage.getMessageId()));
    }
}
