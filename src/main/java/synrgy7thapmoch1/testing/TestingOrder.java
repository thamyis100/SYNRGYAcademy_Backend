package synrgy7thapmoch1.testing;

import com.example.challenge6.entity.Order;
import com.example.challenge6.service.impl.ServiceImplOrder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestingOrder {

    @Autowired
    private ServiceImplOrder orderService;

    @Test
    public void saveOrderWithRequiredFields() {
        Order save = new Order();
        save.setOrder_time(new Date());
        save.setDestination_address("123 Main St");
        save.setCompleted(false);

        Map<String, Object> orderSaveResponse = orderService.save(save);
        int orderSaveStatusCode = (int) orderSaveResponse.get("status");
        Assert.assertEquals(200, orderSaveStatusCode);
    }

    @Test
    public void editOrder() {
        UUID orderId = UUID.fromString("9bca81d7-cb36-4fad-8e86-0ca0092de5df");

        Map<String, Object> getOrderResponse = orderService.getById(orderId);
        int getOrderStatusCode = (int) getOrderResponse.get("status");
        Assert.assertEquals(200, getOrderStatusCode);

        if (getOrderStatusCode == 200) {
            Order existingOrder = (Order) getOrderResponse.get("data");
            existingOrder.setOrder_time(new Date());
            existingOrder.setDestination_address("456 Elm St");
            existingOrder.setCompleted(true);

            Map orderUpdateResponse = orderService.edit(existingOrder);
            int orderUpdateStatusCode = (int) orderUpdateResponse.get("status");
            Assert.assertEquals(200, orderUpdateStatusCode);
        }
    }

    @Test
    public void deleteOrder() {
        UUID orderId = UUID.fromString("9bca81d7-cb36-4fad-8e86-0ca0092de5df");

        Map orderDeleteResponse = orderService.delete(orderId);
        int orderDeleteStatusCode = (int) orderDeleteResponse.get("status");
        Assert.assertEquals(200, orderDeleteStatusCode);
    }

    @Test
    public void getOrderById() {
        UUID orderId = UUID.fromString("9bca81d7-cb36-4fad-8e86-0ca0092de5df");

        Map<String, Object> getOrderResponse = orderService.getById(orderId);
        int getOrderStatusCode = (int) getOrderResponse.get("status");
        Assert.assertEquals(200, getOrderStatusCode);
    }
}
