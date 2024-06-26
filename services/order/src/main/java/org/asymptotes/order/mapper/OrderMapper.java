package org.asymptotes.order.mapper;

import jakarta.validation.Valid;
import org.asymptotes.order.dto.OrderRequest;
import org.asymptotes.order.dto.OrderResponse;
import org.asymptotes.order.entities.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {
    public Order toOrder(@Valid OrderRequest request) {
        return Order.builder().id(request.id()).customerId(request.customerId())
                .reference(request.reference()).totalAmount(request.amount())
                .paymentMethod(request.paymentMethod()).build();

    }

    public OrderResponse toOrderResponse(Order order) {
        return new OrderResponse(order.getId(), order.getReference(), order.getTotalAmount(), order.getPaymentMethod(), order.getCustomerId());
    }
}
