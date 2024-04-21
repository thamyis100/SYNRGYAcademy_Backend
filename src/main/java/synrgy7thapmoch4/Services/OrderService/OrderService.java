package synrgy7thapmoch4.Services.OrderService;

import java.util.List;

public interface OrderService {
    Order placeOrder(Order order);

    List<Order> getAllOrders();
}
