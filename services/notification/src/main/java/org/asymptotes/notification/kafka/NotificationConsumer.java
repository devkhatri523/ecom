package org.asymptotes.notification.kafka;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.asymptotes.notification.constant.NotificationType;
import org.asymptotes.notification.entities.notification.Notification;
import org.asymptotes.notification.entities.order.OrderConfirmation;
import org.asymptotes.notification.entities.payment.PaymentConfirmation;
import org.asymptotes.notification.repository.NotificationRepository;
import org.asymptotes.notification.service.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {
    private final NotificationRepository repository;
    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotification(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info(String.format("Consuming message from payment topic %s", paymentConfirmation));
        repository.save(Notification.builder().type(NotificationType.PAYMENT_CONFIRMATION).notificationDate(LocalDateTime.now())
                .paymentConfirmation(paymentConfirmation).build());
        var customerName = paymentConfirmation.customerFirstname() + " " + paymentConfirmation.customerLastname();
        emailService.sendPaymentSuccessEmail(
                paymentConfirmation.customerEmail(),
                customerName,
                paymentConfirmation.amount(),
                paymentConfirmation.orderReference()
        );


    }

    @KafkaListener(topics = "order-topic")
    public void consumeOrdderSuccessNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info(String.format("Consuming message from payment topic %s", orderConfirmation));
        repository.save(Notification.builder().type(NotificationType.ORDER_CONFIRMATION).notificationDate(LocalDateTime.now())
                .orderConfirmation(orderConfirmation).build());
        var customerName = orderConfirmation.customer().firstName() + " " + orderConfirmation.customer().lastName();
        emailService.sendOrderConfirmationEmail(
                orderConfirmation.customer().email(),
                customerName,
                orderConfirmation.totalAmount(),
                orderConfirmation.orderReference(),
                orderConfirmation.products()
        );


    }
}
