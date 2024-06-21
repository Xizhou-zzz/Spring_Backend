package com.example.login.service;

import com.example.login.bean.User;
import com.example.login.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    //@Autowired注解用于实现自动装配
    @Autowired
    private UserMapper userMapper;//注入UserMapper
    //查询所有用户
    public List<User> userList(){
        return userMapper.userList();
    }
    //增加保存用户
    public void save(User user){
        userMapper.save(user);
    }
    //根据id删除用户
    public int delete(Integer id){
        return userMapper.delete(id);
    }
    //根据id查找用户
    public User findUserById(int id){
        return userMapper.findUserById(id);
    }
    //更改用户信息
    public int update(User user){
        return userMapper.update(user);
    }
}
