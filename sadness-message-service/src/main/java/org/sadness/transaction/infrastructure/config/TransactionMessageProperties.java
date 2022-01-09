package org.sadness.transaction.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <p></p>
 *
 * @author xlp
 * @version 1.0.0
 * @since 2021/12/21 18:42
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "transaction.message.config")
public class TransactionMessageProperties {

    /**
     * 确认事务消息每批次处理的数据量，默认100，最多不能超过1000。
     */
    private int confirmBatchSize = 100;

    /**
     * 恢复事务消息每批次处理的数据量，默认100，最多不能超过1000。
     */
    private int recoverBatchSize = 100;

    /**
     * 事务消息的确认超时时间
     */
    private long confirmTimeout = 30000;

    /**
     * 事务消息的完成超时时间
     */
    private long finishTimeout = 30000;

    /**
     * 消息发送重试次数，默认3次
     */
    private int messageSendRetryTimes = 3;

    /**
     * 消息确认重试次数，默认3次
     */
    private int messageConfirmRetryTimes = 3;
}
