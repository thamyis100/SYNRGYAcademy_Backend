package synrgy7thapmoch1.service.oauth;


import synrgy7thapmoch1.req.LoginModel;
import synrgy7thapmoch1.req.RegisterModel;

import java.security.Principal;
import java.util.Map;

public interface UserService {
    Map registerManual(RegisterModel objModel);

    Map registerByGoogle(RegisterModel objModel);

    Map getDetailProfile(Principal principal);

    Map login(LoginModel objLogin);
}
