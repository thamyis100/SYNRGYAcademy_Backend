package synrgy7thapmoch1.controller;

import com.example.challenge6.entity.UserOld;
import com.example.challenge6.repository.UserRepositoryOld;
import com.example.challenge6.service.UserServiceOld;
import com.example.challenge6.utils.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/user")
public class UserControllerOld {
    @Autowired
    private UserServiceOld userService;

    @Autowired
    private UserRepositoryOld userRepository;

    @Autowired
    public Response response;

    private static final Logger logger = LoggerFactory.getLogger(UserControllerOld.class);

    @GetMapping("/list")
    public ResponseEntity<Map> getListUser(@RequestParam(required = false, name = "username") String username,
                                           @RequestParam(required = false, name = "email_address") String email_address,
                                           @PageableDefault(page = 0, size = 10, sort = "null") Pageable pageable) {
        Specification<UserOld> spec = ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (username != null && !username.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), "%" + username.toLowerCase() + "%"));
            }
            if (email_address != null && !email_address.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email_address")), "%" + email_address.toLowerCase() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });

        Page<UserOld> userList = userRepository.findAll(spec, pageable);
        return new ResponseEntity<>(response.sukses(userList), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map> getUserById(@PathVariable UUID id) {
        Map userResponse = userService.getById(id);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Map> saveUser(@RequestBody UserOld request) {
        Map userResponse = userService.save(request);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Map> editUser(@PathVariable UUID id, @RequestBody UserOld user) {
        user.setId(id);
        Map userResponse = userService.edit(user);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map> deleteUser(@PathVariable UUID id) {
        Map userResponse = userService.delete(id);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }
}
