package com.snopko.RestApi.security.logic.dto;

import com.snopko.RestApi.security.dao.entity.RoleDao;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class AdminDto extends UserDto {
    @NotEmpty
    private List<RoleDao> roles;
}
