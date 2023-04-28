package com.snopko.RestApi.security.logic.service;

import com.snopko.RestApi.cars.logic.exception.NotFoundException;
import com.snopko.RestApi.security.dao.entity.UserDao;
import com.snopko.RestApi.security.dao.repository.IUserRepository;
import com.snopko.RestApi.security.logic.dto.AdminDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AdminService {
    @Autowired
    private IUserRepository repository;
    @Autowired
    private PasswordEncoder encoder;

    public List<UserDao> getAll() {
        return repository.findAll();
    }

    public UserDao findById(long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("invalid id. user not found"));
    }

    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    public UserDao create(AdminDto user) {
        UserDao newUser = new UserDao();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(encoder.encode(user.getPassword()));
        newUser.setRole(user.getRole());

        return repository.save(newUser);
    }

    public UserDao update(long id, AdminDto userDto) {
        UserDao user = repository.findById(id).orElseThrow(() -> new NotFoundException("invalid id. user not found"));
        user.setUsername(userDto.getUsername());
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setRole(userDto.getRole());
        return user;
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
