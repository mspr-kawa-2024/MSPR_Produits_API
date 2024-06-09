package com.productApi.config;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQReceiver {

    private String receivedMessage;

    @RabbitListener(queues = "orderProductQueue")
    public void receiveMessage(String message) {
        this.receivedMessage = message;
        try {
        } catch (NumberFormatException e) {
        }
    }

    public String getReceivedMessage() {
        return this.receivedMessage;
    }
}
