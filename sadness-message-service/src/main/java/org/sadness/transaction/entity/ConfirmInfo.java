package org.sadness.transaction.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * <p></p>
 *
 * @author xlp
 * @version 1.0.0
 * @since 2022/1/5 21:30
 */
@Data
public class ConfirmInfo {

    private String serverName;

    private String path;

    private String method;

    private String body;

    private String headers;

    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{")
                .append("\"serverName\":\"").append(serverName).append("\",")
                .append("\"path\":\"").append(path).append("\",")
                .append("\"method\":\"").append(method).append("\"");
        if (!StringUtils.isEmpty(body))
            sb.append(",").append("\"body\":\"").append(body).append("\"");
        if (!StringUtils.isEmpty(headers))
            sb.append(",").append("\"headers\":\"").append(headers).append("\"");
        sb.append("}");
        return sb.toString();
    }

    public static ConfirmInfo toInstance(String jsonConfirmInfo) {
        return JSONObject.parseObject(jsonConfirmInfo, ConfirmInfo.class);
    }
}
