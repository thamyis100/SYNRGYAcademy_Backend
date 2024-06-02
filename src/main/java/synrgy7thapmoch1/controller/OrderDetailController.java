package synrgy7thapmoch1.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import synrgy7thapmoch1.entity.OrderDetail;
import synrgy7thapmoch1.service.OrderDetailService;

import java.util.Map;

@RestController
@RequestMapping("/v1/orderdetail")
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderService;

    @PostMapping(value = {"/save", "/save/"})
    public ResponseEntity<Map> savemerchant(@RequestBody OrderDetail request) {
        return new ResponseEntity<>(orderService.save(request), HttpStatus.OK);
    }
}
