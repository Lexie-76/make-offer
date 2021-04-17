package com.test.domain;

public class Data3 {
    Integer square;
    Integer year;
    Double price;

    public Data3() {
    }

    public Data3(Integer square, Integer year, Double price) {
        this.square = square;
        this.year = year;
        this.price = price;
    }

    public Integer getSquare() {
        return square;
    }

    public void setSquare(Integer square) {
        this.square = square;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
