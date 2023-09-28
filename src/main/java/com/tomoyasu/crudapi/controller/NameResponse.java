package com.tomoyasu.crudapi.controller;

import com.tomoyasu.crudapi.entity.Name;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class NameResponse {
    private String name;
    private String birth;

    public NameResponse(Name name) {
        this.name = name.getName();
        this.birth = formatYearMonth(name.getBirth());
    }

    public String getName() {
        return this.name;
    }

    public String getBirth() {
        return this.birth;
    }

    private String formatYearMonth(YearMonth yearMonth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        return yearMonth.format(formatter);
    }
}
