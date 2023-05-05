package com.snopko.RestApi.security.controller;

import com.snopko.RestApi.security.dao.entity.AppUser;
import com.snopko.RestApi.security.logic.dto.AdminDto;
import com.snopko.RestApi.security.logic.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminService service;

    @GetMapping(path = "/{id}")
    public ResponseEntity<AppUser> get(@PathVariable long id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<AppUser>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<AppUser> add(@RequestBody AdminDto user) {
        if (service.existsByUsername(user.getUsername())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.create(user), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<AppUser> update(@PathVariable("id") long id, @RequestBody AdminDto user) {
        return new ResponseEntity<>(service.update(id, user), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") long id) {
        service.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
