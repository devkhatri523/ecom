package org.asymptotes.payment.mapper;

import org.asymptotes.payment.dto.PaymentRequest;
import org.asymptotes.payment.entities.Payment;
import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {
    public Payment toPayment(PaymentRequest request) {
        if (request == null) {
            return null;
        }
        return Payment.builder()
                .paymentMethod(request.paymentMethod())
                .amount(request.amount())
                .orderId(request.orderId())
                .build();
    }

}
