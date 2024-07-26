package org.asymptotes.order.service;

import org.asymptotes.order.dto.OrderLineRequest;
import org.asymptotes.order.dto.OrderLineResponse;
import org.asymptotes.order.entities.OrderLine;
import org.asymptotes.order.mapper.OrderLineMapper;
import org.asymptotes.order.repository.OrderLineRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.asymptotes.order.util.OrderServiceDataUtils.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class OrderLineServiceTest {
    @Mock
    private OrderLineRepository repository;
    @Mock
    private OrderLineMapper mapper;
    OrderLineService orderLineService;

    @BeforeEach
    void init() {
        orderLineService = new OrderLineService(repository, mapper);

    }

    @Test
    void test_saveOrderLine() {
        Mockito.when(mapper.toOrderLine(any(OrderLineRequest.class))).thenReturn(orderLine());
        Mockito.when(repository.save(any(OrderLine.class))).thenReturn(orderLine());
        Integer id = orderLineService.saveOrderLine(generateOrderLineRequest());
        Assertions.assertEquals(123, id);

    }

    @Test
    void test_findAllOrderLineById() {
        Mockito.when(mapper.toOrderLineResponse(any(OrderLine.class))).thenReturn(orderLineResponse());
        Mockito.when(repository.findAllByOrderId(any(Integer.class))).thenReturn(Arrays.asList(orderLine()));
        List<OrderLineResponse> orderLineResponseList = orderLineService.findAllByOrderId(123);
        Assertions.assertFalse(orderLineResponseList.isEmpty());
        Assertions.assertEquals(123, orderLineResponseList.get(0).id());
    }

}
