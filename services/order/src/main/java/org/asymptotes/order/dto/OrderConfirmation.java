package org.asymptotes.order.dto;

import org.asymptotes.order.entities.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(String orderReference,
                                BigDecimal totalAmount,
                                PaymentMethod paymentMethod,
                                CustomerResponse customer,
                                List<PurchaseResponse> products) {
}
