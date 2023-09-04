package com.tomoyasu.crudapi.entity;

public class Name {
    private int id;
    private String name;
    private String birth;

    public Name(int id, String name, String birth) {
        this.id = id;
        this.name = name;
        this.birth = birth;
    }

    public Name(String name, String birth) {
        this.name = name;
        this.birth = birth;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBirth() {
        return birth;
    }
}
