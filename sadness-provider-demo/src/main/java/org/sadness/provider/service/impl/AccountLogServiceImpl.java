package org.sadness.provider.service.impl;

import org.sadness.message.provider.SadnessTMContext;
import org.sadness.provider.entity.AccountLog;
import org.sadness.provider.mapper.AccountLogMapper;
import org.sadness.provider.service.IAccountLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 熊林鹏
 * @since 2021-12-30
 */
@Service
public class AccountLogServiceImpl extends ServiceImpl<AccountLogMapper, AccountLog> implements IAccountLogService {

    @Override
    public void recordAccountOperationLog(Long accountId, Long userId, BigDecimal money, Integer action) {
        AccountLog log = new AccountLog();
        log.setBusinessTag(SadnessTMContext.getBusinessTag());
        log.setUserId(userId);
        log.setAccountId(accountId);
        log.setAction(action);
        log.setMoney(money);
        log.setCreateTime(System.currentTimeMillis());
        save(log);
    }
}
