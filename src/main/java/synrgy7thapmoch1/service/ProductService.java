package synrgy7thapmoch1.service;



import synrgy7thapmoch1.entity.Product;

import java.util.Map;
import java.util.UUID;

public interface ProductService {
    Map save(Product request);
    Map edit(Product request);
    Map delete(UUID id);
    Map getById(UUID id);
    Map pagination(int page, int size);
}
