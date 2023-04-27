package com.snopko.RestApi.security.logic.dto;

import com.snopko.RestApi.security.config.SecurityConstants;
import lombok.Data;

@Data
public class AuthResponseDto {
    private String accessToken;
    private String tokenType = SecurityConstants.TOKEN_PREFIX;

    public AuthResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
