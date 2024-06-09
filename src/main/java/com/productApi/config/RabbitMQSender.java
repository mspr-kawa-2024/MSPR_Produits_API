package com.productApi.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendProductToOrder(String product) {
        rabbitTemplate.convertAndSend("orderToSendQueue", product);
    }
}