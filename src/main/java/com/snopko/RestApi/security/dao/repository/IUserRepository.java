package com.snopko.RestApi.security.dao.repository;

import com.snopko.RestApi.security.dao.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);

    Boolean existsByUsername(String username);

    void deleteByUsername(String username);
}
