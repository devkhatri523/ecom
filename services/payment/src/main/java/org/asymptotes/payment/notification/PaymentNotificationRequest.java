package org.asymptotes.payment.notification;

import org.asymptotes.payment.model.PaymentMethod;

import java.math.BigDecimal;

public record PaymentNotificationRequest(String orderReference,
                                         BigDecimal amount,
                                         PaymentMethod paymentMethod,
                                         String customerFirstname,
                                         String customerLastname,
                                         String customerEmail) {
}
