package com.core.auth.dto.auth;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class ChangePasswordRequest {
    @NotEmpty(message = "email is required")
    @Email(message = "email must be filled with valid email")
    private String email;

    @NotEmpty(message = "newPassword is required")
    @Size(min = 6, message = "password strength is weak")
    private String newPassword;

    @NotEmpty(message = "newConfirmPassword is required")
    @Size(min = 6, message = "password strength is weak")
    private String newConfirmPassword;

    @NotEmpty(message = "otp is required")
    @Size(min = 6, max = 6, message = "otp only has 6 character")
    private String otp;
}
