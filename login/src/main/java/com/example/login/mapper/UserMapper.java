package com.example.login.mapper;

import com.example.login.bean.User;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserMapper {
    //查询所有用户
    List<User> userList();
    //增加保存用户
    void save(User user);
    //根据id删除用户
    int delete(Integer id);
    //根据id查找用户
    User findUserById(int id);
    //更改用户信息
    int update(User user);
}
