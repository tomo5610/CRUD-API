package com.tomoyasu.crudapi.service;

import com.tomoyasu.crudapi.NameCreateForm;
import com.tomoyasu.crudapi.ResourceNotFoundException;
import com.tomoyasu.crudapi.entity.Name;
import com.tomoyasu.crudapi.mapper.NameMapper;
import org.springframework.stereotype.Service;

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
    public Name createName(NameCreateForm nameCreateForm) {
        Name name = new Name(
                0,
                nameCreateForm.getName(),
                nameCreateForm.getBirth()
        );
        nameMapper.insertName(name);
        return name;
    }
}
