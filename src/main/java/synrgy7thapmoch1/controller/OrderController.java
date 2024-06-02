package synrgy7thapmoch1.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import synrgy7thapmoch1.entity.Order;
import synrgy7thapmoch1.service.OrderService;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public ResponseEntity<Map> getListOrder(@RequestParam(value = "page", defaultValue = "0") int page,
                                              @RequestParam(value = "size", defaultValue = "10") int size) {
        return new ResponseEntity<>(orderService.pagination(page, size), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map> getOrderById(@PathVariable UUID id) {
        Map orderResponse = orderService.getById(id);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Map> saveOrder(@RequestBody Order request) {
        Map orderResponse = orderService.save(request);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Map> editOrder(@PathVariable UUID id, @RequestBody Order order) {
        order.setId(id);
        Map orderResponse = orderService.edit(order);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map> deleteOrder(@PathVariable UUID id) {
        Map orderResponse = orderService.delete(id);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }
}
