package com.example.consumer.bean;

import java.time.LocalDateTime;

public class Repair {
    private int id;
    private int bikeId;
    private LocalDateTime time;
    private String reason;

    // Getters
    public int getId() {
        return id;
    }

    public int getBikeId() {
        return bikeId;
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

    public void setBikeId(int bikeId) {
        this.bikeId = bikeId;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

}
