package com.snopko.RestApi.cars.dao.repository;

import com.snopko.RestApi.cars.dao.entity.Owner;
import org.springframework.data.repository.CrudRepository;

public interface IOwnerRepository extends CrudRepository<Owner, Long> {
}
