package com.productApi.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

/**
 * Classe de configuration pour les files d'attente RabbitMQ utilisées dans l'API des produits.
 * Cette classe définit et initialise diverses files d'attente qui seront utilisées
 * pour la communication par messages entre les différentes parties du système.
 */

public class RabbitMQConfig {

    /**
     * Déclare une file d'attente nommée "productsIdQueue" utilisée pour envoyer les identifiants des produits.
     * @return une instance de la file d'attente.
     */
    @Bean
    public Queue productsIdQueue() {
        return new Queue("productsIdQueue", false);
    }
    @Bean
    public Queue productToSendQueue() {
        return new Queue("productToSendQueue", false);
    }

    @Bean
    public Queue productIdsIdQueue() {
        return new Queue("productIdsToProductQueue", false);
    }

    @Bean
    public Queue responseProductIdsVerificationQueue() {
        return new Queue("responseProductIdsVerificationQueue", false);
    }
}
