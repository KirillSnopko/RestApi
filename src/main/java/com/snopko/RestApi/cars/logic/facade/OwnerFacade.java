package com.snopko.RestApi.cars.logic.facade;

import com.snopko.RestApi.cars.dao.entity.Owner;
import com.snopko.RestApi.cars.dao.repository.IOwnerRepository;
import com.snopko.RestApi.cars.logic.dto.OwnerDto;
import com.snopko.RestApi.cars.logic.dto.OwnerDtoWithProfiles;
import com.snopko.RestApi.cars.logic.exception.NotFoundException;
import com.snopko.RestApi.cars.logic.dto.OwnerCreateDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class OwnerFacade {
    @Autowired
    private IOwnerRepository repository;
    @Autowired
    private ModelMapper mapper;

    public long count() {
        return repository.count();
    }

    public OwnerDtoWithProfiles getById(long id) {
        Owner owner = repository.findById(id).orElseThrow(() -> new NotFoundException("owner with id=" + id + " not found"));
        return mapper.map(owner, OwnerDtoWithProfiles.class);
    }

    public List<OwnerDto> getAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), true)
                .map(i -> mapper.map(i, OwnerDto.class))
                .collect(Collectors.toList());
    }

    public OwnerDto add(OwnerCreateDto dto) {
        Owner owner = repository.save(mapper.map(dto, Owner.class));
        return mapper.map(owner, OwnerDto.class);
    }

    public OwnerDto update(OwnerCreateDto dto, long id) {
        Owner owner = repository.findById(id).orElseThrow(() -> new NotFoundException("owner with id=" + id + " not found"));
        mapper.map(dto, owner);
        Owner updated = repository.save(owner);
        return mapper.map(updated, OwnerDto.class);
    }

    public void delete(long id) {
        Owner owner = repository.findById(id).orElseThrow(() -> new NotFoundException("owner with id=" + id + " not found"));
        repository.delete(owner);
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
