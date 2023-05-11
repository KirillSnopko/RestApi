package com.snopko.RestApi.security.controller;

import com.snopko.RestApi.security.config.logic.dto.AuthResponseDto;
import com.snopko.RestApi.security.config.logic.dto.LoginDto;
import com.snopko.RestApi.security.config.logic.dto.UserDtoCreate;
import com.snopko.RestApi.security.config.logic.facade.AccountFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AccountController {
    @Autowired
    private AccountFacade accountFacade;

    @PostMapping(path = "/authenticate")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto login) {
        return new ResponseEntity<>(accountFacade.authAndGetToken(login.getUsername(), login.getPassword()), HttpStatus.OK);
    }

    @PostMapping(path = "/register")
    public ResponseEntity<String> register(@RequestBody UserDtoCreate user) {
        if (accountFacade.existsByUsername(user.getUsername())) {
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }
        accountFacade.register(user);
        return new ResponseEntity<>("User registered success!", HttpStatus.CREATED);
    }

    @PutMapping(path = "/{username}")
    public ResponseEntity<AuthResponseDto> update(@PathVariable("username") String username, @RequestBody UserDtoCreate user) {
        accountFacade.update(username, user);
        return new ResponseEntity<>(accountFacade.authAndGetToken(user.getUsername(), user.getPassword()), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{username}")
    public ResponseEntity<HttpStatus> delete(@PathVariable() String username) {
        accountFacade.deleteByUsername(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
