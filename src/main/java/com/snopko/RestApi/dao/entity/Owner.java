package com.snopko.RestApi.dao.entity;

import jakarta.persistence.*;
import lombok.Data;

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
}
