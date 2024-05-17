package synrgy7thapmoch4.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import synrgy7thapmoch4.Entity.Order;
import synrgy7thapmoch4.Entity.User;
import synrgy7thapmoch4.Services.OrderService;
import synrgy7thapmoch4.Services.UserService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @PostMapping("/place")
    public ResponseEntity<Map> placeOrder(@RequestBody Order request) {
        try {
            // Fetch the User entity from the database using its ID
            User user = userService.readUser(request.getUser_id().getId());

            // Set the fetched User entity in the Order entity
            request.setUser_id(user);

            // Place the order
            Map result = orderService.placeOrder(request);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            // Handle any exceptions
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/list")
    public ResponseEntity<Map> listAllOrders() {
        Map result = orderService.listAllOrder();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
