package org.asymptotes.order.kafka;


import org.asymptotes.order.dto.OrderConfirmation;
import org.asymptotes.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import static org.asymptotes.order.util.OrderServiceDataUtils.generateOrderConfirmationObject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)

public class OrderProducerTest {
    @Mock
    private KafkaTemplate<String, OrderConfirmation> kafkaTemplate;

    @Captor
    private ArgumentCaptor<Message<OrderConfirmation>> messageCaptor;
    OrderProducer producer;

    @BeforeEach
    void setUp(){
        producer = new OrderProducer(kafkaTemplate);

    }
    @Test
    public void test_orderProducer() {
        OrderConfirmation orderConfirmation = generateOrderConfirmationObject();
        producer.sendOrderConfirmation(orderConfirmation);
        verify(kafkaTemplate).send(messageCaptor.capture());
        Message<OrderConfirmation> capturedMessage = messageCaptor.getValue();
        assertEquals(orderConfirmation, capturedMessage.getPayload());
        assertEquals("order-topic", capturedMessage.getHeaders().get(KafkaHeaders.TOPIC));
    }
}
