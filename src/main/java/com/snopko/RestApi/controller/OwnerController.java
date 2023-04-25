package com.snopko.RestApi.controller;

import com.snopko.RestApi.logic.dto.Dto;
import com.snopko.RestApi.logic.dto.OwnerDto;
import com.snopko.RestApi.logic.dto.OwnerDtoWithProfiles;
import com.snopko.RestApi.logic.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/owners")
public class OwnerController {
    @Autowired
    private OwnerService service;

    @GetMapping(path = "/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<>(service.count(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Dto<OwnerDtoWithProfiles>> get(@PathVariable("id") long id) {
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Dto<OwnerDto>>> get() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Dto<OwnerDto>> create(@RequestBody OwnerDto dto) {
        return new ResponseEntity<>(service.add(dto), HttpStatus.CREATED);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<Dto<OwnerDto>> update(@PathVariable("id") long id, @RequestBody OwnerDto dtoUp) {
        return new ResponseEntity<>(service.update(dtoUp, id), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/all")
    public ResponseEntity<HttpStatus> delete() {
        service.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
