package com.snopko.RestApi.security.logic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoCreate {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
