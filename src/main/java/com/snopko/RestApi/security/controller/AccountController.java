package com.snopko.RestApi.security.controller;

import com.snopko.RestApi.security.config.Constants;
import com.snopko.RestApi.security.config.JwtUtils;
import com.snopko.RestApi.security.dao.entity.UserDao;
import com.snopko.RestApi.security.logic.dto.LoginDto;
import com.snopko.RestApi.security.logic.dto.UserDto;
import com.snopko.RestApi.security.logic.service.UserDetailsServiceImpl;
import com.snopko.RestApi.security.logic.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

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
    public ResponseEntity<String> authenticate(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword(),
                            new ArrayList<>()));
            final UserDetails user = detailsService.loadUserByUsername(loginDto.getUsername());
            if (user != null) {
                String jwt = jwtUtils.generateToken(user);
                Cookie cookie = new Cookie(Constants.TOKEN_PREFIX, jwt);
                cookie.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days
                cookie.setHttpOnly(true);
                cookie.setPath("/"); // Global
                response.addCookie(cookie);
                return ResponseEntity.ok(jwt);
            }
            return ResponseEntity.status(400).body("Error authenticating");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("" + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserDao> register(@RequestBody UserDto user) throws Exception {
        return ResponseEntity.ok(userService.register(user).orElseThrow(() -> new Exception("Unknown")));
    }
}
