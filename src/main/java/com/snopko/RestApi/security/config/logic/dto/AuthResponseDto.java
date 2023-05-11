package com.snopko.RestApi.security.config.logic.dto;

import com.snopko.RestApi.security.config.SecurityConstants;
import lombok.Data;

@Data
public class AuthResponseDto {
    private String accessToken;
    private String username;
    private String tokenType = SecurityConstants.TOKEN_PREFIX;

    public AuthResponseDto(String username, String accessToken) {
        this.accessToken = accessToken;
        this.username = username;
    }
}
