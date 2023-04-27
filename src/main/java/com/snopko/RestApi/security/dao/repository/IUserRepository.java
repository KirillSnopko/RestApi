package com.snopko.RestApi.security.dao.repository;

import com.snopko.RestApi.security.dao.entity.UserDao;
import jakarta.transaction.UserTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserDao, Long> {
    Optional<UserDao> findByUsername(String username);

    Boolean existsByUsername(String username);

    void deleteByUsername(String username);
}
