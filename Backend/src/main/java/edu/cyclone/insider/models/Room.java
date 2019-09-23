package edu.cyclone.insider.models;

import javax.persistence.Entity;

@Entity
public class Room extends BaseModel {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
