package org.sadness.message;

import org.apache.rocketmq.spring.core.RocketMQListener;
import org.sadness.message.consumer.RocketMQTransactionMessageListener;
import org.sadness.message.provider.SadnessTransactionAspect;
import org.sadness.message.remote.TransactionMessageClient;
import org.sadness.transaction.GlobalTransactionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p></p>
 *
 * @author xlp
 * @version 1.0.0
 * @since 2021/12/29 22:55
 */
@Configuration
@EnableFeignClients("org.sadness.message.remote")
public class SadnessClientAutoconfiguration {

    @Bean
    @ConditionalOnProperty("rocketmq.name-server")
    @ConditionalOnMissingBean(RocketMQTransactionMessageListener.class)
    public RocketMQListener<GlobalTransactionMessage> rocketMQTransactionMessageListener(
            TransactionMessageClient transactionMessageClient){
        return new RocketMQTransactionMessageListener(transactionMessageClient);
    }

    @Bean
    public SadnessTransactionAspect sadnessTransactionAspect(){
        return new SadnessTransactionAspect();
    }

}
