package com.test.domain;

import java.util.ArrayList;
import java.util.List;

public class Data1 {
    private List<Integer> names = new ArrayList<>();
    private List<Float> value = new ArrayList<>();

    public Data1() {
    }

    public Data1(List<Integer> names, List<Float> value) {
        this.names = names;
        this.value = value;
    }

    public List<Integer> getNames() {
        return names;
    }

    public void setNames(List<Integer> names) {
        this.names = names;
    }

    public List<Float> getValue() {
        return value;
    }

    public void setValue(List<Float> value) {
        this.value = value;
    }
}
