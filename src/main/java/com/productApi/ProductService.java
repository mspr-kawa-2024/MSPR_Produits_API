package com.productApi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.productApi.config.RabbitMQReceiver;
import com.productApi.config.RabbitMQSender;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductService {

    @Autowired
    RabbitMQSender rabbitMQSender;

    @Autowired
    RabbitMQReceiver rabbitMQReceiver;

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

}
