package org.sadness.message.remote;

import org.sadness.transaction.dto.TransactionMessageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * <p></p>
 *
 * @author xlp
 * @version 1.0.0
 * @since 2021/12/22 21:07
 */
@FeignClient("sadness-message-service")
@RequestMapping("/transaction/message")
public interface TransactionMessageClient {

    /**
     * 预备事务消息
     * @param param 消息信息参数。
     * @return 消息ID
     */
    @PostMapping("/prepare")
    Long prepareTransactionMessage(@RequestBody TransactionMessageDTO param);

    /**
     * 提交事务消息
     * @param messageId 消息ID。
     * @return TRUE→提交成功
     */
    @PutMapping("/commit/{messageId}")
    Boolean commitTransactionMessage(@PathVariable("messageId") Long messageId);

    /**
     * 回滚事务消息
     * @param messageId 消息ID。
     * @return TRUE→回滚成功
     */
    @PutMapping("/rollback/{messageId}")
    Boolean rollbackTransactionMessage(@PathVariable("messageId") Long messageId);

    /**
     * 完成事务消息
     * @param messageId 消息ID。
     * @return TRUE→完成成功
     */
    @PutMapping("/finish/{messageId}")
    Boolean finishTransactionMessage(@PathVariable("messageId") Long messageId);
}
