package com.core.auth.dto.auth;


import lombok.Data;

@Data
public class TokenAuth {
    private String access_token;
    private String method;
}
