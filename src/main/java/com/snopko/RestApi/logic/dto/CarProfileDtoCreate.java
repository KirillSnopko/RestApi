package com.snopko.RestApi.logic.dto;

import lombok.Data;

@Data
public class CarProfileDtoCreate {
    private String number;
    private long carId;
    private long ownerId;
}
