package org.sadness.provider.controller;

import io.swagger.annotations.Api;
import org.sadness.provider.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * <p>Account</p>
 *
 * @author xlp
 * @version 1.0.0
 * @since 2021/12/28 23:22
 */
@Api(tags = "Account")
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @PostMapping("/transfer/accounts")
    public boolean transferAccounts(
            @RequestParam(defaultValue = "1") Long accountId,
            @RequestParam(defaultValue = "1") Long userId,
            @RequestParam BigDecimal money) {
        return accountService.deductMoney(accountId, userId, money);
    }
}
