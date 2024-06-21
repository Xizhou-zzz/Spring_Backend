package com.example.repair.controller;


import com.example.repair.bean.Repair;
import com.example.repair.service.RepairService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class RepairController {
    @Autowired
    //注入RepairService
    private RepairService repairService;

    //微服务:增加一条维修数据
    @RequestMapping(method = RequestMethod.POST)
    @Operation(summary = "添加维修信息")
    public ResponseEntity<?> addRepair(@RequestBody Repair repair){
        //把新的信息保存到数据库中
        repairService.save(repair);
        return ResponseEntity.ok().body("{\"message\": \"插入成功\"}");
    }

    //微服务:删除一条维修数据
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @Operation(summary = "删除维修信息")
    public ResponseEntity<?> deleteRepair(@PathVariable Integer id){
        // 检查数据库中是否存在指定的用户
        Optional<Repair> repairOptional = Optional.ofNullable(repairService.findRepairById(id));
        if (repairOptional.isPresent()) {
            // 如果信息存在，则删除
            repairService.delete(id);
            return ResponseEntity.ok().body("删除成功！");
        } else {
            // 如果用户不存在，则返回删除失败信息
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("删除失败，信息不存在！");
        }
    }

    //微服务:查询一条维修数据
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @Operation(summary = "通过ID获得一条维修数据")
    public ResponseEntity<?> getRepairById(@PathVariable int id){
        // 检查数据库中是否存在指定的数据
        Optional<Repair> repairOptional = Optional.ofNullable(repairService.findRepairById(id));
        if (repairOptional.isPresent()) {
            // 如果数据存在，则返回该数据
            return ResponseEntity.ok().body(repairOptional.get());
        } else {
            // 如果数据不存在，则返回查询失败的消息
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("查询失败，不存在该信息！");
        }
    }

    //微服务:修改一条维修数据
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @Operation(summary = "更新用户信息")
    public ResponseEntity<?> updateRepair(@RequestBody Repair repair, @PathVariable int id){
        // 检查数据库中是否存在指定的信息
        Optional<Repair> repairOptional = Optional.ofNullable(repairService.findRepairById(id));
        if (repairOptional.isPresent()) {
            int bikeId = repair.getBikeId();
            LocalDateTime time = repair.getTime();
            String reason = repair.getReason();
            // 构建更新后的User对象
            Repair updatedRepair = new Repair();
            updatedRepair.setBikeId(bikeId);
            updatedRepair.setTime(time);
            updatedRepair.setReason(reason);
            // 更新信息内容
            repairService.update(updatedRepair);
            return ResponseEntity.ok().body("{\"message\": \"更新成功\"}");
        } else {
            // 如果用户不存在，则返回更改失败的消息
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("更改失败，不存在该信息！");
        }
    }
}
