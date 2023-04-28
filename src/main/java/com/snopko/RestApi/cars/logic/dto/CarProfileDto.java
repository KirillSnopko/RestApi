package com.snopko.RestApi.cars.logic.dto;

//import jakarta.validation.constraints.NotBlank;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarProfileDto {
    @NotBlank
    private String number;
    @NotBlank
    private CarDto car;
    @NotBlank
    private OwnerDto owner;
}
