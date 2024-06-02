package synrgy7thapmoch1.service.oauth;

import com.example.challenge6.dao.req.LoginModel;
import com.example.challenge6.dto.req.RegisterModel;

import java.security.Principal;
import java.util.Map;

public interface UserService {
    Map registerManual(RegisterModel objModel);

    Map registerByGoogle(RegisterModel objModel);

    Map getDetailProfile(Principal principal);

    Map login(LoginModel objLogin);
}
