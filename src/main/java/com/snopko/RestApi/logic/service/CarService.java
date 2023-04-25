package com.snopko.RestApi.logic.service;

import com.snopko.RestApi.dao.entity.Car;
import com.snopko.RestApi.dao.repository.ICarRepository;
import com.snopko.RestApi.logic.dto.CarDto;
import com.snopko.RestApi.logic.dto.Dto;
import com.snopko.RestApi.logic.exception.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CarService {
    @Autowired
    private ICarRepository repository;
    @Autowired
    private ModelMapper mapper;

    public long count() {
        return repository.count();
    }

    public Dto<CarDto> getById(long id) {
        Car car = repository.findById(id).orElseThrow(() -> new NotFoundException("car with id=" + id + " not found"));
        return new Dto<CarDto>(car.getId(), mapper.map(car, CarDto.class));
    }

    public List<Dto<CarDto>> getAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), true)
                .map(i -> new Dto<CarDto>(i.getId(), mapper.map(i, CarDto.class)))
                .collect(Collectors.toList());
    }

    public Dto<CarDto> add(CarDto dto) {
        Car car = repository.save(mapper.map(dto, Car.class));
        return new Dto<CarDto>(car.getId(), mapper.map(car, CarDto.class));
    }

    public Dto<CarDto> update(CarDto dto, long id) {
        Car car = repository.findById(id).orElseThrow(() -> new NotFoundException("car with id=" + id + " not found"));
        car.setBodyNumber(dto.getBodyNumber());
        car.setBrand(dto.getBrand());
        car.setModal(dto.getModal());
        car.setBodyType(dto.getBodyType());
        car.setTransmission(dto.getTransmission());
        car.setFuelType(dto.getFuelType());
        car.setYearOfProduction(dto.getYearOfProduction());
        car.setMOT(dto.getMOT());
        car.setInsurance(dto.getInsurance());

        return new Dto<CarDto>(car.getId(), mapper.map(car, CarDto.class));
    }

    public void delete(long id) {
        Car car = repository.findById(id).orElseThrow(() -> new NotFoundException("car with id=" + id + " not found"));
        repository.delete(car);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
