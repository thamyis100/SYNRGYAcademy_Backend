package synrgy7thapmoch4.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import synrgy7thapmoch4.Entity.Order;
import synrgy7thapmoch4.Services.OrderService;

import java.util.Map;

@RestController
@RequestMapping("/v1/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<Map> placeOrder(@RequestBody Order request) {
        Map result = orderService.placeOrder(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<Map> listAllOrders() {
        Map result = orderService.listAllOrder();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
