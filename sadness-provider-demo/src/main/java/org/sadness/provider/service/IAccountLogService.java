package org.sadness.provider.service;

import org.sadness.provider.entity.AccountLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 熊林鹏
 * @since 2021-12-30
 */
public interface IAccountLogService extends IService<AccountLog> {

    void recordAccountOperationLog(Long accountId, Long userId, BigDecimal money, Integer action);
}
