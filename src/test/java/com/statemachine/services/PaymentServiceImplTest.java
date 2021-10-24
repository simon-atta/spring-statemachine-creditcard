package com.statemachine.services;

import com.statemachine.model.Payment;
import com.statemachine.model.PaymentEvent;
import com.statemachine.model.PaymentState;
import com.statemachine.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@SpringBootTest
class PaymentServiceImplTest {

    @Autowired
    PaymentService paymentService;

    @Autowired
    PaymentRepository paymentRepository;

    Payment payment;

    @BeforeEach
    void setUp() {
        payment = Payment.builder().amount(new BigDecimal("12.99")).build();
    }

    @Transactional
    @Test
    void preAuth() {
        Payment savedPayment = paymentService.newPayment(payment);

        System.out.println("Should be NEW");
        System.out.println(savedPayment.getState());

        StateMachine<PaymentState, PaymentEvent> stateMachine = paymentService.preAuth(savedPayment.getId());

        Payment preAuthedPayment = paymentRepository.findById(savedPayment.getId()).get();

        System.out.println("Should be PRE_AUTH");
        System.out.println("State Machine state : "  + stateMachine.getState().getId());
        System.out.println("Object State : " + preAuthedPayment.getState());

    }
}