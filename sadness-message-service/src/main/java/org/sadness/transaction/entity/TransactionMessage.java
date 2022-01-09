package org.sadness.transaction.entity;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;

import org.sadness.transaction.infrastructure.enums.TransactionMessageStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.sadness.transaction.infrastructure.ibatis.ConfirmInfoTypeHandler;

/**
 * <p>
 * 
 * </p>
 *
 * @author 熊林鹏
 * @since 2021-12-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "transaction_message",autoResultMap = true)
public class TransactionMessage extends Model<TransactionMessage> {

    private static final long serialVersionUID = 1L;

    /**
     * 消息ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long messageId;

    /**
     * 业务标识
     */
    private String businessTag;

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
     * 消息重发次数
     */
    private Integer messageSendTimes;

    /**
     * 消息确认次数
     */
    private Integer messageConfirmTimes;

    /**
     * 是否死亡
     */
    private Boolean dead;

    /**
     * 状态
     */
    private TransactionMessageStatus status;

    /**
     * 回调确认消息
     */
    @TableField(typeHandler = ConfirmInfoTypeHandler.class)
    private ConfirmInfo confirmInfo;

    /**
     * 备注
     */
    private String remark;

    /**
     * 版本号
     */
    @Version
    private Integer version;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 修改时间
     */
    private Long updateTime;

    public static final String MESSAGE_ID = "message_id";

    public static final String BUSINESS_TAG = "business_tag";

    public static final String MESSAGE_BODY = "message_body";

    public static final String MESSAGE_DATA_TYPE = "message_data_type";

    public static final String CONSUMER_QUEUE = "consumer_queue";

    public static final String MESSAGE_SEND_TIMES = "message_send_times";

    public static final String MESSAGE_CONFIRM_TIMES = "message_confirm_times";

    public static final String DEAD = "dead";

    public static final String STATUS = "status";

    public static final String CONFIRM_INFO = "confirm_info";

    public static final String REMARK = "remark";

    public static final String VERSION = "version";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
