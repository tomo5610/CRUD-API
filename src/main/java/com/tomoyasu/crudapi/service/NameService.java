package com.tomoyasu.crudapi.service;

import com.tomoyasu.crudapi.entity.Name;

import java.time.YearMonth;
import java.util.List;

public interface NameService {
    List<Name> findAll();

    Name findById(int id);

    Name createName(String name, YearMonth birth);

    void updateName(int id, String name, YearMonth birth) throws Exception;

    void deleteById(int id);
}
