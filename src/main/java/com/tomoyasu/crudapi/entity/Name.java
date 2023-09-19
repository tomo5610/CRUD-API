package com.tomoyasu.crudapi.entity;

import java.util.Objects;

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

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name name = (Name) o;
        return id == name.id &&
                Objects.equals(this.name, name.name) &&
                Objects.equals(this.birth, name.birth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birth);
    }
}
