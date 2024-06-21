package com.example.consumer.bean;

import java.time.LocalDateTime;

public class Lend {
    private int user_id;
    private int bike_id;
    private LocalDateTime lend_time;
    private LocalDateTime return_time;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getBike_id() {
        return bike_id;
    }

    public void setBike_id(int bike_id) {
        this.bike_id = bike_id;
    }

    public LocalDateTime getLend_time() {
        return lend_time;
    }

    public void setLend_time(LocalDateTime lend_time) {
        this.lend_time = lend_time;
    }

    public LocalDateTime getReturn_time() {
        return return_time;
    }

    public void setReturn_time(LocalDateTime return_time) {
        this.return_time = return_time;
    }
}
