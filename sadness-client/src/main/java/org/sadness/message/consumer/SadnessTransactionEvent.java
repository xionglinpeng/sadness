package org.sadness.message.consumer;

import lombok.Getter;
import org.sadness.transaction.GlobalTransactionMessage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;

/**
 * <p></p>
 *
 * @author xlp
 * @version 1.0.0
 * @since 2022/1/1 21:12
 */

@Getter
public class SadnessTransactionEvent extends ApplicationContextEvent {

    private GlobalTransactionMessage globalTransactionMessage;


    public SadnessTransactionEvent(ApplicationContext source, GlobalTransactionMessage globalTransactionMessage) {
        super(source);
        this.globalTransactionMessage = globalTransactionMessage;
    }

    /**
     * Create a new TransactionMessageEvent.
     *
     * @param source the {@code ApplicationContext} that the event is raised for
     *               (must not be {@code null})
     */
    public SadnessTransactionEvent(ApplicationContext source) {
        super(source);
    }
}
