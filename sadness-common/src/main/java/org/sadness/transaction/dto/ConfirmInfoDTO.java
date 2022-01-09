package org.sadness.transaction.dto;

import lombok.Data;
import org.sadness.transaction.validator.HttpMethod;
import org.sadness.transaction.validator.JsonFormat;

import javax.validation.constraints.NotEmpty;

/**
 * <p></p>
 *
 * @author xlp
 * @version 1.0.0
 * @since 2022/1/5 21:26
 */
@Data
public class ConfirmInfoDTO {

    @NotEmpty(message = "回调服务名称不能为空")
    private String serverName;

    @NotEmpty(message = "回调服务路径不能为空")
    private String path;

    @NotEmpty(message = "回调请求方式不能为空")
    @HttpMethod(message = "回调请求方式不合法")
    private String method;

    private String body;

    @JsonFormat(message = "回调请求头必须为JSON数据")
    private String headers;

}
