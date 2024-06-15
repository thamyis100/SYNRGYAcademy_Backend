package synrgy7thapmoch1.service.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import synrgy7thapmoch1.entity.Merchant;
import synrgy7thapmoch1.entity.Product;
import synrgy7thapmoch1.repository.MerchantRepository;
import synrgy7thapmoch1.repository.ProductRepository;
import synrgy7thapmoch1.service.ProductService;
import synrgy7thapmoch1.utils.Response;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServiceImplProduct implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ServiceImplProduct.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private Response response;

    @Override
    public Map<String, Object> save(Product request) {
        try {
            Merchant managedMerchant = merchantRepository.findById(request.getMerchant().getId())
                    .orElseThrow(() -> new RuntimeException("Merchant not found"));

            request.setMerchant(managedMerchant);

            if (response.chekNull(request.getProduct_name())) {
                return response.error("Product Name is required.", 402);
            }
            if (response.chekNull(request.getPrice())) {
                return response.error("Price is required.", 402);
            }

            Product savedProduct = productRepository.save(request);
            return response.sukses(savedProduct);
        } catch (Exception e) {
            logger.error("Error saving product", e);
            return response.error("Failed to save product.", 500);
        }
    }

    @Override
    public Map edit(Product request) {
        try{
            if(request.getId() == null){
                return response.error("id_product is required.", 402);
            }

            Optional<Product> getProduct = productRepository.findById(request.getId());
            if(getProduct.isEmpty()){
                return response.error("id_product not found", 404);
            }

            Product editProduct = getProduct.get();
            if (request.getProduct_name() != null) {
                editProduct.setProduct_name(request.getProduct_name());
            }
            if (request.getPrice() != 0) {
                editProduct.setPrice(request.getPrice());
            }
            if (request.getMerchant() != null) {
                editProduct.setMerchant(request.getMerchant());
            }

            return response.sukses(productRepository.save(editProduct));
        } catch (Exception e) {
            logger.error("Error while editing product_id: {}", e.getMessage());
            return response.error("Failed to edit product_id", 500);
        }
    }

    @Override
    public Map delete(UUID id) {
        try {
            Optional<Product> productOptional = productRepository.findById(id);
            if (productOptional.isPresent()) {
                Product product = productOptional.get();
                productRepository.delete(product);
                return response.sukses("Product deleted successfully");
            } else {
                return response.error("Product not found", 404);
            }
        } catch (Exception e) {
            logger.error("Error while deleting product_id: %s".formatted(e.getMessage()));
            return response.error("Failed to delete product_id", 500);
        }
    }

    @Override
    public Map getById(UUID id) {
        try{
            Optional<Product> getId = productRepository.findById(id);
            if(getId.isEmpty()){
                return response.error("id_product not found", 404);
            }
            return response.sukses(getId.get());
        } catch (Exception e) {
            logger.error("Error occurred while fetching product_id by ID: {}", e.getMessage());
            return response.error("Failed to fetch product_id by ID", 500);
        }
    }

    @Override
    public Map pagination(int page, int size) {
        try{
            Pageable pageable = PageRequest.of(page, size);
            Page<Product> listProduct = productRepository.getAllDataPage(pageable);
            return response.sukses(listProduct);
        } catch (Exception e) {
            logger.error("Error occurred while paginating products: {}", e.getMessage());
            return response.error("Failed to paginate products", 500);
        }
    }
}
