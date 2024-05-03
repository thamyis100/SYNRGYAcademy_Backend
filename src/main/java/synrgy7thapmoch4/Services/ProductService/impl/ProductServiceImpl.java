package synrgy7thapmoch4.Services.ProductService.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import synrgy7thapmoch4.Entity.Product;
import synrgy7thapmoch4.Repository.ProductRepository;
import synrgy7thapmoch4.Services.ProductService.ProductService;
import synrgy7thapmoch4.utils.Response;

import java.util.Map;
import java.util.Optional;

// ProductServiceImpl.java
public class ProductServiceImpl implements ProductService {
    @Autowired
    private Response response;
    @Autowired
    private ProductRepository ProductRepository;

    @Override
    public Map save(Product request) {
        /*
        1. validasi field apa : Wajib sesuai flow bisnis
         */
        if(response.chekNull(request.getName())){
            return  response.error("Name is required.",402);
        }
        if(StringUtils.isEmpty(request.getName())){
            return  response.error("Name is required.",402);
        }

        return response.sukses(ProductRepository.save(request));
    }

    @Override
    public Map edit(Product request) {
        try {
        /*
        1. get id  -> cehk data ke db, ?
        2. save
         */
            if(response.chekNull(request.getId())){
                return  response.error("Id is required.",402);
            }

            Optional<Product> getId = ProductRepository.findById(request.getId());
            if(!getId.isPresent()){
                return response.error("id not found", 404);
            }

            Product edit = getId.get();
            edit.setPrice(request.getPrice());
            edit.setName(request.getName());
            edit.setMerchant_id(request.getMerchant_id());

            return response.sukses(ProductRepository.save(edit));
        }catch (Exception e){
            return response.error(e.getMessage(), 500);
        }
    }

    @Override
    public Map delete(Product request) {
        if (request.getId() == null) {
            return response.error("Id is required.", 402);
        }

        Optional<Product> optionalProduct = ProductRepository.findById(request.getId());
        if (optionalProduct.isPresent()) {
            ProductRepository.delete(optionalProduct.get());
            return response.sukses("Product deleted successfully.");
        } else {
            return response.error("Product not found.", 404);
        }
    }

    @Override
    public Map listAllProduct() {
        Iterable<Product> products = ProductRepository.findAll();
        return response.sukses(products);
    }


}
