package org.sadness.transaction.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
/**
 * <p>
 * 
 * </p>
 *
 * @author 熊林鹏
 * @since 2022-01-06
 */
@TableName("confirm_log")
public class ConfirmLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 事务消息ID
     */
    private Long messageId;

    /**
     * 异常消息
     */
    private String errorInfo;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 修改时间
     */
    private Long updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Log{" +
        ", id=" + id +
        ", messageId=" + messageId +
        ", errorInfo=" + errorInfo +
        ", version=" + version +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
