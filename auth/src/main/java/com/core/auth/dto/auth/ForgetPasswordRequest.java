package com.core.auth.dto.auth;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class ForgetPasswordRequest {
    @Email(message = "email must be filled with valid email")
    @NotEmpty(message = "email is required")
    private String email;
}
