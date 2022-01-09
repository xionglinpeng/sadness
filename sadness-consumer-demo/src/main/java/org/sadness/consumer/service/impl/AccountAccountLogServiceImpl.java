package org.sadness.consumer.service.impl;

import org.sadness.consumer.entity.AccountLog;
import org.sadness.consumer.mapper.AccountLogMapper;
import org.sadness.consumer.service.IAccountLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 熊林鹏
 * @since 2022-01-01
 */
@Service
public class AccountAccountLogServiceImpl extends ServiceImpl<AccountLogMapper, AccountLog> implements IAccountLogService {

    @Override
    public void recordAccountOperationLog(Long accountId, Long userId, BigDecimal money, String businessTag, Integer action) {
        AccountLog log = new AccountLog();
        log.setBusinessTag(businessTag);
        log.setUserId(userId);
        log.setAccountId(accountId);
        log.setAction(action);
        log.setMoney(money);
        log.setCreateTime(System.currentTimeMillis());
        save(log);
    }

    @Override
    public boolean isRecord(String businessTag) {
        return lambdaQuery().eq(AccountLog::getBusinessTag,businessTag).count() == 1;
    }
}
