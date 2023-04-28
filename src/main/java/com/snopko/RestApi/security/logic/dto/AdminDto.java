package com.snopko.RestApi.security.logic.dto;

import com.snopko.RestApi.security.dao.entity.RoleDao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class AdminDto extends UserDto {
    @NotEmpty
    private RoleDao role;
}
