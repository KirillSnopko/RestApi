package com.snopko.RestApi.security.logic.service;

import com.snopko.RestApi.cars.logic.exception.NotFoundException;
import com.snopko.RestApi.security.dao.entity.AppRole;
import com.snopko.RestApi.security.dao.entity.AppUser;
import com.snopko.RestApi.security.dao.repository.IUserRepository;
import com.snopko.RestApi.security.logic.dto.UserDtoCreate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {
    @Autowired
    private IUserRepository repository;
    @Autowired
    private PasswordEncoder encoder;

    public void register(UserDtoCreate user) {
        AppUser newUser = new AppUser(user.getUsername(), encoder.encode(user.getPassword()), user.getEmail(), AppRole.USER);
        repository.save(newUser);
    }

    public void update(String username, UserDtoCreate userDto) {
        AppUser user = repository.findByUsername(username).orElseThrow(() -> new NotFoundException("user not found"));
        user.update(userDto.getUsername(), encoder.encode(userDto.getPassword()), userDto.getEmail());
        repository.save(user);
    }

    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    public void deleteByUsername(String username) {
        repository.deleteByUsername(username);
    }
}
