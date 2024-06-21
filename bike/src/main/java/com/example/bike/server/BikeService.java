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
    /*
     * 根据id查询
     */
    public Bike select(int id){
        return bikeMapper.select(id);
    }


    //查询所有
    public List<Bike> userList(){
        return bikeMapper.BikeList();
    }

    public void save(Bike bike){
        bikeMapper.save(bike);
    }

    //根据id删除
    public int delete(int id){
        return bikeMapper.delete(id);
    }

    //根据id查找
    public Bike findUserById(int id){
        return bikeMapper.findUserById(id);
    }

    //更改信息
    public int update(Bike bike){
        return bikeMapper.update(bike);
    }
}
