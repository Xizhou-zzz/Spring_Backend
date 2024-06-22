package com.example.consumer.bean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Repair {
    private int id;
    private int bike_id;
    private LocalDateTime time;
    private String reason;

    // Getters
    public int getId() {
        return id;
    }

    public int getBikeId() {
        return bike_id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getReason() {
        return reason;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setBikeId(int bike_id) {
        this.bike_id = bike_id;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }

}
