package com.snopko.RestApi.cars.logic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerDto {
    private long id;
    private String firstName;
    private String secondName;
}
