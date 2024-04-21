package synrgy7thapmoch4;

// ProductServiceImplTest.java
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import synrgy7thapmoch4.Services.ProductService.ProductService;
import synrgy7thapmoch4.Services.ProductService.impl.ProductServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ProductServiceImplTest {
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        productService = new ProductServiceImpl();
    }

    @Test
    public void testAddProduct() {
        Product product = new Product(1, "Test Product", 10.0);
        productService.addProduct(product);
        Product retrievedProduct = productService.getProductById(1);
        assertEquals(product, retrievedProduct);
    }

    @Test
    public void testGetProductById() {
        Product product = new Product(2, "Another Product", 20.0);
        productService.addProduct(product);
        Product retrievedProduct = productService.getProductById(2);
        assertEquals(product, retrievedProduct);
    }

    @Test
    public void testGetProductById_NotFound() {
        Product retrievedProduct = productService.getProductById(3);
        assertNull(retrievedProduct);
    }
}
