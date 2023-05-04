package com.snopko.RestApi.cars.controller;

import com.snopko.RestApi.cars.logic.dto.CarDto;
import com.snopko.RestApi.cars.logic.dto.Dto;
import com.snopko.RestApi.cars.logic.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    public ResponseEntity<EntityModel<Dto<CarDto>>> get(@PathVariable("id") long id) {
        return ResponseEntity.ok(toModel(service.getById(id)));
    }

    @GetMapping(path = "/all")
    public CollectionModel<EntityModel<Dto<CarDto>>> get() {
        List<EntityModel<Dto<CarDto>>> cars = service.getAll().stream()
                .map(car -> toModel(car))
                .collect(Collectors.toList());

        return CollectionModel.of(cars, linkTo(methodOn(CarController.class).get()).withSelfRel());
    }

    @PostMapping()
    public ResponseEntity<EntityModel<Dto<CarDto>>> create(@RequestBody CarDto carDto) {
        Dto<CarDto> car = service.add(carDto);
        return ResponseEntity
                .created(linkTo(methodOn(CarController.class).get(car.getId())).toUri())
                .body(toModel(car));
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<EntityModel<Dto<CarDto>>> update(@PathVariable("id") long id, @RequestBody CarDto carDto) {
        Dto<CarDto> car = service.update(carDto, id);
        return ResponseEntity.ok(toModel(car));
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

    private EntityModel<Dto<CarDto>> toModel(Dto<CarDto> dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(CarController.class).get(dto.getId())).withSelfRel(),
                linkTo(methodOn(CarController.class).get()).withRel("cars"));
    }
}
