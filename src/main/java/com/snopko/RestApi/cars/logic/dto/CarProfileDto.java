package com.snopko.RestApi.cars.logic.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarProfileDto {
    private long id;
    private String number;
    private CarCreateDto car;
    private OwnerCreateDto owner;
}
