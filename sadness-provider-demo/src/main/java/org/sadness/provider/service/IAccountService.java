package org.sadness.provider.service;

import org.sadness.provider.entity.Account;
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
public interface IAccountService extends IService<Account> {

    boolean deductMoney(Long accountId, Long userId, BigDecimal money);
}
