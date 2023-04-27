package com.snopko.RestApi.security.logic.service;

import com.snopko.RestApi.security.dao.entity.UserDao;
import com.snopko.RestApi.security.dao.repository.IUserRepository;
import com.snopko.RestApi.security.logic.dto.AdminDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private IUserRepository repository;
    @Autowired
    private PasswordEncoder encoder;

    public List<UserDao> getAll() {
        return repository.findAll();
    }

    public UserDao create(AdminDto user) {
        UserDao newUser = new UserDao();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(encoder.encode(user.getPassword()));
        newUser.setRole(user.getRole());

        return repository.save(newUser);
    }
}
