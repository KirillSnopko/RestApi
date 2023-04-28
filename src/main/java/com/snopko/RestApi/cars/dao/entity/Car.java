package com.snopko.RestApi.cars.dao.entity;


//import jakarta.persistence.*;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(nullable = false)
    private String bodyNumber;
    @Column(nullable = false)
    private String brand;
    @Column(nullable = false)
    private String modal;
    @Column(nullable = false)
    private String bodyType;
    @Column(nullable = false)
    private String transmission;
    @Column(nullable = false)
    private String fuelType;
    @Column(nullable = false)
    private Date yearOfProduction;
    private Date MOT;
    private Date insurance;
}
