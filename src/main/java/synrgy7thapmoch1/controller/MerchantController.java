package synrgy7thapmoch1.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import synrgy7thapmoch1.entity.Merchant;
import synrgy7thapmoch1.service.MerchantService;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/merchant")
public class MerchantController {
    @Autowired
    private MerchantService merchantService;

    @GetMapping("/list")
    public ResponseEntity<Map> getListMerchant(@RequestParam(value = "page", defaultValue = "0") int page,
                                              @RequestParam(value = "size", defaultValue = "10") int size) {
        return new ResponseEntity<>(merchantService.pagination(page, size), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map> getMerchantById(@PathVariable UUID id) {
        Map merchantResponse = merchantService.getById(id);
        return new ResponseEntity<>(merchantResponse, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Map> saveMerchant(@RequestBody Merchant request) {
        Map merchantResponse = merchantService.save(request);
        return new ResponseEntity<>(merchantResponse, HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Map> editMerchant(@PathVariable UUID id, @RequestBody Merchant merchant) {
        merchant.setId(id);
        Map merchantResponse = merchantService.edit(merchant);
        return new ResponseEntity<>(merchantResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map> deleteMerchant(@PathVariable UUID id) {
        Map merchantResponse = merchantService.delete(id);
        return new ResponseEntity<>(merchantResponse, HttpStatus.OK);
    }
}
