package com.test.domain;

import java.util.ArrayList;
import java.util.List;

public class Data4 {
    private List<Double> data = new ArrayList<>();

    public Data4() {
    }

    public Data4(List<Double> data) {
        this.data = data;
    }

    public List<Double> getData() {
        return data;
    }

    public void setData(List<Double> data) {
        this.data = data;
    }
}
