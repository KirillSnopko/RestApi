package com.snopko.RestApi.controller;

import com.snopko.RestApi.logic.dto.CarDto;
import com.snopko.RestApi.logic.dto.Dto;
import com.snopko.RestApi.logic.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/cars")
public class CarController {
    @Autowired
    private CarService service;

    @GetMapping(path = "/count")
    public ResponseEntity<Long> count() {
        long count = service.count();
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Dto<CarDto>> get(@PathVariable("id") long id) {
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Dto<CarDto>>> get() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Dto<CarDto>> create(@RequestBody CarDto carDto) {
        return new ResponseEntity<>(service.add(carDto), HttpStatus.CREATED);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<Dto<CarDto>> update(@PathVariable("id") long id, @RequestBody CarDto carDto) {
        return new ResponseEntity<>(service.update(carDto, id), HttpStatus.OK);
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
