package com.snopko.RestApi.logic.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerDto {
    @NotBlank
    private String firstName;
    @NotBlank
    private String secondName;
}
