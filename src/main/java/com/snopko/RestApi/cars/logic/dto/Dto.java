package com.snopko.RestApi.cars.logic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dto <T>{
    private long id;
    private T object;
}