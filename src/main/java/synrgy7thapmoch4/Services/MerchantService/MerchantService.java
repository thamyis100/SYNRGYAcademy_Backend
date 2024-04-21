package synrgy7thapmoch4.Services.MerchantService;

import java.util.List;

public interface MerchantService {
    Merchant addMerchant(Merchant merchant);

    Merchant updateMerchant(Merchant merchant);

    void deleteMerchant(int merchantId);

    List<Merchant> getOpenMerchants();
}
