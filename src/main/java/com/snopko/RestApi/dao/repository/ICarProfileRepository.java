package com.snopko.RestApi.dao.repository;

import com.snopko.RestApi.dao.entity.CarProfile;
import org.springframework.data.repository.CrudRepository;

public interface ICarProfileRepository extends CrudRepository<CarProfile, Long> {
}
