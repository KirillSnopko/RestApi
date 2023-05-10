package com.snopko.RestApi.cars.logic;

import com.snopko.RestApi.cars.dao.entity.Car;
import com.snopko.RestApi.cars.logic.dto.CarCreateDto;
import com.snopko.RestApi.cars.logic.dto.CarDto;
import com.snopko.RestApi.cars.logic.service.AppEmailService;
import com.snopko.RestApi.cars.logic.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CarFacade {
    @Autowired
    private CarService service;
    @Autowired
    private AppEmailService emailService;
    @Value("${default.admin.email}")
    private String adminEmail;

    public long count() {
        return service.count();
    }

    public CarDto getById(long id) {
        return service.getById(id);
    }

    public List<CarDto> getAll() {
        return service.getAll();
    }

    public CarDto add(CarCreateDto dto) {
        CarDto car = service.add(dto);
        emailService.sendTestEmail(adminEmail, Car.class.getName(), "add new car" + car.toString());
        return car;
    }

    public CarDto update(CarCreateDto dto, long id) {
        CarDto car = service.update(dto, id);
        emailService.sendTestEmail(adminEmail, Car.class.getName(), "car updated: " + car.toString());
        return car;
    }

    public void delete(long id) {
        service.delete(id);
        emailService.sendTestEmail(adminEmail, Car.class.getName(), "deleted car with id=: " + id);
    }

    public void deleteAll() {
        service.deleteAll();
        emailService.sendTestEmail(adminEmail, Car.class.getName(), "delete all cars");
    }
}
