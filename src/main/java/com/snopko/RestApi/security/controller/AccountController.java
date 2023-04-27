package com.snopko.RestApi.security.controller;

import com.snopko.RestApi.security.config.JwtUtils;
import com.snopko.RestApi.security.logic.dto.AuthResponseDto;
import com.snopko.RestApi.security.logic.dto.LoginDto;
import com.snopko.RestApi.security.logic.dto.UserDto;
import com.snopko.RestApi.security.logic.service.UserDetailsServiceImpl;
import com.snopko.RestApi.security.logic.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
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
    private UserDetailsServiceImpl detailsService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponseDto(token), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto user) {
        if (userService.existsByUsername(user.getUsername())) {
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }
        userService.register(user);
        return new ResponseEntity<>("User registered success!", HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{username}")
    public ResponseEntity<HttpStatus> delete(@PathVariable() String username) {
        userService.deleteByUsername(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
