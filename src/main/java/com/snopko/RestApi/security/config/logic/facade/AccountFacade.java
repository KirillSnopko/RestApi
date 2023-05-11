package com.snopko.RestApi.security.config.logic.facade;

import com.snopko.RestApi.cars.logic.exception.NotFoundException;
import com.snopko.RestApi.security.config.JwtUtils;
import com.snopko.RestApi.security.config.logic.dto.UserDtoCreate;
import com.snopko.RestApi.security.dao.entity.AppRole;
import com.snopko.RestApi.security.dao.entity.AppUser;
import com.snopko.RestApi.security.dao.repository.IUserRepository;
import com.snopko.RestApi.security.config.logic.dto.AuthResponseDto;
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

    public AuthResponseDto authAndGetToken(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateToken(authentication);
        return new AuthResponseDto(username, token);
    }
}
