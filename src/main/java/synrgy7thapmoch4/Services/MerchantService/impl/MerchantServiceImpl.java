package synrgy7thapmoch4.Services.MerchantService.impl;

import org.springframework.stereotype.Service;
import synrgy7thapmoch4.Entity.Merchant;
import synrgy7thapmoch4.Services.MerchantService.MerchantService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MerchantServiceImpl implements MerchantService {
    static List<Merchant> listmerchant = new ArrayList<>();

    @Override
    public Merchant addMerchant(Merchant merchant) {

        listmerchant.add(merchant);
        return  merchant;

    }

    @Override
    public Merchant updateMerchant(Merchant merchant) {
        for (Merchant data : listmerchant){
            if (merchant.getId() == data.getId()){
                Merchant update = new Merchant();
                update.setId(data.getId());
                update.setName(data.getName());
                update.setLocation(data.getLocation());
                update.setOpen(data.isOpen());
                listmerchant.remove(data);
                listmerchant.add(update);
                return update;
            }
        }
        return null;
    }


    public List<Merchant> deleteMerchant(UUID merchantId) {
        for(Merchant data : listmerchant){
            if(merchantId.equals(data.getId())){
                Merchant update = new Merchant();
                update.setName(data.getName());
                update.setId(data.getId());
                update.setLocation(data.getLocation());
                update.setOpen(data.isOpen());
                listmerchant.remove(data);

                return listmerchant;
            }
        }
        return null;

    }

    @Override
    public List<Merchant> getOpenMerchants() {
        return listmerchant;
    }
}
