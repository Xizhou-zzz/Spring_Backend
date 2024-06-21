package com.example.lend.service;

import com.example.lend.bean.Lend;
import com.example.lend.mapper.LendMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LendService {
    //@Autowired注解用于实现自动装配
    @Autowired
    private LendMapper lendMapper;//注入UserMapper
    //查询所有lend
    public List<Lend> lendList(){
        return lendMapper.lendList();
    }
    //增加保存lend
    public void save(Lend lend){
        lendMapper.save(lend);
    }
    //根据id删除lend
    public int delete(Integer id){
        return lendMapper.delete(id);
    }
    //根据id查找lend
    public Lend findLendById(int id){
        return lendMapper.findLendById(id);
    }
    //更改用户lend
    public int update(Lend lend){
        return lendMapper.update(lend);
    }
}
