package com.core.auth.service;

import com.core.auth.dto.auth.*;
import com.core.auth.dto.auth.*;

public interface IAuthService {
    String register(RegisterRequest request, Boolean isEnabled);

    LoginResponse login(LoginRequest request);

    void confirmOTP(String otp);

    void forgetPassword(ForgetPasswordRequest request);

    void changePassword(ChangePasswordRequest request);
}
