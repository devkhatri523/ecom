package org.asymptotes.order.mapper;

import org.asymptotes.order.dto.OrderLineRequest;
import org.asymptotes.order.dto.OrderLineResponse;
import org.asymptotes.order.entities.Order;
import org.asymptotes.order.entities.OrderLine;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {
    public OrderLine toOrderLine(OrderLineRequest orderLineRequest) {
        return OrderLine.builder().id(orderLineRequest.Id())
                .quantity(orderLineRequest.quantity())
                .order(Order.builder().id(orderLineRequest.orderId()).build())
                .productId(orderLineRequest.productId()).build();

    }
    public OrderLineResponse toOrderLineResponse(OrderLine orderLine) {
        return new OrderLineResponse(
                orderLine.getId(),
                orderLine.getQuantity()
        );
    }
}
