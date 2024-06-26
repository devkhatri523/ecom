package org.asymptotes.payment.dto;

import org.asymptotes.payment.model.Customer;
import org.asymptotes.payment.model.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        String id,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        Customer customer
) {
}
