package com.snopko.RestApi.cars.logic.facade;

import com.snopko.RestApi.cars.dao.entity.Car;
import com.snopko.RestApi.cars.dao.entity.CarProfile;
import com.snopko.RestApi.cars.dao.entity.Owner;
import com.snopko.RestApi.cars.dao.repository.ICarProfileRepository;
import com.snopko.RestApi.cars.dao.repository.ICarRepository;
import com.snopko.RestApi.cars.dao.repository.IOwnerRepository;
import com.snopko.RestApi.cars.logic.dto.CarProfileDto;
import com.snopko.RestApi.cars.logic.dto.CarProfileDtoCreate;
import com.snopko.RestApi.cars.logic.exception.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class CarProfileFacade {
    @Autowired
    private ICarProfileRepository carProfileRepository;
    @Autowired
    private ICarRepository carRepository;
    @Autowired
    private IOwnerRepository ownerRepository;
    @Autowired
    private ModelMapper mapper;

    public long count() {
        return carProfileRepository.count();
    }

    public CarProfileDto getById(long id) {
        CarProfile profile = carProfileRepository.findById(id).orElseThrow(() -> new NotFoundException("profile with id=" + id + " not found"));
        return mapper.map(profile, CarProfileDto.class);
    }

    public List<CarProfileDto> getAll() {
        return StreamSupport.stream(carProfileRepository.findAll().spliterator(), true)
                .map(i -> mapper.map(i, CarProfileDto.class))
                .collect(Collectors.toList());
    }

    public CarProfileDto add(CarProfileDtoCreate dto) {
        Car car = carRepository.findById(dto.getCarId()).orElseThrow(() -> new NotFoundException("car with id=" + dto.getCarId() + " not found"));
        Owner owner = ownerRepository.findById(dto.getOwnerId()).orElseThrow(() -> new NotFoundException("owner with id=" + dto.getOwnerId() + " not found"));

        CarProfile newProfile = new CarProfile();
        newProfile.setCar(car);
        newProfile.setOwner(owner);
        newProfile.setNumber(dto.getNumber());
        CarProfile carProfile = carProfileRepository.save(newProfile);
        return mapper.map(carProfile, CarProfileDto.class);
    }

    public CarProfileDto update(CarProfileDtoCreate dto, long id) {
        CarProfile profile = carProfileRepository.findById(id).orElseThrow(() -> new NotFoundException("profile with id=" + id + " not found"));
        if (profile.getCar().getId() != dto.getCarId()) {
            Car car = carRepository.findById(dto.getCarId()).orElseThrow(() -> new NotFoundException("car id is invalid"));
            profile.setCar(car);
        }
        if (profile.getOwner().getId() != dto.getOwnerId()) {
            Owner owner = ownerRepository.findById(dto.getOwnerId()).orElseThrow(() -> new NotFoundException("owner id is invalid"));
            profile.setOwner(owner);
        }

        CarProfile updated = carProfileRepository.save(profile);
        return mapper.map(updated, CarProfileDto.class);
    }

    public void delete(long id) {
        CarProfile profile = carProfileRepository.findById(id).orElseThrow(() -> new NotFoundException("profile with id=" + id + " not found"));
        carProfileRepository.delete(profile);
    }

    public void deleteAll() {
        carProfileRepository.deleteAll();
    }
}
