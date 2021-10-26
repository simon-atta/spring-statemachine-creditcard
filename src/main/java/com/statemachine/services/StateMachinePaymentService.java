package com.statemachine.services;

import com.statemachine.model.PaymentEvent;
import com.statemachine.model.PaymentState;
import org.springframework.statemachine.StateMachine;

public interface StateMachinePaymentService {

    StateMachine<PaymentState, PaymentEvent> preAuth(Long paymentId);

    StateMachine<PaymentState, PaymentEvent> authorizePayment(Long paymentId);

}
