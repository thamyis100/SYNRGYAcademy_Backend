package synrgy7thapmoch1.service;

import com.example.challenge6.entity.Merchant;

import java.util.Map;
import java.util.UUID;

public interface MerchantService {
    Map save(Merchant request);
    Map edit(Merchant request);
    Map delete(UUID id);
    Map getById(UUID id);
    Map pagination(int page, int size);
}
