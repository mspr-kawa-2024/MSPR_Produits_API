package com.productApi.config;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQReceiver {

    private String receivedMessage;

    public String getReceivedMessage(String message) {
        receivedMessage = message;
        return receivedMessage;
    }

    @RabbitListener(queues = "productToSendQueue")
    public void receiveProductMessage(String message) {
        this.receivedMessage = message;
    }

    @RabbitListener(queues = "responseProductIdsVerificationQueue")
    public void receiveVerificationMessage(String message) {
        this.receivedMessage = message;
    }
}
