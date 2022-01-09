package org.sadness.transaction.controller;

import cn.hutool.db.PageResult;
import io.swagger.annotations.ApiOperation;
import org.sadness.transaction.dto.FinishTimeoutMessageDTO;
import org.sadness.transaction.dto.PrepareTimeoutMessageDTO;
import org.sadness.transaction.dto.TransactionMessageDTO;
import org.sadness.transaction.entity.TransactionMessage;
import org.sadness.transaction.service.ITransactionMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 熊林鹏
 * @since 2021-12-17
 */
@RestController
@RequestMapping("/transaction/message")
public class TransactionMessageController {

    @Autowired
    private ITransactionMessageService iTransactionMessageService;

    @ApiOperation(value = "预备事务消息")
    @PostMapping("/prepare")
    public Long prepareTransactionMessage(@Validated @RequestBody TransactionMessageDTO param){
        return iTransactionMessageService.prepareTransactionMessage(param);
    }

    @ApiOperation(value = "提交事务消息")
    @PutMapping("/commit/{messageId}")
    public Boolean commitTransactionMessage(@PathVariable Long messageId){
        return iTransactionMessageService.commitTransactionMessage(messageId);
    }

    @ApiOperation(value = "回滚事务消息")
    @PutMapping("/rollback/{messageId}")
    public Boolean rollbackTransactionMessage(@PathVariable Long messageId){
        return iTransactionMessageService.rollbackTransactionMessage(messageId);
    }

    @ApiOperation(value = "完成事务消息")
    @PutMapping("/finish/{messageId}")
    public Boolean finishTransactionMessage(@PathVariable Long messageId){
        return iTransactionMessageService.finishTransactionMessage(messageId);
    }

    @ApiOperation(value = "查询预备超时的消息")
    @GetMapping("/prepares")
    public PageResult<TransactionMessage> queryPrepareTimeoutMessage(PrepareTimeoutMessageDTO param){
        return iTransactionMessageService.queryPrepareTimeoutMessage(param);
    }

    @ApiOperation(value = "查询完成超时的消息")
    @GetMapping("/finishes")
    public PageResult<TransactionMessage> queryFinishTimeoutMessage(FinishTimeoutMessageDTO param){
        return iTransactionMessageService.queryFinishTimeoutMessage(param);
    }

}
