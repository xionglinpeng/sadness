package org.sadness.consumer.service;

import org.sadness.consumer.entity.Account;
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
public interface IAccountService extends IService<Account> {

    boolean enterAccount(Account account, BigDecimal money, String businessTag);

}
