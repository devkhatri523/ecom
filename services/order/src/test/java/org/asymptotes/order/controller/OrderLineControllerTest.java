package org.asymptotes.order.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.asymptotes.order.dto.OrderLineResponse;
import org.asymptotes.order.dto.OrderResponse;
import org.asymptotes.order.mapper.OrderLineMapper;
import org.asymptotes.order.repository.OrderLineRepository;
import org.asymptotes.order.service.OrderLineService;
import org.asymptotes.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.asymptotes.order.util.OrderServiceDataUtils.genereateOrderResponse;
import static org.asymptotes.order.util.OrderServiceDataUtils.orderLineResponse;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderLineController.class)
@ContextConfiguration(classes = {OrderLineController.class})
@ExtendWith(SpringExtension.class)
public class OrderLineControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderLineService orderLineService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void test_findOrderLineById() throws Exception {
        List<OrderLineResponse> response = List.of(orderLineResponse());
        MockHttpServletRequestBuilder builder = get("/api/v1/order-lines/order/{order-id}",123);
        when(orderLineService.findAllByOrderId(123)).thenReturn(response);
        this.mockMvc.perform(builder.contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().json(objectMapper.writeValueAsString(response))).
                andExpect(jsonPath("$.[0].quantity").value(20));
    }

}
