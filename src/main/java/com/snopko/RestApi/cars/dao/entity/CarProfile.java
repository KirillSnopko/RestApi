package com.snopko.RestApi.cars.dao.entity;

//import jakarta.persistence.*;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "profile")
public class CarProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(nullable = false)
    private String number;
    @OneToOne(targetEntity = Car.class, cascade = CascadeType.DETACH, optional = false, fetch = FetchType.LAZY)
    private Car car;
    @ManyToOne(targetEntity = Owner.class)
    @JoinColumn(name = "owner_id")
    private Owner owner;
}
