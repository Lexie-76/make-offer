package com.test.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "wage")
public class WageEntity implements Serializable {
    @Id
    private String id;
    private String wage;

    public WageEntity() {
    }

    public WageEntity(String id, String wage) {
        this.id = id;
        this.wage = wage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWage() {
        return wage;
    }

    public void setWage(String wage) {
        this.wage = wage;
    }
}
