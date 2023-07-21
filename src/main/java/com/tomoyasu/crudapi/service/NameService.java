package com.tomoyasu.crudapi.service;

import com.tomoyasu.crudapi.entity.Name;

import java.util.List;

public interface NameService {
    List<Name> findAll();
}
