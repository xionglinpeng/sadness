package org.sadness.provider.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author 熊林鹏
 * @since 2021-12-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("account_log")
public class AccountLog extends Model<AccountLog> {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 业务唯一标识
     */
    private String businessTag;

    /**
     * 账户所有人
     */
    private Long userId;

    /**
     * 账户ID
     */
    private Long accountId;

    /**
     * 动作：0→扣款，1→入账
     */
    private Integer action;

    /**
     * 余额
     */
    private BigDecimal money;

    /**
     * 创建时间
     */
    private Long createTime;

    public static final String ID = "id";

    public static final String BUSINESS_TAG = "business_tag";

    public static final String USER_ID = "user_id";

    public static final String ACCOUNT_ID = "account_id";

    public static final String ACTION = "action";

    public static final String MONEY = "money";

    public static final String CREATE_TIME = "create_time";

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
