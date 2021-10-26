package com.statemachine.services;

import com.statemachine.controller.dto.DtoPaymentRequest;
import com.statemachine.controller.dto.DtoPaymentResponse;
import com.statemachine.model.Payment;
import com.statemachine.model.PaymentState;
import com.statemachine.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final StateMachinePaymentService stateMachinePaymentService;

    @Override
    public DtoPaymentResponse newPayment(DtoPaymentRequest dtoPaymentRequest) {
        Payment payment = new Payment(PaymentState.NEW, dtoPaymentRequest.getAmount());
        paymentRepository.save(payment);
        stateMachinePaymentService.preAuth(payment.getId());
        return new DtoPaymentResponse(payment.getId(), payment.getState(), payment.getAmount());
    }

    @Override
    public List<DtoPaymentResponse> getAllPayments() {
        return paymentRepository.findAll().stream().map(payment
                        -> new DtoPaymentResponse(payment.getId(), payment.getState(), payment.getAmount()))
                .collect(Collectors.toList());
    }

    @Override
    public void authPayment(Long id) {
        stateMachinePaymentService.authorizePayment(id);
    }
}
