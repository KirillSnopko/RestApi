package com.snopko.RestApi.cars.logic.facade;

import com.snopko.RestApi.cars.dao.entity.Car;
import com.snopko.RestApi.cars.dao.repository.ICarRepository;
import com.snopko.RestApi.cars.logic.dto.CarCreateDto;
import com.snopko.RestApi.cars.logic.dto.CarDto;
import com.snopko.RestApi.cars.logic.exception.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class CarFacade {
    @Autowired
    private ICarRepository repository;
    @Autowired
    private ModelMapper mapper;

    public long count() {
        return repository.count();
    }

    public CarDto getById(long id) {
        Car car = repository.findById(id).orElseThrow(() -> new NotFoundException("car with id=" + id + " not found"));
        return mapper.map(car, CarDto.class);
    }

    public List<CarDto> getAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), true)
                .map(i -> mapper.map(i, CarDto.class))
                .collect(Collectors.toList());
    }

    public CarDto add(CarCreateDto dto) {
        Car car = repository.save(mapper.map(dto, Car.class));
        return mapper.map(car, CarDto.class);
    }

    public CarDto update(CarCreateDto dto, long id) {
        Car car = repository.findById(id).orElseThrow(() -> new NotFoundException("car with id=" + id + " not found"));
        mapper.map(dto, car);
        Car updated = repository.save(car);
        return mapper.map(updated, CarDto.class);
    }

    public void delete(long id) {
        Car car = repository.findById(id).orElseThrow(() -> new NotFoundException("car with id=" + id + " not found"));
        repository.delete(car);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
