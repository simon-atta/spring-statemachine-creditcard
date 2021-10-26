package com.statemachine.controller.dto;

import com.statemachine.model.PaymentState;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class DtoPaymentRequest {

    private Long id;

    @Enumerated(EnumType.STRING)
    private PaymentState state;

    private BigDecimal amount;

    public DtoPaymentRequest(BigDecimal amount) {
        this.amount = amount;
    }
}
