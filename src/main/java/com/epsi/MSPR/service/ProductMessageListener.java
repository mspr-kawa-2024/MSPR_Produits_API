package com.epsi.MSPR.service;

import org.springframework.stereotype.Service;

@Service
public class ProductMessageListener {

    public void receiveMessage(Object message) {
        try {
            // Logique pour traiter le message
            System.out.println("Received message: " + message);
            // Traitez la commande ici et envoyez la réponse si nécessaire
        } catch (Exception e) {
            // Gérer les erreurs
            System.err.println("Error processing message: " + e.getMessage());
            // Logique de gestion des erreurs, comme renvoyer un message d'erreur à une autre queue
        }
    }
}

