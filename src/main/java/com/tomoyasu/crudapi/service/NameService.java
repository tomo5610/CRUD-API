package com.tomoyasu.crudapi.service;

import com.tomoyasu.crudapi.entity.Name;

import java.util.List;

public interface NameService {
    List<Name> findAll();

    Name findById(int id);

    Name createName(String name, String birth);

    void updateName(int id, String name, String birth) throws Exception;

    void deleteById(int id);
}
