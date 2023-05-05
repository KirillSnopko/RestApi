package com.snopko.RestApi.cars.logic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerCreateDto {
    @NotBlank
    private String firstName;
    @NotBlank
    private String secondName;
}
