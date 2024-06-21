package com.example.bike.mapper;

import com.example.bike.bean.Bike;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BikeMapper {
    //查询所有自行车
    List<Bike> bikeList();
    //增加保存自行车
    void save(Bike bike);
    //根据id删除自行车
    int delete(Integer id);
    //根据id查找自行车
    Bike findBikeById(int id);
    //更改自行车信息
    int update(Bike bike);
}
