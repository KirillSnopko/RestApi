package com.snopko.RestApi.cars.logic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {
    private long id;
    private String bodyNumber;
    private String brand;
    private String modal;
    private String bodyType;
    private String transmission;
    private String fuelType;
    private Date yearOfProduction;
    private Date MOT;
    private Date insurance;
}