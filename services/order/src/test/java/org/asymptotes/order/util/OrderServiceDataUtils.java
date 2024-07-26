package org.asymptotes.order.util;

import org.asymptotes.order.dto.*;
import org.asymptotes.order.entities.Order;
import org.asymptotes.order.entities.OrderLine;
import org.asymptotes.order.entities.PaymentMethod;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class OrderServiceDataUtils {
    public static Answer<Optional<CustomerResponse>> genereateCustomerResponse() {
        return new Answer<Optional<CustomerResponse>>() {
            @Override
            public Optional<CustomerResponse> answer(InvocationOnMock invocationOnMock) throws Throwable {
                CustomerResponse customerResponse = new CustomerResponse("cust-1", "first-name", "last-name", "test@gmail.com");
                return Optional.of(customerResponse);
            }
        };


    }

    public static Answer<List<PurchaseResponse>> generatePurchaseProductsResponse() {
        return new Answer<List<PurchaseResponse>>() {
            @Override
            public List<PurchaseResponse> answer(InvocationOnMock invocationOnMock) throws Throwable {

                return generatePurchaseProductsResponseListObject();

            }
        };
    }

    public static Answer<Integer> generatePaymentRequestResponse() {
        return new Answer<Integer>() {
            @Override
            public Integer answer(InvocationOnMock invocationOnMock) throws Throwable {

                return 123;

            }
        };
    }

    public static  List<PurchaseResponse> generatePurchaseProductsResponseListObject(){
        List<PurchaseResponse> purchaseResponseList = new ArrayList<>();
        PurchaseResponse purchaseResponse1 = new PurchaseResponse(123, "prod-1", "desc-1", new BigDecimal(500), 20.0);
        PurchaseResponse purchaseResponse2 = new PurchaseResponse(123, "prod-2", "desc-2", new BigDecimal(600), 30.0);
        purchaseResponseList.add(purchaseResponse1);
        purchaseResponseList.add(purchaseResponse1);
        return purchaseResponseList;
    }

    public static List<PurchaseRequest> generatePurchaseRequest() {
        List<PurchaseRequest> purchaseRequests = new ArrayList<>();
        PurchaseRequest purchaseRequest = new PurchaseRequest(123, 20);
        purchaseRequests.add(purchaseRequest);
        return purchaseRequests;
    }

    public static PaymentRequest genereatePaymentRequest() {
        CustomerResponse customerResponse = generateCustomerResponse();
        return new PaymentRequest("pay-1", new BigDecimal(300), PaymentMethod.BITCOIN, 234, "ord-ref-1", customerResponse);
    }

    public static  CustomerResponse generateCustomerResponse(){
        return   new CustomerResponse("cust-1", "first-name", "last-name", "test@gmail.com");

    }

    public static Order generateOrderCreationObject() {
        Order order = new Order();
        order.setId(123);
        order.setCustomerId("cust-id-1");
        order.setReference("order-ref-1");
        order.setPaymentMethod(PaymentMethod.BITCOIN);
        order.setCreateAt(LocalDateTime.now());
        OrderLine orderLine = new OrderLine();
        orderLine.setId(123);
        orderLine.setOrder(order);
        order.setLastModifiedDate(LocalDateTime.now());
        order.setOrderLine(Arrays.asList(orderLine));
        return order;
    }
    public static OrderLineRequest generateOrderLineRequest(){
        return new OrderLineRequest(123,456,789,30);
    }

    public static OrderConfirmation generateOrderConfirmationObject(){
        CustomerResponse customerResponse = generateCustomerResponse();
        List<PurchaseResponse> purchaseResponseList = generatePurchaseProductsResponseListObject();
        return new OrderConfirmation("ord-ref-1",new BigDecimal(200),PaymentMethod.BITCOIN,customerResponse,purchaseResponseList);
    }

    public static  OrderRequest generateOrderRequestObject(){
        List<PurchaseRequest> purchaseRequest = generatePurchaseRequest();
        return new OrderRequest(123,"ord-ref-1",new BigDecimal(200),PaymentMethod.BITCOIN,"cust-id-1",purchaseRequest);
    }

    public static OrderResponse genereateOrderResponse(){
        return new OrderResponse(123,"ord-ref-1",new BigDecimal(200),PaymentMethod.BITCOIN,"cust-id-1");
    }

    public static OrderLine orderLine(){
        OrderLine orderLine = new OrderLine();
        Order order = generateOrderCreationObject();
        orderLine.setId(123);
        orderLine.setQuantity(20);
        orderLine.setProductId(234);
        orderLine.setOrder(order);
        return orderLine;
    }

    public static OrderLineResponse orderLineResponse(){
        return new OrderLineResponse(123,20);
    }



}
