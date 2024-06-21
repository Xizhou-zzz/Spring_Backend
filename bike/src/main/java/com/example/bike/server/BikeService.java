package com.example.bike.server;

import com.example.bike.bean.Bike;
import com.example.bike.mapper.BikeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BikeService {
    @Autowired
    private BikeMapper bikeMapper;//注入UserMapper
    //查询所有自行车
    public List<Bike> bikeList(){
        return bikeMapper.bikeList();
    }
    //增加保存自行车
    public void save(Bike bike){
        bikeMapper.save(bike);
    }
    //根据id删除自行车
    public int delete(Integer id){
        return bikeMapper.delete(id);
    }
    //根据id查找自行车
    public Bike findBikeById(int id){
        return bikeMapper.findBikeById(id);
    }
    //更改自行车信息
    public int update(Bike bike){
        return bikeMapper.update(bike);
    }
}
