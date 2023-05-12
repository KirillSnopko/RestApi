package com.snopko.RestApi.security.logic.facade;

import com.snopko.RestApi.cars.logic.exception.NotFoundException;
import com.snopko.RestApi.security.config.JwtUtils;
import com.snopko.RestApi.security.logic.dto.UserDtoCreate;
import com.snopko.RestApi.security.dao.entity.AppRole;
import com.snopko.RestApi.security.dao.entity.AppUser;
import com.snopko.RestApi.security.dao.repository.IUserRepository;
import com.snopko.RestApi.security.logic.dto.AuthResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class AccountFacade {
    @Autowired
    private IUserRepository repository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private ModelMapper mapper;

    public void register(UserDtoCreate user) {
        AppUser newUser = new AppUser(user.getUsername(), encoder.encode(user.getPassword()), user.getEmail(), AppRole.USER);
        repository.save(newUser);
    }

    public void update(String username, UserDtoCreate userDto) {
        AppUser user = repository.findByUsername(username).orElseThrow(() -> new NotFoundException("user not found"));
        mapper.map(userDto, user);
        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);
    }

    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    public void deleteByUsername(String username) {
        repository.deleteByUsername(username);
    }

    public AuthResponseDto authAndGetToken(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateToken(authentication);
        return new AuthResponseDto(username, token);
    }
}
