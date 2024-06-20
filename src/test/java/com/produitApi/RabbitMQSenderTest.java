package com.produitApi;

import com.productApi.ProductApplication;
import com.productApi.config.RabbitMQSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.verify;

@SpringBootTest(classes = ProductApplication.class)
@ActiveProfiles("test")
public class RabbitMQSenderTest {

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendProductToOrder() {
        String productMessage = "{\"id\":1,\"name\":\"Product1\"}";

        rabbitMQSender.sendProductToOrder(productMessage);

        verify(rabbitTemplate).convertAndSend("productToSendQueue", productMessage);
    }

    @Test
    void testSendResponseOfIdsVerification() {
        String responseMessage = "ok";

        rabbitMQSender.sendResponseOfIdsVerification(responseMessage);

        verify(rabbitTemplate).convertAndSend("responseProductIdsVerificationQueue", responseMessage);
    }
}
