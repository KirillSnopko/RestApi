package com.snopko.RestApi.security.controller;

import com.snopko.RestApi.security.config.JwtUtils;
import com.snopko.RestApi.security.logic.dto.AuthResponseDto;
import com.snopko.RestApi.security.logic.dto.LoginDto;
import com.snopko.RestApi.security.logic.dto.UserDtoCreate;
import com.snopko.RestApi.security.logic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AccountController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping(path = "/authenticate")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto login) {
        return new ResponseEntity<>(authAndGetToken(login.getUsername(), login.getPassword()), HttpStatus.OK);
    }

    @PostMapping(path = "/register")
    public ResponseEntity<String> register(@RequestBody UserDtoCreate user) {
        if (userService.existsByUsername(user.getUsername())) {
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }
        userService.register(user);
        return new ResponseEntity<>("User registered success!", HttpStatus.CREATED);
    }

    @PutMapping(path = "/{username}")
    public ResponseEntity<AuthResponseDto> update(@PathVariable("username") String username, @RequestBody UserDtoCreate user) {
        userService.update(username, user);
        return new ResponseEntity<>(authAndGetToken(user.getUsername(), user.getPassword()), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{username}")
    public ResponseEntity<HttpStatus> delete(@PathVariable() String username) {
        userService.deleteByUsername(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private AuthResponseDto authAndGetToken(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateToken(authentication);
        return new AuthResponseDto(username, token);
    }
}
