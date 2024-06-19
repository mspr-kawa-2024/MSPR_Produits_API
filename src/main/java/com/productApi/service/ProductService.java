package com.productApi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.productApi.config.RabbitMQSender;
import com.productApi.model.Product;
import com.productApi.repository.ProductRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private RabbitMQSender rabbitMQSender;
    private ObjectMapper objectMapper;
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void setRabbitMQSender(RabbitMQSender rabbitMQSender) {
        this.rabbitMQSender = rabbitMQSender;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void addNewProduct(Product product) {
        Optional<Product> productByName = productRepository.findByName(product.getName());
        if (productByName.isPresent()) {
            throw new IllegalStateException("name taken");
        }
        productRepository.save(product);
    }

    public void updateProduct(Long productId, String name) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalStateException("Product with id " + productId + " does not exist"));

        if (name != null && !name.isEmpty() && !name.equals(product.getName())) {
            product.setName(name);
            productRepository.save(product);
        }
    }

    public void deleteProduct(Long productId) {
        boolean exists = productRepository.existsById(productId);
        if (!exists) {
            throw new IllegalStateException("Product with id " + productId + " does not exist");
        }
        productRepository.deleteById(productId);
    }

    @RabbitListener(queues = "productsIdQueue")
    public void handleProductsRequest(String productsId) throws JsonProcessingException {
        System.out.println(productsId);

        List<Long> productsByIds = extractProductsIds(productsId);

        StringBuilder jsonResult = new StringBuilder();

        for (Long productId : productsByIds) {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalStateException("Product with id " + productId + " does not exist"));
            String productJson = objectMapper.writeValueAsString(product);
            jsonResult.append(productJson);
        }
        rabbitMQSender.sendProductToOrder(jsonResult.toString());
    }

    public static List<Long> extractProductsIds(String str) {
        List<Long> numberList = new ArrayList<>();
        String[] numbers = str.split(",");

        for (String number : numbers) {
            try {
                numberList.add(Long.parseLong(number.trim()));
            } catch (NumberFormatException e) {
                System.out.println("Erreur de format : " + e.getMessage());
            }
        }

        return numberList;
    }

    @RabbitListener(queues = "productIdsToProductQueue")
    public void verificationOfProductsToAddInOrder(String productIdsReceived) {
        List<Long> ids = convertirEnLongs(productIdsReceived);

        Long idToSend = null;
        for (Long id : ids) {
            if (!productRepository.existsById(id)) {
                idToSend = id;
                break;
            }
        }
        if (idToSend != null) {
            rabbitMQSender.sendResponseOfIdsVerification(idToSend.toString());
        } else {
            rabbitMQSender.sendResponseOfIdsVerification("ok");
        }
    }
        private static List<Long> convertirEnLongs(String input) {
            String[] elements = input.split(",");
            List<Long> result = new ArrayList<>();

            for (String element : elements) {
                element = element.trim();
                try {
                    result.add(Long.parseLong(element));
                } catch (NumberFormatException e) {
                    System.out.println("Erreur : '" + element + "' n'est pas un nombre valide.");
                    return new ArrayList<>();
                }
            }
            return result;
        }
    }
