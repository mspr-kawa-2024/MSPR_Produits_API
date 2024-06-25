package com.productApi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.productApi.config.RabbitMQSender;
import com.productApi.model.Product;
import com.productApi.repository.ProductRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service pour gérer les opérations CRUD sur les produits et les interactions RabbitMQ.
 */
@Service
public class ProductService {

    @Autowired
    RabbitMQSender rabbitMQSender;

    @Autowired
    private ObjectMapper objectMapper;

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Optional<Product> findByName(String name) {
        return productRepository.findByName(name);
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

        if (name != null && name.length() > 0 && !name.equals(product.getName())) {
            product.setName(name);
            productRepository.save(product);
        }
    }

    public void deleteProduct(Long productId) {
        boolean exists = productRepository.existsById(productId);
        if (!exists) {
            throw new IllegalStateException( "Product with id " + productId + " does not exists");
        }
        productRepository.deleteById(productId);
    }

    @RabbitListener(queues = "productsIdQueue")
    public void handleProductsRequest(String productsId) throws JsonProcessingException {
        System.out.println(productsId);

        List<Long> productsByIds = extractProductsIds(productsId);

        String jsonResult = "";

        int i ;
        for (i=0 ; i<productsByIds.size() ; i++) {

            Product product = productRepository.findById(productsByIds.get(i))
                    .orElseThrow(() -> new IllegalStateException(
                            "Product with id  does not exist"));
            String productJson = objectMapper.writeValueAsString(product);
            jsonResult += productJson;
        }
        rabbitMQSender.sendProductToOrder(jsonResult);
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
        String response = processVerificationOfProducts(productIdsReceived);
        rabbitMQSender.sendResponseOfIdsVerification(response);
    }

    public String processVerificationOfProducts(String productIdsReceived) {
        List<Long> ids = convertirEnLongs(productIdsReceived);
        int i = 0;
        Long idToSend = null;
        for (Long id : ids) {
            if (!productRepository.existsById(id)) {
                i++;
                idToSend = id;
                break;
            }
        }
        if (i > 0) {
            return idToSend.toString();
        } else {
            return "ok";
        }
    }


    public static List<Long> convertirEnLongs(String input) {
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
