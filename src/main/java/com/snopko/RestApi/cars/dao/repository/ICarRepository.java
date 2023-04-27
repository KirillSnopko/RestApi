package com.snopko.RestApi.cars.dao.repository;

import com.snopko.RestApi.cars.dao.entity.Car;
import org.springframework.data.repository.CrudRepository;

public interface ICarRepository extends CrudRepository<Car, Long> {
}
