package com.snopko.RestApi.security.logic.service;

import com.snopko.RestApi.security.dao.entity.RoleDao;
import com.snopko.RestApi.security.dao.entity.UserDao;
import com.snopko.RestApi.security.dao.repository.IUserRepository;
import com.snopko.RestApi.security.logic.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private IUserRepository repository;
    @Autowired
    private PasswordEncoder encoder;

    public Optional<UserDao> register(UserDto user) {
        UserDao newUser = new UserDao();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(encoder.encode(user.getPassword()));
        newUser.setRole(RoleDao.USER);

        return Optional.of(repository.save(newUser));
    }

    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }

    public void deleteByUsername(String username){
        repository.deleteByUsername(username);
    }
}
