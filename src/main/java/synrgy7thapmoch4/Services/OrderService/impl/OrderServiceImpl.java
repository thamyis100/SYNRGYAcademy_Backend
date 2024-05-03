package synrgy7thapmoch4.Services.OrderService.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import synrgy7thapmoch4.Entity.Order;
import synrgy7thapmoch4.Repository.OrderRepository;
import synrgy7thapmoch4.Services.OrderService.OrderService;
import synrgy7thapmoch4.utils.Response;

import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private Response response;
    @Autowired
    private OrderRepository OrderRepository;
    @Override
    public Map placeOrder(Order request) {
        try {
            // Place order logic
            Order savedOrder = OrderRepository.save(request);
            return response.sukses(savedOrder);
        } catch (Exception e) {
            return response.error(e.getMessage(), 500);
        }
    }

    @Override
    public Map listAllOrder() {
        try {
            List<Order> orders = OrderRepository.findAll();
            return response.sukses(orders);
        } catch (Exception e) {
            return response.error(e.getMessage(), 500);
        }
    }
}
