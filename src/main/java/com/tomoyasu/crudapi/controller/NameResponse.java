package com.tomoyasu.crudapi.controller;

import com.tomoyasu.crudapi.entity.Name;

public class NameResponse {
    private int id;
    private String name;

    public NameResponse(Name name) {
        this.id = name.getId();
        this.name = name.getName();
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
