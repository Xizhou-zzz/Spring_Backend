package com.example.bike.mapper;

import com.example.bike.bean.Bike;

import java.util.List;

public interface BikeMapper {
    //查询所有用户
    List<Bike> BikeList();
    /*
     * 根据id查询用户
     */
    Bike select(int id);

    //增加保存的用户
    void save(Bike bike);

    //根据id删除用户
    int delete(Integer id);

    //根据id查找用户
    Bike findUserById(int id);

    //更改用户信息
    int update(Bike bike);
}
