package com.statemachine.controller;

import com.statemachine.controller.dto.DtoPaymentRequest;
import com.statemachine.controller.dto.DtoPaymentResponse;
import com.statemachine.services.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static com.statemachine.constants.APIConstants.APPLICATION_JSON;


@RestController
@Validated
@RequiredArgsConstructor
@Slf4j
public class CreditCardController {

    private final PaymentService paymentService;

    @PostMapping(value = "/payment/v1", produces = {APPLICATION_JSON}, consumes = {APPLICATION_JSON})
    HttpEntity<DtoPaymentResponse> createPayment(@RequestBody DtoPaymentRequest body) {
        log.debug("Start method createPayment()");
        return new ResponseEntity<>(
                paymentService.newPayment(body),
                HttpStatus.OK
        );
    }

    @PutMapping(value = "/payment/v1/auth/{id}", produces = {APPLICATION_JSON}, consumes = {APPLICATION_JSON})
    HttpEntity<Void> authPayment(@PathVariable Long id) {
        log.debug("Start method authPayment()");
        paymentService.authPayment(id);
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/payment/v1", produces = {APPLICATION_JSON})
    public HttpEntity<List<DtoPaymentResponse>> createPayment() {
        log.debug("Start method createPayment()");
        return new ResponseEntity<>(
                paymentService.getAllPayments(),
                HttpStatus.OK
        );
    }


}
