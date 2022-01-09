package org.sadness.transaction.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * <p>事务消息数据传输对象</p>
 *
 * @author xlp
 * @version 1.0.0
 * @since 2021/12/17 22:22
 */
@Data
public class TransactionMessageDTO {

    /**
     * 消息体
     */
    private String messageBody;

    /**
     * 消息数据类型
     */
    @NotEmpty(message = "消息数据类型不能为空")
    private String messageDataType;

    /**
     * 消费队列
     */
    @NotEmpty(message = "消费队列不能为空")
    private String consumerQueue;

    /**
     * 备注
     */
    private String remark;

    /**
     * 回调确认消息
     */
    @Valid
    @NotNull(message = "回调确认消息不能为空")
    private ConfirmInfoDTO confirmInfo;

    /**
     * 业务标识
     */
    @NotEmpty(message = "业务标识不能为空")
    private String businessTag;

}
