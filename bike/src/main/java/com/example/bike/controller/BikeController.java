package com.example.bike.controller;

import com.example.bike.bean.Bike;
import com.example.bike.server.BikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class BikeController {
    @Autowired
    //注入BikeService
    private BikeService bikeService;

    //微服务:获取所有维修数据
    @RequestMapping(method = RequestMethod.GET)
    @Operation(summary = "获取所有用户信息")
    public ResponseEntity<?> getAllBikes(){
        // 获取所有用户数据
        List<Bike> bikes = bikeService.bikeList();
        // 检查是否有用户数据
        if (!bikes.isEmpty()) {
            // 如果存在用户数据，则返回所有用户信息
            return ResponseEntity.ok().body(bikes);
        } else {
            // 如果没有用户数据，则返回空列表
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("没有找到任何维修数据！");
        }
    }

    //微服务:增加一条自行车数据
    @RequestMapping(method = RequestMethod.POST)
    @Operation(summary = "添加自行车信息")
    public ResponseEntity<?> addBike(@RequestBody Bike bike){
        //后端拿到前端发送来的新的自行车
        String location = bike.getLocation();
        String available_state = bike.getAvailable_state();
        String repaired_state = bike.getRepaired_state();


        //构建新的自行车对象
        Bike newBike = new Bike();
        newBike.setLocation(location);
        newBike.setAvailable_state(available_state);
        newBike.setRepaired_state(repaired_state);

        //把新的自行车信息保存到数据库中
        bikeService.save(newBike);
        return ResponseEntity.ok().body("{\"message\": \"插入成功\"}");
    }

    //微服务:删除一条自行车数据
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @Operation(summary = "删除自行车信息")
    public ResponseEntity<?> deleteBike(@PathVariable Integer id){
        // 检查数据库中是否存在指定的自行车
        Optional<Bike> bikeOptional = Optional.ofNullable(bikeService.findBikeById(id));
        if (bikeOptional.isPresent()) {
            // 如果自行车存在，则删除
            bikeService.delete(id);
            return ResponseEntity.ok().body("删除成功！");
        } else {
            // 如果自行车不存在，则返回删除失败信息
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("删除失败，自行车不存在！");
        }
    }

    //微服务:查询一条自行车数据
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @Operation(summary = "通过ID获得一条用户数据")
    public ResponseEntity<?> getBikeById(@PathVariable int id){
        // 检查数据库中是否存在指定的自行车
        Optional<Bike> bikeOptional = Optional.ofNullable(bikeService.findBikeById(id));
        if (bikeOptional.isPresent()) {
            // 如果自行车存在，则返回该用户数据
            return ResponseEntity.ok().body(bikeOptional.get());
        } else {
            // 如果自行车不存在，则返回查询失败的消息
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("查询失败，不存在该用户！");
        }
    }

    //微服务:修改一条自行车数据
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @Operation(summary = "更新用户信息")
    public ResponseEntity<?> updateBike(@RequestBody Bike bike, @PathVariable int id){
        // 检查数据库中是否存在指定的用户
        Optional<Bike> bikeOptional = Optional.ofNullable(bikeService.findBikeById(id));
        if (bikeOptional.isPresent()) {
            // 如果用户存在，则更新用户信息
            String location = bike.getLocation();
            String available_state = bike.getAvailable_state();
            String repaired_state = bike.getRepaired_state();
            // 构建更新后的User对象
            Bike updatedBike = new Bike();
            updatedBike.setId(id);
            updatedBike.setLocation(location);
            updatedBike.setAvailable_state(available_state);
            updatedBike.setRepaired_state(repaired_state);
            // 更新信息内容
            bikeService.update(updatedBike);
            return ResponseEntity.ok().body("{\"message\": \"更新成功\"}");
        } else {
            // 如果用户不存在，则返回更改失败的消息
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("更改失败，不存在该用户！");
        }
    }


}

