package com.example.lend.mapper;

import com.example.lend.bean.Lend;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LendMapper {
    //查询所有用户
    List<Lend> lendList();
    //增加保存用户
    void save(Lend lend);
    //根据id删除用户
    int delete(Integer id);
    //根据id查找用户
    Lend findLendById(int id);
    //更改用户信息
    int update(Lend lend);
}
