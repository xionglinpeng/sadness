package org.sadness.transaction.infrastructure.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * <p>事务消息状态</p>
 *
 * @author xlp
 * @version 1.0.0
 * @since 2021/12/17 22:31
 */
public enum TransactionMessageStatus implements IEnum<Integer> {

    PREPARE(0), //预备
    COMMIT(1), //提交
    ROLLBACK(2), //回滚
    FINISH(3) //完成
    ;

    private final int value;

    TransactionMessageStatus(int value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
