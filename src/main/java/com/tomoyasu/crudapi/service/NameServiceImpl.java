package com.tomoyasu.crudapi.service;

import com.tomoyasu.crudapi.entity.Name;
import com.tomoyasu.crudapi.exception.ResourceNotFoundException;
import com.tomoyasu.crudapi.mapper.NameMapper;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

@Service
public class NameServiceImpl implements NameService {

    private NameMapper nameMapper;

    public NameServiceImpl(NameMapper nameMapper) {
        this.nameMapper = nameMapper;
    }

    @Override
    public List<Name> findAll() {
        return nameMapper.findAll();
    }

    @Override
    public Name findById(int id) {
        Optional<Name> name = this.nameMapper.findById(id);
        return name.orElseThrow(() -> new ResourceNotFoundException("resource not found"));
    }

    @Override
    public Name createName(String name, YearMonth birth) {
        Name NameData = new Name(name, birth);
        nameMapper.createName(NameData);
        return NameData;
    }

    @Override
    public void updateName(int id, String name, YearMonth birth) {
        Name nameUpdate = nameMapper.findById(id).orElseThrow(() -> new ResourceNotFoundException("resource not found"));
        nameUpdate.setName(name);
        nameUpdate.setBirth(birth);
        nameMapper.updateName(nameUpdate);
    }

    @Override
    public void deleteById(int id) {
        Name deleteName = nameMapper.findById(id).orElseThrow(() -> new ResourceNotFoundException("resource not found"));
        nameMapper.deleteById(id);
    }
}
