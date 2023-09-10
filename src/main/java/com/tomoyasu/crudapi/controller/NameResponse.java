package com.tomoyasu.crudapi.controller;

import com.tomoyasu.crudapi.entity.Name;

public class NameResponse {
    private String name;
    private String birth;

    public NameResponse(Name name) {
        this.name = name.getName();
        this.birth = name.getBirth();
    }

    public String getName() {
        return this.name;
    }

    public String getBirth() {
        return this.birth;
    }
}
