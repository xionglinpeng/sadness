package org.sadness.consumer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.sadness.consumer.entity.Account;
import org.sadness.consumer.mapper.AccountMapper;
import org.sadness.consumer.service.IAccountLogService;
import org.sadness.consumer.service.IAccountService;
import org.sadness.message.consumer.SadnessTransactionEvent;
import org.sadness.transaction.GlobalTransactionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 熊林鹏
 * @since 2022-01-01
 */
@Slf4j
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {

    @Autowired
    private IAccountLogService accountLogService;

    private final ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows
    @EventListener(SadnessTransactionEvent.class)
    @Transactional
    public void onTransactionMessage(SadnessTransactionEvent event) {
        GlobalTransactionMessage message = event.getGlobalTransactionMessage();
        log.info("The enter account info : {}", message);
        TransactionMessageBody messageBody = mapper.readValue(message.getMessageBody(), TransactionMessageBody.class);
        //校验幂等性（基于日志保证幂等性，基于乐观锁保证并发幂等性）
        if (!accountLogService.isRecord(message.getBusinessTag())) {
            Account account = lambdaQuery().eq(Account::getUserId, messageBody.getUserId()).one();
            enterAccount(account, messageBody.getMoney(), message.getBusinessTag());
        }
    }

    @Override
    public boolean enterAccount(Account account, BigDecimal money, String businessTag) {
        account.setMoney(account.getMoney().add(money));
        if (updateById(account)) {
            accountLogService.recordAccountOperationLog(account.getId(), account.getUserId(), money, businessTag, 2);
            return true;
        }
        // 虽然乐观锁保证并发的幂等性，但是不同的事务消息也会由于乐观锁而更新失败。
        // 因此抛出异常，避免完成事务，使失败的事务重新消费。
        throw new RuntimeException("入账失败");
    }

    @Data
    static class TransactionMessageBody {

        private Long userId;

        private BigDecimal money;

    }
}
