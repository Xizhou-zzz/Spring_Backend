package com.example.lend.mapper;

import com.example.lend.bean.Lend;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LendMapper {
    //查询所有lend
    List<Lend> lendList();
    //增加保存lend
    void save(Lend lend);
    //根据id删除lend
    int delete(Integer id);
    //根据id查找lend
    Lend findLendById(int id);
    //更改lend信息
    int update(Lend lend);
}
