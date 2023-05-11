package com.snopko.RestApi.security.config.logic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
