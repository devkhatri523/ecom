package org.asymptotes.payment.service;


import lombok.RequiredArgsConstructor;
import org.asymptotes.payment.dto.PaymentRequest;
import org.asymptotes.payment.mapper.PaymentMapper;
import org.asymptotes.payment.notification.NotificationProducer;
import org.asymptotes.payment.notification.PaymentNotificationRequest;
import org.asymptotes.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final NotificationProducer notificationProducer;

    public Integer createPayment(PaymentRequest request) {
        var payment = this.repository.save(this.mapper.toPayment(request));

        this.notificationProducer.sendNotification(
                new PaymentNotificationRequest(
                        request.orderReference(),
                        request.amount(),
                        request.paymentMethod(),
                        request.customer().firstName(),
                        request.customer().lastName(),
                        request.customer().email()
                )
        );
        return payment.getId();
    }

}
