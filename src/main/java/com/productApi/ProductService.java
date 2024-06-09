package com.productApi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.productApi.config.RabbitMQReceiver;
import com.productApi.config.RabbitMQSender;
import jakarta.transaction.Transactional;
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

    @RabbitListener(queues = "orderProductQueue")
    public void handleOrderRequest(String orderId) throws JsonProcessingException {

        Product product = productRepository.findByOrderId(orderId);

        String productJson = objectMapper.writeValueAsString(product);
        rabbitMQSender.sendProductToOrder(productJson);

    }


}
