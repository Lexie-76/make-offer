package com.test.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "houseforecast")
public class HousePriceEntity implements Serializable {
    @Id
    private String id;
    private String year;
    private String price;


    public HousePriceEntity() {
    }

    public HousePriceEntity(String id, String year, String price) {
        this.id = id;
        this.year = year;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
