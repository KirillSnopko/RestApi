package com.snopko.RestApi.security.logic.dto;

import com.snopko.RestApi.security.dao.entity.AppRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private long id;
    private String username;
    private String email;
    private AppRole role;
    private String createdBy;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;
}
