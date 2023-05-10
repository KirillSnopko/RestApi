package com.snopko.RestApi.security.logic.service;

import com.snopko.RestApi.cars.logic.exception.NotFoundException;
import com.snopko.RestApi.security.dao.entity.AppUser;
import com.snopko.RestApi.security.dao.repository.IUserRepository;
import com.snopko.RestApi.security.logic.dto.AdminDtoCreate;
import com.snopko.RestApi.security.logic.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class AdminService {
    @Autowired
    private IUserRepository repository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private ModelMapper mapper;

    public List<UserDto> getAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), true)
                .map(i -> mapper.map(i, UserDto.class))
                .collect(Collectors.toList());
    }

    public UserDto findById(long id) {
        AppUser user = repository.findById(id).orElseThrow(() -> new NotFoundException("invalid id. user not found"));
        return mapper.map(user, UserDto.class);
    }

    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    public UserDto create(AdminDtoCreate user) {
        AppUser newUser = new AppUser(user.getUsername(), encoder.encode(user.getPassword()), user.getEmail(), user.getRole());
        return mapper.map(repository.save(newUser), UserDto.class);
    }

    public UserDto update(long id, AdminDtoCreate dto) {
        AppUser user = repository.findById(id).orElseThrow(() -> new NotFoundException("invalid id. user not found"));
        user.update(dto.getUsername(), encoder.encode(dto.getPassword()), user.getEmail(), dto.getRole());
        return mapper.map(repository.save(user), UserDto.class);
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
