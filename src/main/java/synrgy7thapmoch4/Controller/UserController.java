package synrgy7thapmoch4.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import synrgy7thapmoch4.Entity.User;
import synrgy7thapmoch4.Services.UserService;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(value = {"/save", "/save/"})
    public ResponseEntity<Map> saveUser(@RequestBody User request) {
        return new ResponseEntity<>(userService.save(request), HttpStatus.OK);
    }

    @PutMapping(value = {"/update", "/update/"})
    public ResponseEntity<Map> updateUser(@RequestBody User request) {
        return new ResponseEntity<>(userService.edit(request), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/deleted", "/deleted/"})
    public ResponseEntity<Map> deleteUser(@RequestBody User request) {
        return new ResponseEntity<>(userService.delete(request), HttpStatus.OK);
    }

    @DeleteMapping(value = {"/deleted/{id}", "/deleted/{id}"})
    public ResponseEntity<Map> deleteUserById(@PathVariable UUID id) {
        //validate notnull
        User emp=  new User();
        emp.setId(id);
        return new ResponseEntity<>(userService.delete(emp), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<Map> listAllUsers() {
        Map result = userService.listAllUser();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}