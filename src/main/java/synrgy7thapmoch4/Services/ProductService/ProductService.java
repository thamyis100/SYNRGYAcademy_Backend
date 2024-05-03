package synrgy7thapmoch4.Services.ProductService;

import synrgy7thapmoch4.Entity.Product;

import java.util.List;
import java.util.UUID;

// ProductService.java
public interface ProductService {
    Product addProduct(Product product);
    Product updateProduct(Product product);
    List<Product> deleteProduct(UUID productId);
    List<Product> getAllProducts();
}
