package synrgy7thapmoch4.Services.ProductService;

import java.util.List;

// ProductService.java
public interface ProductService {
    Product addProduct(Product product);
    Product updateProduct(Product product);
    void deleteProduct(int productId);
    List<Product> getAllProducts();
}
