package com.snopko.RestApi.dao.repository;

import com.snopko.RestApi.dao.entity.Car;
import org.springframework.data.repository.CrudRepository;

public interface ICarRepository extends CrudRepository<Car, Long> {
}
