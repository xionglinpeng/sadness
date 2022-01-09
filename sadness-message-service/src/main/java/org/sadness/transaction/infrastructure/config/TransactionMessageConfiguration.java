package org.sadness.transaction.infrastructure.config;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import static org.sadness.transaction.TransactionMessageConst.ROCKET_MQ_DESTINATION;

/**
 * <p></p>
 *
 * @author xlp
 * @version 1.0.0
 * @since 2021/12/21 18:44
 */
@Configuration
public class TransactionMessageConfiguration implements CommandLineRunner {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public void run(String... args) {
        rocketMQTemplate.setDefaultDestination(ROCKET_MQ_DESTINATION);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
