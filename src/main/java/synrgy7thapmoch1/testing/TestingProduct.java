package synrgy7thapmoch1.testing;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import synrgy7thapmoch1.entity.Product;
import synrgy7thapmoch1.service.ProductService;

import java.util.Map;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestingProduct {

    @Autowired
    public ProductService productService;

    @Test
    public void saveProduct(){
        Product save = new Product();
        save.setProduct_name("Nasi Pecel");
        save.setPrice(10000);

        Map map =  productService.save(save);
        int responseCode = (Integer) map.get("status");
        Assert.assertEquals(200, responseCode);
    }

    @Test
    public void getProductById() {
        UUID productId = UUID.fromString("9bca81d7-cb36-4fad-8e86-0ca0092de5df");

        Map<String, Object> response = productService.getById(productId);

        int statusCode = (int) response.get("status");
        if (statusCode == 200) {
            Product product = (Product) response.get("data");
            Assert.assertNotNull(product);
            Assert.assertEquals(productId, product.getId());
        } else {
            Assert.assertEquals(404, statusCode);
            String errorMessage = (String) response.get("message");
            Assert.assertEquals("id_product not found", errorMessage);
        }
    }

    @Test
    public void getPagination(){
        Map map =  productService.pagination(0,5);
        System.out.println(map);
        int responseCode = (Integer) map.get("status");
        Assert.assertEquals(200, responseCode);
    }

    @Test
    public void editProduct(){
        UUID productIdToEdit = UUID.fromString("9bca81d7-cb36-4fad-8e86-0ca0092de5df");

        Product editedProduct = new Product();
        editedProduct.setId(productIdToEdit);
        editedProduct.setProduct_name("Nasi Uduk");
        editedProduct.setPrice(12000);

        Map<String, Object> editResponse = productService.edit(editedProduct);

        Assert.assertEquals(200, editResponse.get("status"));
        Assert.assertEquals("Success", editResponse.get("message"));
    }

    @Test
    public void deleteProduct(){
        UUID productIdToDelete = UUID.fromString("9bca81d7-cb36-4fad-8e86-0ca0092de5df");

        Map<String, Object> deleteResponse = productService.delete(productIdToDelete);

        Assert.assertEquals(200, deleteResponse.get("status"));
        Assert.assertEquals("Product deleted successfully", deleteResponse.get("message"));
    }
}
