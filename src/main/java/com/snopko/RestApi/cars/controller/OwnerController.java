package com.snopko.RestApi.cars.controller;

import com.snopko.RestApi.cars.logic.dto.OwnerCreateDto;
import com.snopko.RestApi.cars.logic.dto.OwnerDto;
import com.snopko.RestApi.cars.logic.dto.OwnerDtoWithProfiles;
import com.snopko.RestApi.cars.logic.service.OwnerService;
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
    public ResponseEntity<OwnerDtoWithProfiles> get(@PathVariable("id") long id) {
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<OwnerDto>> get() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<OwnerDto> create(@RequestBody OwnerCreateDto dto) {
        return new ResponseEntity<>(service.add(dto), HttpStatus.CREATED);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<OwnerDto> update(@PathVariable("id") long id, @RequestBody OwnerCreateDto dtoUp) {
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
