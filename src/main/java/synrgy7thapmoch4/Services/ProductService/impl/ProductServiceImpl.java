package synrgy7thapmoch4.Services.ProductService.impl;


import synrgy7thapmoch4.Entity.Product;
import synrgy7thapmoch4.Services.ProductService.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// ProductServiceImpl.java
public class ProductServiceImpl implements ProductService {
    static List<Product> listproduct = new ArrayList<>();

    @Override
    public Product addProduct(Product product) {
        listproduct.add(product);
        return product;
    }

    @Override
    public Product updateProduct(Product product) {
        for (Product data : listproduct){
            if (product.getId() == data.getId()){
                Product update = new Product();
                update.setId(data.getId());
                update.setName(data.getName());
                update.setPrice(data.getPrice());
                update.setMerchant_id(data.getMerchant_id());
                listproduct.remove(data);
                listproduct.add(update);
                return update;
            }
        }
        return null;
    }

    @Override
    public List<Product> deleteProduct(UUID productId) {
        for(Product data : listproduct){
            if(productId.equals(data.getId())){
                Product update = new Product();
                update.setName(data.getName());
                update.setId(data.getId());
                update.setPrice(data.getPrice());
                update.setMerchant_id(data.getMerchant_id());
                listproduct.remove(data);

                return listproduct;
            }
        }
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return listproduct;
    }
}
