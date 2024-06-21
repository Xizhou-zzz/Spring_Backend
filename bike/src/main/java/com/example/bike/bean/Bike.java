package com.example.bike.bean;

public class Bike {
    private int id;

    private String location;
    private String available_state;
    private String repaired_state;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAvailable_state() {
        return available_state;
    }

    public void setAvailable_state(String available_state) {
        this.available_state = available_state;
    }

    public String getRepaired_state() {
        return repaired_state;
    }

    public void setRepaired_state(String repaired_state) {
        this.repaired_state = repaired_state;
    }
}
