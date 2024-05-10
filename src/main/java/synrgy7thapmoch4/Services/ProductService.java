package synrgy7thapmoch4.Services;

import synrgy7thapmoch4.Entity.Product;

import java.util.Map;

// ProductService.java

public interface ProductService {
    Map save(Product request);
    Map edit(Product request);
    Map delete(Product request);

    Map listAllProduct();
}
