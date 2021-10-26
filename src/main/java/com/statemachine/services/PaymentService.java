package com.statemachine.services;

import com.statemachine.controller.dto.DtoPaymentRequest;
import com.statemachine.controller.dto.DtoPaymentResponse;

import java.util.List;

public interface PaymentService {

    DtoPaymentResponse newPayment(DtoPaymentRequest payment);

    List<DtoPaymentResponse> getAllPayments();

    void authPayment(Long id);

}
