package synrgy7thapmoch4.Services.OrderService.impl;

import org.springframework.stereotype.Service;
import synrgy7thapmoch4.Entity.Order;
import synrgy7thapmoch4.Services.OrderService.OrderService;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    static List<Order> listorder = new ArrayList<>();


    @Override
    public Order placeOrder(Order order) {
        listorder.add(order);
        return order;
    }

    @Override
    public List<Order> getAllOrders() {
        return listorder;
    }
}
