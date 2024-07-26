package org.asymptotes.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.asymptotes.order.dto.OrderRequest;
import org.asymptotes.order.dto.OrderResponse;
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

import java.util.Arrays;
import java.util.List;

import static org.asymptotes.order.util.OrderServiceDataUtils.generateOrderRequestObject;
import static org.asymptotes.order.util.OrderServiceDataUtils.genereateOrderResponse;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
@ContextConfiguration(classes = {OrderController.class})
@ExtendWith(SpringExtension.class)
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderService orderService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void test_createOrder() throws Exception {
        OrderRequest orderRequest = generateOrderRequestObject();
        String requestJson = objectMapper.writeValueAsString(orderRequest);
        when(orderService.createOrder(orderRequest)).thenReturn(123);
        MockHttpServletRequestBuilder builder = post("/api/v1/orders").content(requestJson);
        this.mockMvc.perform(builder.contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().string(String.valueOf(orderRequest.id())));
    }

    @Test
    public void test_findAll() throws Exception {
        List<OrderResponse> responseList = List.of(genereateOrderResponse());
        MockHttpServletRequestBuilder builder = get("/api/v1/orders");
        when(orderService.findAll()).thenReturn(List.of(genereateOrderResponse()));
        this.mockMvc.perform(builder.contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().json(objectMapper.writeValueAsString(responseList)))
                .andExpect(jsonPath("$.[0].reference").value("ord-ref-1"));
    }

    @Test
    public void test_findById() throws Exception {
        OrderResponse response = genereateOrderResponse();
        MockHttpServletRequestBuilder builder = get("/api/v1/orders/{order-id}",123);
        when(orderService.findById(123)).thenReturn(genereateOrderResponse());
        this.mockMvc.perform(builder.contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().json(objectMapper.writeValueAsString(response))).
                andExpect(jsonPath("$.reference").value("ord-ref-1"));
    }

}
