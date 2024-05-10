package synrgy7thapmoch4.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import synrgy7thapmoch4.Entity.Merchant;
import synrgy7thapmoch4.Services.MerchantService;
import synrgy7thapmoch4.utils.Response;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/merchant")
public class MerchantController {
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private Response response;

    @PostMapping(value = {"/save", "/save/"})
    public ResponseEntity<Map> savemerchant(@RequestBody Merchant request) {
        return new ResponseEntity<>(merchantService.save(request), HttpStatus.OK);
    }

    @PutMapping(value = {"/update", "/update/"})
    public ResponseEntity<Map> updatemerchant(@RequestBody Merchant request) {
        return new ResponseEntity<>(merchantService.edit(request), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/deleted", "/deleted/"})
    public ResponseEntity<Map> deletemerchant(@RequestBody Merchant request) {
        return new ResponseEntity<>(merchantService.delete(request), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/deleted/{id}", "/deleted/{id}"})
    public ResponseEntity<Map> deletemerchantById(@PathVariable UUID id) {
        //validate notnull
        Merchant emp=  new Merchant();
        emp.setId(id);
        return new ResponseEntity<>(merchantService.delete(emp), HttpStatus.OK);
    }
    @GetMapping(value = {"/list-merchant", "/list-merchant/"})
    public ResponseEntity<Map> getListMerchant() {
        return new ResponseEntity<>(merchantService.listByOpen(0,10), HttpStatus.OK);
    }
}
