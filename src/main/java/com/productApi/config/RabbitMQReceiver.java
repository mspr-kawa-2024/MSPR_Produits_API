package com.productApi.config;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQReceiver {

    private String receivedMessage;

    @RabbitListener(queues = "orderQueue")
    public void receiveOrderMessage(String message) {
        this.receivedMessage = message;
        try {
            System.out.println("Received order message: " + message);
        } catch (NumberFormatException e) {
            System.err.println("Erreur de parsing des IDs : " + e.getMessage());
        }
    }

    public String getReceivedMessage() {
        return this.receivedMessage;
    }
}
