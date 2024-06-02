package synrgy7thapmoch1.service;

import com.example.challenge6.entity.Order;

import java.util.Map;
import java.util.UUID;

public interface OrderService {
    Map save(Order request);
    Map edit(Order request);
    Map delete(UUID id);
    Map getById(UUID id);
    Map pagination(int page, int size);

}
