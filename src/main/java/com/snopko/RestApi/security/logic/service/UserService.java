package com.snopko.RestApi.security.logic.service;

import com.snopko.RestApi.security.dao.entity.RoleDao;
import com.snopko.RestApi.security.dao.entity.UserDao;
import com.snopko.RestApi.security.dao.repository.IUserRepository;
import com.snopko.RestApi.security.logic.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private IUserRepository repository;

    public Optional<UserDao> register(UserDto user) {
        UserDao newUser = new UserDao();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        newUser.setRoles(List.of(RoleDao.USER));

        return Optional.of(repository.save(newUser));
    }
}
