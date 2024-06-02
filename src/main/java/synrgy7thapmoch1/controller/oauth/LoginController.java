package synrgy7thapmoch1.controller.oauth;

import com.example.challenge6.config.Config;
import com.example.challenge6.dao.req.LoginModel;
import com.example.challenge6.repository.oauth.UserRepository;
import com.example.challenge6.service.oauth.UserService;
import com.example.challenge6.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/user-login/")
public class LoginController {
    @Autowired
    private UserRepository userRepository;

    Config config = new Config();

    @Autowired
    public UserService serviceReq;

    @Autowired
    public Response templateCRUD;

    @PostMapping("/login")
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map> login(@Valid @RequestBody LoginModel objModel) {
        Map map = serviceReq.login(objModel);
        return new ResponseEntity<Map>(map, HttpStatus.OK);
    }

}
