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

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getBirth() {
        return this.birth;
    }

}
