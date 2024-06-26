package org.asymptotes.order.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.asymptotes.order.mapper.OrderMapper;
import org.asymptotes.order.client.PaymentClient;
import org.asymptotes.order.dto.*;
import org.asymptotes.order.kafka.OrderProducer;
import org.asymptotes.order.repository.OrderRepository;
import org.asymptotes.order.client.CustomerClient;
import org.asymptotes.order.client.ProductClient;
import org.asymptotes.order.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository respository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public Integer createOrder(@Valid OrderRequest request) {
        var customer = this.customerClient.findCustomerById(request.customerId()).orElseThrow(() -> new BusinessException("Cannot create order" +
                " :: No Customer exists Provided Id")); // feign client
        var purchaseProducts = this.productClient.purchaseProducts(request.products());
        var order = this.respository.save(mapper.toOrder(request));
        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity())
            );

        }
        var paymentRequest = new PaymentRequest(
                request.customerId(),
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );


        paymentClient.requestOrderPayment(paymentRequest);
        orderProducer.sendOrderConfirmation(new OrderConfirmation(request.reference(),
                request.amount(), request.paymentMethod(), customer, purchaseProducts
        ));
        return order.getId();
    }

    public List<OrderResponse> findAll() {

        return respository.findAll().stream().map(mapper::toOrderResponse).collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {

        return respository.findById(orderId).map(mapper::toOrderResponse).orElseThrow(() -> new EntityNotFoundException(
                String.format("Order not found with id :: %d", orderId)));
    }
}
