package com.example.bike.controller;

import com.example.bike.bean.Bike;
import com.example.bike.server.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bikes")
public class BikeController {
    @Autowired
    private BikeService bikeService;

    @GetMapping
    public List<Bike> getBikeList() {
        return bikeService.bikeList();
    }


    @DeleteMapping("/{id}")
    public void deleteBike(@PathVariable Integer id){
        bikeService.delete(id);
    }
    //通过Id获得一条用户数据
    @GetMapping("/{id}")
    public Bike getBikeById(@PathVariable int id){
        Bike bike = bikeService.findBikeById(id);
        return bike;
    }



}
