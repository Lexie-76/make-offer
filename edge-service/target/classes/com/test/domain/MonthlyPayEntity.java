package com.test.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "wageforecast")
public class MonthlyPayEntity {
    @Id
    private String id;
    private String year;
    private String wage;

    public MonthlyPayEntity() {
    }

    public MonthlyPayEntity(String id, String year, String wage) {
        this.id = id;
        this.year = year;
        this.wage = wage;
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

    public String getWage() {
        return wage;
    }

    public void setWage(String wage) {
        this.wage = wage;
    }
}
