package com.core.auth.dto.auth;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class LoginRequest {
    @NotEmpty(message = "email is required")
    @Email(message = "email must be filled by valid email format")
    private String email;

    @NotEmpty(message = "password is required")
    private String password;
}
