package synrgy7thapmoch1.testing;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import synrgy7thapmoch1.entity.Merchant;
import synrgy7thapmoch1.service.MerchantService;

import java.util.Map;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestingMerchant {

    @Autowired
    public MerchantService merchantService;

    @Test
    public void saveMerchant(){
        Merchant save = new Merchant();
        save.setMerchant_name("Warung Sate");
        save.setMerchant_location("Jalan Pahlawan No. 123");

        Map map =  merchantService.save(save);
        int responseCode = (Integer) map.get("status");
        Assert.assertEquals(200, responseCode);
    }

    @Test
    public void getMerchantById() {
        UUID merchantId = UUID.fromString("9bca81d7-cb36-4fad-8e86-0ca0092de5df");

        Map<String, Object> response = merchantService.getById(merchantId);

        int statusCode = (int) response.get("status");
        if (statusCode == 200) {
            Merchant merchant = (Merchant) response.get("data");
            Assert.assertNotNull(merchant);
            Assert.assertEquals(merchantId, merchant.getId());
        } else {
            Assert.assertEquals(404, statusCode);
            String errorMessage = (String) response.get("message");
            Assert.assertEquals("id_merchant not found", errorMessage);
        }
    }

    @Test
    public void getPagination(){
        Map map =  merchantService.pagination(0,5);
        System.out.println(map);
        int responseCode = (Integer) map.get("status");
        Assert.assertEquals(200, responseCode);
    }

    @Test
    public void editMerchant(){
        UUID merchantIdToEdit = UUID.fromString("9bca81d7-cb36-4fad-8e86-0ca0092de5df");

        Merchant editedMerchant = new Merchant();
        editedMerchant.setId(merchantIdToEdit);
        editedMerchant.setMerchant_name("Warung Bakso");
        editedMerchant.setMerchant_location("Jalan Ahmad Yani No. 456");

        Map<String, Object> editResponse = merchantService.edit(editedMerchant);

        Assert.assertEquals(200, editResponse.get("status"));
        Assert.assertEquals("Success", editResponse.get("message"));
    }

    @Test
    public void deleteMerchant(){
        UUID merchantIdToDelete = UUID.fromString("9bca81d7-cb36-4fad-8e86-0ca0092de5df");

        Map<String, Object> deleteResponse = merchantService.delete(merchantIdToDelete);

        Assert.assertEquals(200, deleteResponse.get("status"));
        Assert.assertEquals("Merchant deleted successfully", deleteResponse.get("message"));
    }
}
