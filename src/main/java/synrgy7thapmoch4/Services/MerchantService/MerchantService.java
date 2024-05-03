package synrgy7thapmoch4.Services.MerchantService;

import synrgy7thapmoch4.Entity.Merchant;

import java.util.List;
import java.util.UUID;

public interface MerchantService {
    Merchant addMerchant(Merchant merchant);

    Merchant updateMerchant(Merchant merchant);

    List<Merchant> deleteMerchant(UUID merchantId);

    List<Merchant> getOpenMerchants();
}
