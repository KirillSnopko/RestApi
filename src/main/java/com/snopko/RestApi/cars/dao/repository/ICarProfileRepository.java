package com.snopko.RestApi.cars.dao.repository;

import com.snopko.RestApi.cars.dao.entity.CarProfile;
import org.springframework.data.repository.CrudRepository;

public interface ICarProfileRepository extends CrudRepository<CarProfile, Long> {
}
