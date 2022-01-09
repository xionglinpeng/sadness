package org.sadness.consumer.entity;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
/**
 * <p>
 * 
 * </p>
 *
 * @author 熊林鹏
 * @since 2022-01-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("account")
public class Account extends Model<Account> {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 账户所有人
     */
    private Long userId;

    /**
     * 余额
     */
    private BigDecimal money;

    /**
     * 账户状态：0→正常，-1冻结
     */
    private Integer status;

    /**
     * 账户等级：0→普通账户，1→白金账户，2→黄金账户
     */
    private Integer level;

    /**
     * 版本号
     */
    @Version
    private Integer version;

    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String MONEY = "money";

    public static final String STATUS = "status";

    public static final String LEVEL = "level";

    public static final String VERSION = "version";

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
