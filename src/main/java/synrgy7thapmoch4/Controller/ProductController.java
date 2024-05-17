package synrgy7thapmoch4.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import synrgy7thapmoch4.Entity.Merchant;
import synrgy7thapmoch4.Entity.Product;
import synrgy7thapmoch4.Services.MerchantService;
import synrgy7thapmoch4.Services.ProductService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private MerchantService merchantService;

    @PostMapping(value = {"/save", "/save/"})
    public ResponseEntity<Map> saveproduct(@RequestBody Product request) {try {
        // Fetch the merchant entity from the database using its ID
        Merchant merchant = merchantService.readmerchant(request.getMerchant_id().getId());

        // Set the fetched merchant entity in the Order entity
        request.setMerchant_id(merchant);

        // Place the order
        Map result = productService.save(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    } catch (Exception e) {
        // Handle any exceptions
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("message", e.getMessage());
        errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }

    @PutMapping(value = {"/update", "/update/"})
    public ResponseEntity<Map> updateproduct(@RequestBody Product request) {
        return new ResponseEntity<>(productService.edit(request), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/deleted", "/deleted/"})
    public ResponseEntity<Map> deleteproduct(@RequestBody Product request) {
        return new ResponseEntity<>(productService.delete(request), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/deleted/{id}", "/deleted/{id}"})
    public ResponseEntity<Map> deleteproductById(@PathVariable UUID id) {
        //validate notnull
        Product emp=  new Product();
        emp.setId(id);
        return new ResponseEntity<>(productService.delete(emp), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<Map> listAllProducts() {
        Map result = productService.listAllProduct();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
