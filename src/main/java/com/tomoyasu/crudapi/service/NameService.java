package com.tomoyasu.crudapi.service;

import com.tomoyasu.crudapi.NameCreateForm;
import com.tomoyasu.crudapi.entity.Name;

import java.util.List;

public interface NameService {
    List<Name> findAll();

    Name findById(int id);

    Name createName(NameCreateForm nameCreateForm);
}
