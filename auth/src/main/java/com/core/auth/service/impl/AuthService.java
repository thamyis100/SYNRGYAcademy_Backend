package com.core.auth.service.impl;

import com.core.auth.dto.auth.*;
import com.core.auth.service.IAuthService;
import com.core.auth.service.IUserService;
import com.core.auth.dto.auth.*;
import com.core.auth.entity.oauth.Role;
import com.core.auth.entity.oauth.User;
import com.core.auth.repository.oauth.IRoleRepository;
import com.core.auth.repository.oauth.IUserRepository;
import com.core.auth.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class AuthService implements IAuthService {

    @Value("${LOGINURL:}")
    private String LOGINURL;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private IUserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Override
    @Transactional
    public String register(RegisterRequest request, Boolean isEnabled) {
        if (userRepository.checkExistingEmail(request.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "can't use an existing username");
        }

        String[] userRole = {"ROLE_USER"};
        String[] adminRole = {"ROLE_ADMIN"};
        String[] pickedRole = userRole;
        if (request.getRoleName() != null && request.getRoleName().equalsIgnoreCase("ADMIN")) pickedRole = adminRole;

        User user = new User();
        user.setUsername(request.getUsername().toLowerCase());
        user.setFullname(request.getFullname());

        user.setEnabled(isEnabled);

        String password = encoder.encode(request.getPassword().replaceAll("\\s+", ""));
        List<Role> roles = roleRepository.findByNameIn(pickedRole);

        user.setRoles(roles);
        user.setPassword(password);

        if (!isEnabled) {
            emailService.setupAndSendEmail(user, "REGISTER");
        }

        userRepository.save(user);

        return isEnabled ? "user created successfully." : "user created successfully, please check your email for activation.";

    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findOneByUsername(request.getEmail()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "bad credentials"));

        if (!user.isEnabled()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "account is disabled");

        if (!encoder.matches(request.getPassword(), user.getPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "bad credentials");

        String url = LOGINURL + "?username=" + request.getEmail() +
                "&password=" + request.getPassword() +
                "&grant_type=password" +
                "&client_id=my-client-web" +
                "&client_secret=password";

        ResponseEntity<Map> response = restTemplateBuilder.build().exchange(url, HttpMethod.POST, null, new ParameterizedTypeReference<Map>() {
        });

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new ResponseStatusException(response.getStatusCode(), (String) Objects.requireNonNull(response.getBody()).get("error_description"));
        }

        return LoginResponse.builder()
                .access_token((String) Objects.requireNonNull(response.getBody()).get("access_token"))
                .refresh_token((String) Objects.requireNonNull(response.getBody()).get("refresh_token"))
                .expires_in(Long.valueOf((Integer) Objects.requireNonNull(response.getBody()).get("expires_in")))
                .token_type((String) Objects.requireNonNull(response.getBody()).get("token_type"))
                .scope((String) Objects.requireNonNull(response.getBody()).get("scope"))
                .jti((String) Objects.requireNonNull(response.getBody()).get("jti"))
                .message(null)
                .build();
    }

    @Override
    @Transactional
    public void confirmOTP(String otp) {
        if (!Util.validateOTPFormat(otp)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "otp is invalid");

        User userByOTP = userRepository.findOneByOTP(otp).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "otp is invalid"));

        if (userByOTP.isEnabled())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "account has been activated");

        if (userByOTP.getOtpExpiredDate().before(new Date()))
            throw new ResponseStatusException(HttpStatus.REQUEST_TIMEOUT, "otp is invalid");

        userByOTP.setEnabled(true);
        userByOTP.setOtp(null);
        userByOTP.setOtpExpiredDate(null);
        userRepository.save(userByOTP);
    }

    @Override
    public void forgetPassword(ForgetPasswordRequest request) {
        User user = userRepository.checkExistingEmail(request.getEmail()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "email not found"));

        if (!user.isEnabled())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "account inactive, please activate your account");

        emailService.setupAndSendEmail(user, "FORGET_PASSWORD");
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordRequest request) {
        if (!Util.validateOTPFormat(request.getOtp()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid otp format");

        User user = userRepository.findOneByOTP(request.getOtp()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "otp is invalid"));

        if (user.getOtpExpiredDate().before(new Date()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "otp has expired");

        if (!request.getNewPassword().equals(request.getNewConfirmPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid password");

        if (!user.isEnabled())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "account inactive, please activate your account");

        if (encoder.matches(request.getNewPassword(), user.getPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "password has been used before");

        user.setPassword(encoder.encode(request.getNewPassword()));
        user.setOtp(null);
        user.setOtpExpiredDate(null);
        userRepository.save(user);
    }
}
