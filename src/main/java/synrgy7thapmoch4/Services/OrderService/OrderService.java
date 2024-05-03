package synrgy7thapmoch4.Services.OrderService;

import synrgy7thapmoch4.Entity.Order;

import java.util.List;

public interface OrderService {
    Order placeOrder(Order order);

    List<Order> getAllOrders();
}
