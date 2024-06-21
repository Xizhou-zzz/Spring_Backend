package com.example.repair.service;

import com.example.repair.bean.Repair;
import com.example.repair.mapper.RepairMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RepairService {

    //@Autowired注解用于实现自动装配
    @Autowired
    private RepairMapper repairMapper;
    //查询所有用户
    public List<Repair> repairList(){
        return repairMapper.repairList();
    }
    //增加保存用户
    public void save(Repair repair){
        repairMapper.save(repair);
    }
    //根据id删除用户
    public int delete(Integer id){
        return repairMapper.delete(id);
    }
    //根据id查找用户
    public Repair findRepairById(int id){
        return repairMapper.findRepairById(id);
    }
    //更改用户信息
    public int update(Repair repair){
        return repairMapper.update(repair);
    }
}
