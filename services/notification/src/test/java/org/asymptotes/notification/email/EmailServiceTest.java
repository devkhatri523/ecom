package org.asymptotes.notification.email;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.asymptotes.notification.entities.order.Product;
import org.asymptotes.notification.service.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.math.BigDecimal;
import java.util.List;

import static org.asymptotes.notification.util.NotificationDataUtils.generatePurchaseProductsListObject;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class EmailServiceTest {

    @Mock
    private  JavaMailSender mailSender;
    @Mock
    private  SpringTemplateEngine templateEngine;
    @Mock
    MimeMessage message;
    @Captor
    private ArgumentCaptor<MimeMessage> mimeMessage;


    EmailService emailService;
    @BeforeEach
    public void setup(){
        emailService= new EmailService(mailSender,templateEngine);
    }

    @Test
    public void test_sendPaymentSuccessEmail() throws MessagingException {
        List<Product> productList = generatePurchaseProductsListObject();
        Mockito.when(mailSender.createMimeMessage()).thenReturn(message);
        Mockito.when(templateEngine.process(any(String.class),any(Context.class))).thenReturn("order_email_template");
        doNothing().when(mailSender).send(any(MimeMessage.class));
        emailService.sendOrderConfirmationEmail("dest-1","cust-1",new BigDecimal(200),"order-ref-1",productList);
        verify(mailSender,times(1)).send(any(MimeMessage.class));
    }

    @Test
    public void test_sendOrderSuccessEmail() throws MessagingException {
        Mockito.when(mailSender.createMimeMessage()).thenReturn(message);
        Mockito.when(templateEngine.process(any(String.class),any(Context.class))).thenReturn("payment_email_template");
        doNothing().when(mailSender).send(any(MimeMessage.class));
        emailService.sendPaymentSuccessEmail("dest-1","cust-1",new BigDecimal(200),"order-ref-1");
        verify(mailSender,times(1)).send(any(MimeMessage.class));
    }
}
