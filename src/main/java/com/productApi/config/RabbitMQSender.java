package com.productApi.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
/**
 * Classe de service pour l'envoi de messages via RabbitMQ dans l'API des produits.
 * Cette classe utilise RabbitTemplate pour envoyer des messages à différentes files d'attente.
 */
public class RabbitMQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * Envoie un message contenant les informations d'un produit à la file d'attente "productToSendQueue".
     * @param product Le message contenant les informations du produit.
     */
    public void sendProductToOrder(String product) {
        rabbitTemplate.convertAndSend("productToSendQueue", product);
    }

    /**
     * Envoie une réponse de vérification d'identifiants de produits à la file d'attente "responseProductIdsVerificationQueue".
     * @param response La réponse de vérification des identifiants.
     */
    public void sendResponseOfIdsVerification(String response) {
        rabbitTemplate.convertAndSend("responseProductIdsVerificationQueue", response);
    }
}
