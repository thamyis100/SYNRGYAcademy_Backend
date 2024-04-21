package synrgy7thapmoch4.Services.MerchantService.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import synrgy7thapmoch4.Services.MerchantService.Merchant;
import synrgy7thapmoch4.Services.MerchantService.MerchantRepository;
import synrgy7thapmoch4.Services.MerchantService.MerchantService;

import java.util.List;

@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    private MerchantRepository merchantRepository;

    public Merchant addMerchant(Merchant merchant) {
        return merchantRepository.add(merchant);
    }

    @Override
    public Merchant updateMerchant(Merchant merchant) {
        return merchantRepository.save(merchant);
    }

    @Override
    public void deleteMerchant(int merchantId) {
        merchantRepository.deleteById(merchantId);
    }

    @Override
    public List<Merchant> getOpenMerchants() {
        // Implement logic to get open merchants
        return null;
    }
}
