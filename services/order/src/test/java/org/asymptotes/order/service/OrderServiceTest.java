package org.asymptotes.order.service;

import com.sun.source.tree.ModuleTree;
import jakarta.persistence.EntityNotFoundException;
import org.asymptotes.order.client.CustomerClient;
import org.asymptotes.order.client.PaymentClient;
import org.asymptotes.order.client.ProductClient;
import org.asymptotes.order.dto.*;
import org.asymptotes.order.entities.Order;
import org.asymptotes.order.kafka.OrderProducer;
import org.asymptotes.order.mapper.OrderMapper;
import org.asymptotes.order.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.asymptotes.order.util.OrderServiceDataUtils.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    @Mock
    OrderRepository respository;
    @Mock
    CustomerClient customerClient;
    @Mock
    ProductClient productClient;
    @Mock
    OrderLineService orderLineService;
    @Mock
    OrderProducer orderProducer;
    @Mock
    PaymentClient paymentClient;
    @Mock
    OrderMapper orderMapper;

    OrderService orderService;


    @BeforeEach
    void init() {
        orderService = new OrderService(respository, customerClient, productClient, orderMapper, orderLineService, orderProducer, paymentClient);
    }

    @Test
   public void test_createOrder() {
        List<PurchaseRequest> purchaseRequest = generatePurchaseRequest();
        Order orderRequest = generateOrderCreationObject();
        Mockito.when(customerClient.findCustomerById("cust-id-1")).thenAnswer(genereateCustomerResponse());
        Mockito.when(productClient.purchaseProducts(purchaseRequest)).thenAnswer(generatePurchaseProductsResponse());
        Mockito.when(paymentClient.requestOrderPayment(any(PaymentRequest.class))).thenAnswer(generatePaymentRequestResponse());
        Mockito.when(orderMapper.toOrder(any(OrderRequest.class))).thenReturn(orderRequest);
        Mockito.when(respository.save(orderRequest)).thenReturn(orderRequest);
        Mockito.when(orderLineService.saveOrderLine(any(OrderLineRequest.class))).thenReturn(123);
        Mockito.doNothing().when(orderProducer).sendOrderConfirmation(any(OrderConfirmation.class));
        Mockito.verify(orderProducer, Mockito.times(0)).sendOrderConfirmation(any(OrderConfirmation.class));
        Integer orderId = orderService.createOrder(generateOrderRequestObject());
        Assertions.assertEquals(123, orderId);
    }
    @Test
    public void test_findAllOrders(){
        Order order = generateOrderCreationObject();
        Mockito.when(respository.findAll()).thenReturn(Arrays.asList(order));
        Mockito.when(orderMapper.toOrderResponse(order)).thenReturn(genereateOrderResponse());
        List<OrderResponse> orderResponseList = orderService.findAll();
        Assertions.assertFalse(orderResponseList.isEmpty());
        Assertions.assertEquals(123,orderResponseList.get(0).id());
    }
    @Test
    public void test_findById(){
        Order order = generateOrderCreationObject();
        Mockito.when(respository.findById(123)).thenReturn(Optional.of(order));
        Mockito.when(orderMapper.toOrderResponse(order)).thenReturn(genereateOrderResponse());
        OrderResponse orderResponse = orderService.findById(123);
        Assertions.assertEquals(123,orderResponse.id());
    }
    @Test
    public void test_findByIdThrowsException(){
        Mockito.when(respository.findById(123)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> {
            orderService.findById(123);
        });
    }


}
