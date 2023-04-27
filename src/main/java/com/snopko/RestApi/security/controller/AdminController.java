package com.snopko.RestApi.security.controller;

import com.snopko.RestApi.security.dao.entity.UserDao;
import com.snopko.RestApi.security.logic.dto.AdminDto;
import com.snopko.RestApi.security.logic.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminService service;

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping()
    public ResponseEntity<List<UserDao>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PostMapping()
    public ResponseEntity<UserDao> add(@RequestBody AdminDto user) {
        return new ResponseEntity<>(service.create(user), HttpStatus.OK);
    }
}
