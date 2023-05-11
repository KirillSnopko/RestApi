package com.snopko.RestApi.cars.controller;

import com.snopko.RestApi.cars.logic.dto.CarProfileDto;
import com.snopko.RestApi.cars.logic.dto.CarProfileDtoCreate;
import com.snopko.RestApi.cars.logic.facade.CarProfileFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/profiles")
public class CarProfileController {
    @Autowired
    private CarProfileFacade service;

    @GetMapping(path = "/count")
    public ResponseEntity<Long> count() {
        long count = service.count();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CarProfileDto> get(@PathVariable("id") long id) {
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<CarProfileDto>> get() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<CarProfileDto> create(@RequestBody CarProfileDtoCreate dto) {
        return new ResponseEntity<>(service.add(dto), HttpStatus.CREATED);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<CarProfileDto> update(@PathVariable("id") long id, @RequestBody CarProfileDtoCreate dto) {
        return new ResponseEntity<>(service.update(dto, id), HttpStatus.OK);
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
