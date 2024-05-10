package synrgy7thapmoch4.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import synrgy7thapmoch4.Entity.User;
import synrgy7thapmoch4.Services.UserService;
import synrgy7thapmoch4.utils.Response;

import java.util.Map;

@RestController
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private Response response;

    @PostMapping(value = {"/save", "/save/"})
    public ResponseEntity<Map> createUser(@RequestParam Long userId, @RequestParam String userName, @RequestParam String userEmail) {
        userService.createUser(userId, userName, userEmail);
        return new ResponseEntity<>(response.sukses("User created successfully."), HttpStatus.CREATED);
    }

    @GetMapping(value = {"/{userId}", "/{userId}/"})
    public ResponseEntity<Map> getUser(@PathVariable Long userId) {
        User user = userService.readUser(userId);
        if (user != null) {
            return new ResponseEntity<>(response.sukses(user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response.error("User not found.", 404), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = {"/updata", "/update/"})
    public ResponseEntity<Map> updateUser(@PathVariable Long userId, @RequestParam String userName, @RequestParam String userEmail) {
        userService.updateUser(userId, userName, userEmail);
        return new ResponseEntity<>(response.sukses("User updated successfully."), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/deleted", "/deleted/"})
    public ResponseEntity<Map> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(response.sukses("User deleted successfully."), HttpStatus.OK);
    }
}