package com.snopko.RestApi.cars.logic.service;

import com.snopko.RestApi.cars.dao.entity.Owner;
import com.snopko.RestApi.cars.dao.repository.IOwnerRepository;
import com.snopko.RestApi.cars.logic.dto.Dto;
import com.snopko.RestApi.cars.logic.dto.OwnerDtoWithProfiles;
import com.snopko.RestApi.cars.logic.exception.NotFoundException;
import com.snopko.RestApi.cars.logic.dto.OwnerDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OwnerService {
    @Autowired
    private IOwnerRepository repository;
    @Autowired
    private ModelMapper mapper;

    public long count() {
        return repository.count();
    }

    public Dto<OwnerDtoWithProfiles> getById(long id) {
        Owner owner = repository.findById(id).orElseThrow(() -> new NotFoundException("owner with id=" + id + " not found"));
        return new Dto<OwnerDtoWithProfiles>(owner.getId(), mapper.map(owner, OwnerDtoWithProfiles.class));
    }

    public List<Dto<OwnerDto>> getAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), true)
                .map(i -> new Dto<OwnerDto>(i.getId(), mapper.map(i, OwnerDto.class)))
                .collect(Collectors.toList());
    }

    public Dto<OwnerDto> add(OwnerDto dto) {
        Owner owner = repository.save(mapper.map(dto, Owner.class));
        return new Dto<OwnerDto>(owner.getId(), mapper.map(owner, OwnerDto.class));
    }

    public Dto<OwnerDto> update(OwnerDto dto, long id) {
        Owner owner = repository.findById(id).orElseThrow(() -> new NotFoundException("owner with id=" + id + " not found"));
        owner.setFirstName(dto.getFirstName());
        owner.setSecondName(dto.getSecondName());
        return new Dto<OwnerDto>(owner.getId(), mapper.map(owner, OwnerDto.class));
    }

    public void delete(long id) {
        Owner owner = repository.findById(id).orElseThrow(() -> new NotFoundException("owner with id=" + id + " not found"));
        repository.delete(owner);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
