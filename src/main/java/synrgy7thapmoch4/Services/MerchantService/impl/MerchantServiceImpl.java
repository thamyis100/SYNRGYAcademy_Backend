package synrgy7thapmoch4.Services.MerchantService.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import synrgy7thapmoch4.Entity.Merchant;
import synrgy7thapmoch4.Repository.MerchantRepository;
import synrgy7thapmoch4.Services.MerchantService.MerchantService;
import synrgy7thapmoch4.utils.Response;

import java.util.Map;
import java.util.Optional;

@Service
public class MerchantServiceImpl implements MerchantService {
    @Autowired
    private Response response;
    @Autowired
    private MerchantRepository MerchantRepository;

    @Override
    public Map save(Merchant request) {
        /*
        1. validasi field apa : Wajib sesuai flow bisnis
         */
        if(response.chekNull(request.getName())){
            return  response.error("Name is required.",402);
        }
        if(StringUtils.isEmpty(request.getName())){
            return  response.error("Name is required.",402);
        }

        return response.sukses(MerchantRepository.save(request));
    }

    @Override
    public Map edit(Merchant request) {
        try {
        /*
        1. get id  -> cehk data ke db, ?
        2. save
         */
            if(response.chekNull(request.getId())){
                return  response.error("Id is required.",402);
            }

            Optional<Merchant> getId = MerchantRepository.findById(request.getId());
            if(!getId.isPresent()){
                return response.error("id not found", 404);
            }

            Merchant edit = getId.get();
            edit.setLocation(request.getLocation());
            edit.setName(request.getName());
            edit.setOpen(request.isOpen());

            return response.sukses(MerchantRepository.save(edit));
        }catch (Exception e){
            return response.error(e.getMessage(), 500);
        }
    }

    @Override
    public Map delete(Merchant request) {
        if (request.getId() == null) {
            return response.error("Id is required.", 402);
        }

        Optional<Merchant> optionalMerchant = MerchantRepository.findById(request.getId());
        if (optionalMerchant.isPresent()) {
            MerchantRepository.delete(optionalMerchant.get());
            return response.sukses("Merchant deleted successfully.");
        } else {
            return response.error("Merchant not found.", 404);
        }
    }

    @Override
    public Map listByOpen(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Merchant> openMerchants = MerchantRepository.findByOpen(true, pageable);
        return response.sukses(openMerchants);
    }
}
