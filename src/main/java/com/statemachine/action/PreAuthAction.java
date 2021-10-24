package com.statemachine.action;

import com.statemachine.model.PaymentEvent;
import com.statemachine.model.PaymentState;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.action.Action;

import java.util.Random;

import static com.statemachine.services.PaymentServiceImpl.PAYMENT_ID_HEADER;

public class PreAuthAction {

    public static Action<PaymentState, PaymentEvent> preAuthAction() {
        return context -> {
            System.out.println("PreAuth was called!!!");

            if (new Random().nextInt(10) < 8) {
                context.getStateMachine()
                        .sendEvent(MessageBuilder.withPayload(PaymentEvent.PRE_AUTH_APPROVED)
                        .setHeader(PAYMENT_ID_HEADER, context.getMessageHeader(PAYMENT_ID_HEADER))
                        .build());

            } else {
                context.getStateMachine()
                        .sendEvent(MessageBuilder.withPayload(PaymentEvent.PRE_AUTH_DECLINED)
                        .setHeader(PAYMENT_ID_HEADER, context.getMessageHeader(PAYMENT_ID_HEADER))
                        .build());
            }
        };
    }
}
