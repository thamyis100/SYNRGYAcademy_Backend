package synrgy7thapmoch1.service.impl;

import com.example.challenge6.entity.Merchant;
import com.example.challenge6.repository.MerchantRepository;
import com.example.challenge6.service.MerchantService;
import com.example.challenge6.utils.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServiceImplMerchant implements MerchantService {

    private static final Logger logger = LoggerFactory.getLogger(ServiceImplMerchant.class);

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private Response response;

    @Override
    public Map save(Merchant request) {
        try {
            if(response.chekNull(request.getMerchant_name())){
                return  response.error("Merchant Name is required.",402);
            }
            if(response.chekNull(request.getMerchant_location())){
                return  response.error("Merchant Location is required.",402);
            }
            if(response.chekNull(request.getOpen())){
                return  response.error("Open is required.",402);
            }
            return response.sukses(merchantRepository.save(request));

        } catch (Exception e) {
            logger.error("Error saving merchant_id", e);
            return response.error("Failed to save merchant_id.", 500);
        }
    }

    @Override
    public Map edit(Merchant request) {
        try{
            if(request.getId() == null){
                return response.error("id_merchant is required.", 402);
            }

            Optional<Merchant> getMerchant = merchantRepository.findById(request.getId());
            if(getMerchant.isEmpty()){
                return response.error("id_merchant not found", 404);
            }

            Merchant editMerchant = getMerchant.get();
            if (request.getMerchant_name() != null) {
                editMerchant.setMerchant_name(request.getMerchant_name());
            }
            if (request.getMerchant_location()!= null) {
                editMerchant.setMerchant_location(request.getMerchant_location());
            }
            if (request.getOpen() != null) {
                editMerchant.setOpen(request.getOpen());
            }

            return response.sukses(merchantRepository.save(editMerchant));
        } catch (Exception e) {
            logger.error("Error while editing merchant_id: {}", e.getMessage());
            return response.error("Failed to edit merchant_id", 500);
        }
    }

    @Override
    public Map delete(UUID id) {
        try {
            Optional<Merchant> merchantOptional = merchantRepository.findById(id);
            if (merchantOptional.isPresent()) {
                Merchant merchant = merchantOptional.get();
                merchantRepository.delete(merchant);
                return response.sukses("Merchant deleted successfully");
            } else {
                return response.error("Merchant not found", 404);
            }
        } catch (Exception e) {
            logger.error("Error while deleting merchant_id: %s".formatted(e.getMessage()));
            return response.error("Failed to delete merchant_id", 500);
        }
    }

    @Override
    public Map getById(UUID id) {
        try {
            Optional<Merchant> getMerchant = merchantRepository.findById(id);
            if(getMerchant.isPresent()){
                return response.sukses(getMerchant.get());
            } else {
                return response.error("id_merchant not found", 404);
            }
        } catch (Exception e) {
            logger.error("Error occurred while fetching merchant_id by ID: {}", e.getMessage());
            return response.error("Failed to fetch merchant_id by ID", 500);
        }
    }

    @Override
    public Map pagination(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Merchant> listMerchant = merchantRepository.getAllDataPage(pageable);
            return response.sukses(listMerchant);
        } catch (Exception e) {
            logger.error("Error occurred while paginating merchants: {}", e.getMessage());
            return response.error("Failed to paginate merchants", 500);
        }
    }
}
