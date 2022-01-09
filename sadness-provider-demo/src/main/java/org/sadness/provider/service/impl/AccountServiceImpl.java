package org.sadness.provider.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.sadness.message.provider.SadnessTMContext;
import org.sadness.message.provider.SadnessTransactional;
import org.sadness.provider.entity.Account;
import org.sadness.provider.mapper.AccountMapper;
import org.sadness.provider.service.IAccountLogService;
import org.sadness.provider.service.IAccountService;
import org.sadness.transaction.TransactionMessageConst;
import org.sadness.transaction.dto.ConfirmInfoDTO;
import org.sadness.transaction.dto.TransactionMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 熊林鹏
 * @since 2021-12-30
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {

    @Autowired
    private IAccountLogService accountLogService;

    @Override
    @Transactional
    @SadnessTransactional("enterAccount")
    public boolean deductMoney(Long accountId, Long userId, BigDecimal money) {
        boolean update = lambdaUpdate()
                .setSql(Account.MONEY + " = " + Account.MONEY + " - " + money)
                .eq(Account::getId, accountId)
                .eq(Account::getUserId, userId)
                .ge(Account::getMoney, money)
                .update();
        if (!update)
            throw new RuntimeException("扣款失败");
        accountLogService.recordAccountOperationLog(accountId, userId, money, 1);
        return true;
    }

    public TransactionMessageDTO enterAccount(Long accountId, Long userId, BigDecimal money) {
        TransactionMessageDTO param = new TransactionMessageDTO();
        param.setMessageBody(String.format("{\"userId\":\"%s\",\"money\":%f}", 1, money));
        param.setMessageDataType(MediaType.APPLICATION_JSON_VALUE);
        param.setConsumerQueue(TransactionMessageConst.ROCKET_MQ_DESTINATION);
        param.setRemark("enter account");
        ConfirmInfoDTO confirmInfoDTO = new ConfirmInfoDTO();
        confirmInfoDTO.setServerName("sadness-provider-demo");
        confirmInfoDTO.setPath("/account/log/" + SadnessTMContext.getBusinessTag());
        confirmInfoDTO.setMethod(HttpMethod.GET.name());
        confirmInfoDTO.setBody(null);
        confirmInfoDTO.setHeaders(null);
        param.setConfirmInfo(confirmInfoDTO);
        return param;
    }

}
