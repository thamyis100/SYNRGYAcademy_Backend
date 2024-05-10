package synrgy7thapmoch4.Services;

import synrgy7thapmoch4.Entity.Order;

import java.util.Map;

public interface OrderService {
    Map placeOrder(Order request);

    Map listAllOrder();
}
