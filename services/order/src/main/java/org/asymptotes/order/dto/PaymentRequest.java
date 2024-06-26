package org.asymptotes.order.dto;

import org.asymptotes.order.entities.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        String id,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer) {
}
