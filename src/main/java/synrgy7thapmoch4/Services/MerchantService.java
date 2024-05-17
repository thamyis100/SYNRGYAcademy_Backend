package synrgy7thapmoch4.Services;

import synrgy7thapmoch4.Entity.Merchant;

import java.util.Map;
import java.util.UUID;

public interface MerchantService {
    Map save(Merchant request);
    Map edit(Merchant request);
    Map delete(Merchant request);

    Map listByOpen(int page, int size);

    Merchant readmerchant(UUID id);
}
