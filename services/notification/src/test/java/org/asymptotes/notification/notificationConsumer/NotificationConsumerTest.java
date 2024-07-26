package org.asymptotes.notification.notificationConsumer;

import jakarta.mail.MessagingException;
import org.asymptotes.notification.entities.notification.Notification;
import org.asymptotes.notification.entities.order.OrderConfirmation;
import org.asymptotes.notification.entities.payment.PaymentConfirmation;
import org.asymptotes.notification.kafka.NotificationConsumer;
import org.asymptotes.notification.repository.NotificationRepository;
import org.asymptotes.notification.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.asymptotes.notification.util.NotificationDataUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class NotificationConsumerTest {
    @Mock
    private NotificationRepository repository;
    @Mock
    private EmailService emailService;

    private NotificationConsumer notificationConsumer;

    @BeforeEach
    public void setup() {
        notificationConsumer = new NotificationConsumer(repository, emailService);
    }
    @Test

    public void test_consumePaymentSuccessNotification() throws MessagingException {
        PaymentConfirmation paymentConfirmation =generatePaymentConfirmationObject();
        Notification notification = generatePaymentConfirmationNotificationObject();
        Mockito.when(repository.save(any(Notification.class))).thenReturn(notification);
        doNothing().when(emailService).sendPaymentSuccessEmail(any(String.class),any(String.class),any(BigDecimal.class),any(String.class));
        notificationConsumer.consumePaymentSuccessNotification(paymentConfirmation);
        verify(repository,times(1)).save(any(Notification.class));
        verify(emailService,times(1)).sendPaymentSuccessEmail(any(String.class),any(String.class),any(BigDecimal.class),any(String.class));

    }
    @Test

    public void test_consumeOrderSuccessNotification() throws MessagingException {
        OrderConfirmation orderConfirmation =generateOrderConfirmationObject();
        Notification notification = generatePaymentConfirmationNotificationObject();
        Mockito.when(repository.save(any(Notification.class))).thenReturn(notification);
        doNothing().when(emailService).sendOrderConfirmationEmail(any(String.class),any(String.class),any(BigDecimal.class),any(String.class),any(List.class));
        notificationConsumer.consumeOrdderSuccessNotification(orderConfirmation);
        verify(repository,times(1)).save(any(Notification.class));
        verify(emailService,times(1)).sendOrderConfirmationEmail(any(String.class),any(String.class),any(BigDecimal.class),any(String.class),any(List.class));

    }

}
