package com.snopko.RestApi.security.logic.dto;

import com.snopko.RestApi.security.dao.entity.AppRole;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AdminDto extends UserDto {
    @NotEmpty
    private AppRole role;

    public AdminDto(String username, String password, AppRole role) {
        super(username, password);
        this.role = role;
    }
}
