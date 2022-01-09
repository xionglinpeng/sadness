package org.sadness.consumer.service;

import org.sadness.consumer.entity.AccountLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 熊林鹏
 * @since 2022-01-01
 */
public interface IAccountLogService extends IService<AccountLog> {

    void recordAccountOperationLog(Long accountId, Long userId, BigDecimal money, String businessTag, Integer action);

    boolean isRecord(String businessTag);
}
