package org.sadness.transaction;

import lombok.Data;

/**
 * <p></p>
 *
 * @author xlp
 * @version 1.0.0
 * @since 2022/1/1 16:02
 */
@Data
public class GlobalTransactionMessage {

    /**
     * 消息ID
     */
    private Long messageId;

    /**
     * 消息体
     */
    private String messageBody;

    /**
     * 消息数据类型
     */
    private String messageDataType;

    /**
     * 消费队列
     */
    private String consumerQueue;

    /**
     * 业务标识
     */
    private String businessTag;
}
