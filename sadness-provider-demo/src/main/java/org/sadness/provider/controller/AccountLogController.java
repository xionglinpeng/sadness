package org.sadness.provider.controller;

import io.swagger.annotations.Api;
import org.sadness.provider.entity.AccountLog;
import org.sadness.provider.service.IAccountLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p></p>
 *
 * @author xlp
 * @version 1.0.0
 * @since 2022/1/6 21:45
 */
@Api(tags = "AccountLog")
@RestController
@RequestMapping("/account/log")
public class AccountLogController {

    @Autowired
    private IAccountLogService accountLogService;

    @GetMapping("{businessTag}")
    public boolean is(@PathVariable String businessTag) {
        return accountLogService.lambdaQuery().eq(AccountLog::getBusinessTag,businessTag).count() == 1;
    }
}
