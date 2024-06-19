package com.productApi.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMQSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendProductToOrder(String product) {
        rabbitTemplate.convertAndSend("productToSendQueue", product);
    }

    public void sendResponseOfIdsVerification(String response) {
        rabbitTemplate.convertAndSend("responseProductIdsVerificationQueue", response);
    }
}
