package com.snopko.RestApi.cars.dao.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Data
@Entity
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String secondName;
    @OneToMany(targetEntity = CarProfile.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CarProfile> profiles;

    public Owner update(Owner owner) {
        firstName = owner.firstName;
        secondName = owner.secondName;
        profiles = owner.profiles;
        return this;
    }
}
