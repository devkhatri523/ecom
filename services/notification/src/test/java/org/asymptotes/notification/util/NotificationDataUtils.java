package org.asymptotes.notification.util;

import org.asymptotes.notification.constant.NotificationType;
import org.asymptotes.notification.entities.notification.Notification;
import org.asymptotes.notification.entities.order.Customer;
import org.asymptotes.notification.entities.order.OrderConfirmation;
import org.asymptotes.notification.entities.order.Product;
import org.asymptotes.notification.entities.payment.PaymentConfirmation;
import org.asymptotes.notification.entities.payment.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NotificationDataUtils {

    public static PaymentConfirmation generatePaymentConfirmationObject(){
        return new PaymentConfirmation("ord-ref-1",new BigDecimal(200),PaymentMethod.BITCOIN,"cust-first-1","cust-last-1","cust-email");
    }

    public static Notification generatePaymentConfirmationNotificationObject(){
        Notification notification = new Notification();
        notification.setId("123");
        notification.setNotificationDate(LocalDateTime.now());
        notification.setType(NotificationType.PAYMENT_CONFIRMATION);
        notification.setOrderConfirmation(null);
        notification.setPaymentConfirmation(generatePaymentConfirmationObject());
        return notification;

    }
    public static Notification generateOrderConfirmationNotificationObject(){
        Notification notification = new Notification();
        notification.setId("123");
        notification.setNotificationDate(LocalDateTime.now());
        notification.setType(NotificationType.ORDER_CONFIRMATION);
        notification.setOrderConfirmation(generateOrderConfirmationObject());
        notification.setPaymentConfirmation(null);
        return notification;

    }

    public static OrderConfirmation generateOrderConfirmationObject(){
        Customer customerResponse = generateCustomerObject();
        List<Product> purchaseResponseList = generatePurchaseProductsListObject();
        return new OrderConfirmation("ord-ref-1",new BigDecimal(200),PaymentMethod.BITCOIN,customerResponse,purchaseResponseList);
    }

    public static  Customer generateCustomerObject(){
        return   new Customer("cust-1", "first-name", "last-name", "test@gmail.com");

    }

    public static  List<Product> generatePurchaseProductsListObject(){
        List<Product> purchaseResponseList = new ArrayList<>();
        Product product1 = new Product(123, "prod-1", "desc-1", new BigDecimal(500), 20.0);
        Product product2 = new Product(123, "prod-2", "desc-2", new BigDecimal(600), 30.0);
        purchaseResponseList.add(product1);
        purchaseResponseList.add(product2);
        return purchaseResponseList;
    }
}
