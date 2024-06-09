package com.productApi;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/product")
@CrossOrigin(origins = "http://localhost:3002",
            methods = {RequestMethod.GET,
                    RequestMethod.POST,
                    RequestMethod.PUT,
                    RequestMethod.DELETE})
public class ProductController {

    private final ProductService productService;

    //@Autowired
    //private RabbitMQSender rabbitMQSender;

    //@Autowired
    //private RabbitMQReceiver rabbitMQReceiver;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{ProductId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        Product product = productService.getProductById(productId);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public void registerNewProduct(@RequestBody Product product) {
        productService.addNewProduct(product);
    }

    @PutMapping(path = "{productId}")
    public void updateProduct(@PathVariable("productId") Long producttId,
                             @RequestParam(required = false) String name) {
        productService.updateProduct(producttId, name);
    }

    @DeleteMapping(path = "{productId}")
    public void deleteProduct(@PathVariable("productId") Long productId) {
        productService.deleteProduct(productId);
    }

    /*
    @GetMapping("/{clientId}/orders/{orderId}/products")
    public ResponseEntity<?> getCustomerOrderProducts(@PathVariable Long clientId, @PathVariable Long orderId) {
        String ids = clientId.toString() + "," + orderId.toString();
        rabbitMQSender.sendClientIdAndOrderId(orderId.toString());

        // Attendre la réception du message. Vous pouvez implémenter un mécanisme d'attente ou de délai ici.
        try {
            Thread.sleep(1000); // Attendre 1 seconde pour que le message soit reçu. Ajustez selon vos besoins.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String commandOfClient = rabbitMQReceiver.getReceivedMessage();
        return ResponseEntity.ok(commandOfClient);
    }*/
}
