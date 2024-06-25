package com.core.auth.dto.auth;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class GoogleAuthRequest {

    @NotEmpty(message = "accessToken field is required")
    private String accessToken;
}
