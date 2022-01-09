package org.sadness.message.provider;

import java.util.UUID;

/**
 * <p></p>
 *
 * @author xlp
 * @version 1.0.0
 * @since 2022/1/6 21:21
 */
public class SadnessTMContext {

    /**
     * 事务消息ID
     */
    private static final ThreadLocal<Long> MESSAGE_ID = new ThreadLocal<>();

    /**
     * 业务标识，用于提供给客户端业务上标识使用。
     */
    private static final ThreadLocal<String> BUSINESS_TAG = new ThreadLocal<>();

    public static void addMessageId(Long messageId) {
        MESSAGE_ID.set(messageId);
    }

    public static Long getMessageId() {
        return MESSAGE_ID.get();
    }

    public static void generateUniqueTag() {
        BUSINESS_TAG.set(UUID.randomUUID().toString().replace("-", ""));
    }

    public static String getBusinessTag() {
        return BUSINESS_TAG.get();
    }

    public static void clear() {
        MESSAGE_ID.remove();
        BUSINESS_TAG.remove();
    }
}
