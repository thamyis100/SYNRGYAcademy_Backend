package com.core.auth.controller;

import com.core.auth.dto.auth.*;
import com.core.auth.service.IAuthService;
import com.core.auth.dto.Response;
import com.core.auth.dto.auth.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @GetMapping(path = "/ping")
    public String hello(){
        return "Hello";
    }

    @PostMapping(path = {"/register", "/register/"})
    public Response<Object> registerManual(@Valid @RequestBody RegisterRequest request){
        String message = authService.register(request, false);

        return Response.<Object>builder()
                .status("OK")
                .message(message)
                .build();
    }

    @PostMapping(path = {"/login", "/login/"})
    public Response<LoginResponse> login(@Valid @RequestBody LoginRequest request){
        LoginResponse loginResponse = authService.login(request);

        return Response.<LoginResponse>builder()
                .status("OK")
                .message("login success")
                .data(loginResponse)
                .build();
    }

    @GetMapping(path = {"/activate-account/{otp}", "/activate-account/{otp}/"})
    public Response<Object> confirmOTP(@PathVariable String otp){
        authService.confirmOTP(otp);

        return Response.builder()
                .status("OK")
                .message("account has been activated")
                .build();
    }

    @PostMapping(path = {"/forgot-password", "/forgot-password/"})
    public Response<Object> forgetPassword(@Valid @RequestBody ForgetPasswordRequest request){
        authService.forgetPassword(request);

        return Response.builder()
                .status("OK")
                .message("otp code has been sent")
                .build();
    }

    @PutMapping(path = {"/change-password", "/change-password/"})
    public Response<Object> changePassword(@Valid @RequestBody ChangePasswordRequest request){
        authService.changePassword(request);

        return Response.builder()
                .status("OK")
                .message("password updated successfully")
                .build();
    }
}
