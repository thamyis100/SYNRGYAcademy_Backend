package synrgy7thapmoch1.controller;

import com.example.challenge6.entity.Product;
import com.example.challenge6.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public ResponseEntity<Map> getListProduct(@RequestParam(value = "page", defaultValue = "0") int page,
                                              @RequestParam(value = "size", defaultValue = "10") int size) {
        return new ResponseEntity<>(productService.pagination(page, size), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map> getProductById(@PathVariable UUID id) {
        Map productResponse = productService.getById(id);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Map> saveProduct(@RequestBody Product request) {
        Map productResponse = productService.save(request);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Map> editProduct(@PathVariable UUID id, @RequestBody Product product) {
        product.setId(id);
        Map productResponse = productService.edit(product);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map> deleteProduct(@PathVariable UUID id) {
        Map productResponse = productService.delete(id);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }
}
