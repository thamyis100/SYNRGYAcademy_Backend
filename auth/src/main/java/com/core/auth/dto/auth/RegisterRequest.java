package com.core.auth.dto.auth;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class RegisterRequest {
    @NotEmpty(message = "username is required.")
    private String username;

    @NotEmpty(message = "password is required.")
    @Size(min = 6, message = "password strength is weak")
    private String password;

    @NotEmpty(message = "fullname is required.")
    private String fullname;

    private String roleName;
}
