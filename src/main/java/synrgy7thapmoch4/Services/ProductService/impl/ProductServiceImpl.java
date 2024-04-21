package synrgy7thapmoch4.Services.ProductService.impl;

import org.springframework.beans.factory.annotation.Autowired;
import synrgy7thapmoch4.Services.ProductService.Product;
import synrgy7thapmoch4.Services.ProductService.ProductRepository;
import synrgy7thapmoch4.Services.ProductService.ProductService;

import java.util.List;

// ProductServiceImpl.java
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(int productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}
