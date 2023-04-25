package com.snopko.RestApi.logic.dto;

import com.snopko.RestApi.dao.entity.Car;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerDtoWithProfiles {
    private String firstName;
    private String secondName;
    private List<Profile> profiles;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Profile {
        private long id;
        private String number;
        private Car car;
    }
}
