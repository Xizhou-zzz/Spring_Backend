package com.example.repair.mapper;

import com.example.repair.bean.Repair;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepairMapper {
    //查询所有维修记录
    List<Repair> repairList();
    //增加保存维修记录
    void save(Repair repair);
    //根据id删除维修记录
    int delete(Integer id);
    //根据id查找维修记录
    Repair findRepairById(int id);
    //更改维修记录
    int update(Repair repair);
}
