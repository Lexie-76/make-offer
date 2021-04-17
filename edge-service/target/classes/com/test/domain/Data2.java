package com.test.domain;

public class Data2 {
    private Integer name;
    private Float value;

    public Data2() {
    }

    public Data2(Integer name, Float value) {
        this.name = name;
        this.value = value;
    }

    public Integer getName() {
        return name;
    }

    public void setName(Integer name) {
        this.name = name;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }
}
