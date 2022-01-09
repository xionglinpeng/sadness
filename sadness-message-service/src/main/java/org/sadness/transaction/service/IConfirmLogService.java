package org.sadness.transaction.service;

import org.sadness.transaction.entity.ConfirmLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 熊林鹏
 * @since 2022-01-06
 */
public interface IConfirmLogService extends IService<ConfirmLog> {

    boolean addConfirmLog(Long messageId,String errorMessage);

}
