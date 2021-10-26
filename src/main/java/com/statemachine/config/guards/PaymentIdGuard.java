package com.statemachine.config.guards;

import com.statemachine.model.PaymentEvent;
import com.statemachine.model.PaymentState;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;

import static com.statemachine.services.StateMachinePaymentServiceImpl.PAYMENT_ID_HEADER;


@Component
public class PaymentIdGuard implements Guard<PaymentState, PaymentEvent> {

    @Override
    public boolean evaluate(StateContext<PaymentState, PaymentEvent> context) {
        return context.getMessageHeader(PAYMENT_ID_HEADER) != null;
    }
}
