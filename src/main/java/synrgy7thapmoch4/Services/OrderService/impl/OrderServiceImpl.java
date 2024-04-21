package synrgy7thapmoch4.Services.OrderService.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import synrgy7thapmoch4.Services.OrderService.Order;
import synrgy7thapmoch4.Services.OrderService.OrderRepository;
import synrgy7thapmoch4.Services.OrderService.OrderService;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order placeOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
