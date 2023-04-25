package com.snopko.RestApi.dao.repository;

import com.snopko.RestApi.dao.entity.Owner;
import org.springframework.data.repository.CrudRepository;

public interface IOwnerRepository extends CrudRepository<Owner, Long> {
}
