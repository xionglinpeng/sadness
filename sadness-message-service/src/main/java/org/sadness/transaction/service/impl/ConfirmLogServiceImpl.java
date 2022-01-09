package org.sadness.transaction.service.impl;

import org.checkerframework.checker.units.qual.C;
import org.sadness.transaction.entity.ConfirmLog;
import org.sadness.transaction.mapper.ConfirmLogMapper;
import org.sadness.transaction.service.IConfirmLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 熊林鹏
 * @since 2022-01-06
 */
@Service
public class ConfirmLogServiceImpl extends ServiceImpl<ConfirmLogMapper, ConfirmLog> implements IConfirmLogService {

    @Override
    public boolean addConfirmLog(Long messageId, String errorMessage) {
        ConfirmLog log = new ConfirmLog();
        log.setMessageId(messageId);
        log.setErrorInfo(errorMessage);
        long currentTimeMillis = System.currentTimeMillis();
        log.setCreateTime(currentTimeMillis);
        log.setUpdateTime(currentTimeMillis);
        return save(log);
    }
}
