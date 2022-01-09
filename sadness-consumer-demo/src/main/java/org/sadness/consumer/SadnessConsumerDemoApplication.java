package org.sadness.consumer;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableKnife4j
@EnableSwagger2
@EnableDiscoveryClient
@SpringBootApplication
public class SadnessConsumerDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SadnessConsumerDemoApplication.class, args);
    }

}
