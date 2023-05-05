package com.snopko.RestApi.cars.logic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarCreateDto {
    @NotBlank
    private String bodyNumber;
    @NotBlank
    private String brand;
    @NotBlank
    private String modal;
    @NotBlank
    private String bodyType;
    @NotBlank
    private String transmission;
    @NotBlank
    private String fuelType;
    @NotBlank
    private Date yearOfProduction;
    private Date MOT;
    private Date insurance;
}
