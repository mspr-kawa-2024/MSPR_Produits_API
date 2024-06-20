package com.produitApi;

import com.productApi.ProductApplication;
import com.productApi.config.RabbitMQReceiver;
import com.productApi.config.RabbitMQSender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ProductApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@EnableRabbit
public class RabbitMQIntegrationTest {

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Autowired
    private RabbitMQReceiver rabbitMQReceiver;

    @BeforeEach
    void setUp() {
        // Initialisation nécessaire avant chaque test si besoin
    }

    @Test
    void testSendAndReceiveProductMessage() throws InterruptedException {
        String productMessage = "{\"id\":1,\"name\":\"Product1\"}";

        rabbitMQSender.sendProductToOrder(productMessage);

        // Attendre un peu pour que le message soit reçu
        Thread.sleep(1000);

        String receivedMessage = rabbitMQReceiver.getReceivedMessage(productMessage);
        assertEquals(productMessage, receivedMessage);
    }

    @Test
    void testSendAndReceiveVerificationMessage() throws InterruptedException {
        String verificationMessage = "ok";

        rabbitMQSender.sendResponseOfIdsVerification(verificationMessage);

        // Attendre un peu pour que le message soit reçu
        Thread.sleep(1000);

        String receivedMessage = rabbitMQReceiver.getReceivedMessage(verificationMessage);
        assertEquals(verificationMessage, receivedMessage);
    }
}
