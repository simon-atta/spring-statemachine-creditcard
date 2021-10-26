package com.statemachine.services;

import com.statemachine.controller.dto.DtoPaymentRequest;
import com.statemachine.controller.dto.DtoPaymentResponse;
import com.statemachine.model.Payment;
import com.statemachine.model.PaymentEvent;
import com.statemachine.model.PaymentState;
import com.statemachine.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@SpringBootTest
class StateMachinePaymentServiceImplTest {

    @Autowired
    StateMachinePaymentService stateMachinePaymentService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    PaymentRepository paymentRepository;

    DtoPaymentRequest payment;

    @BeforeEach
    void setUp() {
        payment = new DtoPaymentRequest(new BigDecimal("12.99"));
    }

    @Transactional
    @Test
    void preAuth() {
        DtoPaymentResponse savedPayment = paymentService.newPayment(payment);

        System.out.println("Should be NEW");
        System.out.println(savedPayment.getState());

        StateMachine<PaymentState, PaymentEvent> sm = stateMachinePaymentService.preAuth(savedPayment.getId());

        Payment preAuthedPayment = paymentRepository.getOne(savedPayment.getId());

        System.out.println("Should be PRE_AUTH or PRE_AUTH_ERROR");
        System.out.println(sm.getState().getId());

        System.out.println(preAuthedPayment);

    }


    @Transactional
    @RepeatedTest(10)
    void testAuth() {
        DtoPaymentResponse savedPayment = paymentService.newPayment(payment);

        StateMachine<PaymentState, PaymentEvent> preAuthSM = stateMachinePaymentService.preAuth(savedPayment.getId());

        if (preAuthSM.getState().getId() == PaymentState.PRE_AUTH) {
            System.out.println("Payment is Pre Authorized");

            StateMachine<PaymentState, PaymentEvent> authSM = stateMachinePaymentService.authorizePayment(savedPayment.getId());

            System.out.println("Result of Auth: " + authSM.getState().getId());
        } else {
            System.out.println("Payment failed pre-auth...");
        }
    }
}