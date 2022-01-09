package org.sadness.transaction;

/**
 * <p></p>
 *
 * @author xlp
 * @version 1.0.0
 * @since 2021/12/28 23:18
 */
public interface TransactionMessageConst {

    String ROCKET_MQ_TOPIC = "SADNESS";
    String ROCKET_MQ_TAGS = "transaction-message";
    String ROCKET_MQ_DESTINATION = ROCKET_MQ_TOPIC + ":"+ ROCKET_MQ_TAGS;
    String ROCKET_MQ_GROUP = "sadness-transaction-message";
}
