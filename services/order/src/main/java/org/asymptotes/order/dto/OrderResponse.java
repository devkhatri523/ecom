package org.asymptotes.order.dto;

import org.asymptotes.order.entities.PaymentMethod;

import java.math.BigDecimal;

public record OrderResponse(
        Integer id,
        String reference ,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerId
) {
}
