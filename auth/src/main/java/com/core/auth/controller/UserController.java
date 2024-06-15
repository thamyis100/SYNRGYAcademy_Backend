package com.core.auth.controller;

import com.core.auth.service.IUserService;
import com.core.auth.dto.Response;
import com.core.auth.dto.user.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping(path = "/ping")
    public String hello(){
        return "Hello";
    }

    @GetMapping(path = {"/current", "/current/"})
    public Response<UserResponse> getDetailProfile(Principal principal){
        UserResponse userResponse = userService.getDetailProfile(principal);

        return Response.<UserResponse>builder()
                .status("OK")
                .message("detail user retrieved successfully")
                .data(userResponse)
                .build();
    }
}
