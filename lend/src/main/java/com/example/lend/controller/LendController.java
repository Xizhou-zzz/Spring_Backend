package com.example.lend.controller;

import com.example.lend.bean.Lend;
import com.example.lend.service.LendService;
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
public class LendController {
    @Autowired
    //注入LendService
    private LendService lendService;

    //微服务:增加一条lend数据
    @RequestMapping(method = RequestMethod.POST)
    @Operation(summary = "添加租借信息")
    public ResponseEntity<?> addLend(@RequestBody Lend lend){
        //后端拿到前端发送来的新的数据
        int user_id = lend.getUser_id();
        int bike_id = lend.getBike_id();

        LocalDateTime lend_time = lend.getLend_time();
        LocalDateTime return_time = lend.getReturn_time();


        //构建新的lend对象
        Lend newLend = new Lend();
        newLend.setBike_id(bike_id);
        newLend.setUser_id(user_id);
        newLend.setLend_time(lend_time);
        newLend.setReturn_time(return_time);

        //把新的lend信息保存到数据库中
        lendService.save(newLend);
        return ResponseEntity.ok().body("{\"message\": \"插入成功\"}");
    }

    //微服务:删除一条lend数据
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @Operation(summary = "删除用户信息")
    public ResponseEntity<?> deleteLend(@PathVariable Integer id){
        // 检查数据库中是否存在指定的lend
        Optional<Lend> lendOptional = Optional.ofNullable(lendService.findLendById(id));
        if (lendOptional.isPresent()) {
            // 如果lend存在，则删除lend
            lendService.delete(id);
            return ResponseEntity.ok().body("删除成功！");
        } else {
            // 如果lend不存在，则返回删除失败信息
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("删除失败，用户不存在！");
        }
    }

    //微服务:查询一条lend数据
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @Operation(summary = "通过ID获得一条用户数据")
    public ResponseEntity<?> getLendById(@PathVariable int id){
        // 检查数据库中是否存在指定的lend
        Optional<Lend> lendOptional = Optional.ofNullable(lendService.findLendById(id));
        if (lendOptional.isPresent()) {
            // 如果lend存在，则返回该lend数据
            return ResponseEntity.ok().body(lendOptional.get());
        } else {
            // 如果lend不存在，则返回查询失败的消息
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("查询失败，不存在该用户！");
        }
    }

    //微服务:修改一条lend数据
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @Operation(summary = "更新用户信息")
    public ResponseEntity<?> updateLend(@RequestBody Lend lend, @PathVariable int id){
        // 检查数据库中是否存在指定的lend
        Optional<Lend> lendOptional = Optional.ofNullable(lendService.findLendById(id));
        if (lendOptional.isPresent()) {
            int user_id = lend.getUser_id();
            int bike_id = lend.getBike_id();

            LocalDateTime lend_time = lend.getLend_time();
            LocalDateTime return_time = lend.getReturn_time();


            Lend updatedLend = new Lend();
            updatedLend.setUser_id(user_id);
            updatedLend.setBike_id(bike_id);
            updatedLend.setLend_time(lend_time);
            updatedLend.setReturn_time(return_time);

            // 更新信息内容
            lendService.update(updatedLend);
            return ResponseEntity.ok().body("{\"message\": \"更新成功\"}");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("更改失败，不存在该用户！");
        }
    }

}
