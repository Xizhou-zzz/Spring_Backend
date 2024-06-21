package com.example.user.controller;


import com.example.user.bean.User;
import com.example.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    //注入UserService
    private UserService userService;

    //微服务:获取所有用户数据
    @RequestMapping(method = RequestMethod.GET)
    @Operation(summary = "获取所有用户信息")
    public ResponseEntity<?> getAllUsers(){
        // 获取所有用户数据
        List<User> users = userService.userList();
        // 检查是否有用户数据
        if (!users.isEmpty()) {
            // 如果存在用户数据，则返回所有用户信息
            return ResponseEntity.ok().body(users);
        } else {
            // 如果没有用户数据，则返回空列表
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("没有找到任何用户！");
        }
    }

    //微服务:增加一条用户数据
    @RequestMapping(method = RequestMethod.POST)
    @Operation(summary = "添加用户信息")
    public ResponseEntity<?> addUser(@RequestBody User user){
        //后端拿到前端发送来的新的用户名和密码数据
        String username = user.getUsername();
        //获取当前的用户的用户名和密码信息
        List<User> users = userService.userList();
        //遍历用户列表，检查用户名和密码是否匹配
        for(User user1 : users) {
            //如果用户名重复，则插入失败
            if(user1.getUsername().equals(username)){
                return ResponseEntity.ok().body("{\"message\": \"用户名已存在，插入失败\"}");
            }
        }


        //把新的用户信息保存到数据库中
        userService.save(user);
        return ResponseEntity.ok().body("{\"message\": \"插入成功\"}");
    }

    //微服务:删除一条用户数据
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @Operation(summary = "删除用户信息")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id){
        // 检查数据库中是否存在指定的用户
        Optional<User> userOptional = Optional.ofNullable(userService.findUserById(id));
        if (userOptional.isPresent()) {
            // 如果用户存在，则删除用户
            userService.delete(id);
            return ResponseEntity.ok().body("删除成功！");
        } else {
            // 如果用户不存在，则返回删除失败信息
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("删除失败，用户不存在！");
        }
    }

    //微服务:查询一条用户数据
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @Operation(summary = "通过ID获得一条用户数据")
    public ResponseEntity<?> getUserById(@PathVariable int id){
        // 检查数据库中是否存在指定的用户
        Optional<User> userOptional = Optional.ofNullable(userService.findUserById(id));
        if (userOptional.isPresent()) {
            // 如果用户存在，则返回该用户数据
            return ResponseEntity.ok().body(userOptional.get());
        } else {
            // 如果用户不存在，则返回查询失败的消息
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("查询失败，不存在该用户！");
        }
    }

    //微服务:修改一条用户数据
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @Operation(summary = "更新用户信息")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable int id){
        // 检查数据库中是否存在指定的用户
        Optional<User> userOptional = Optional.ofNullable(userService.findUserById(id));
        if (userOptional.isPresent()) {
            // 如果用户存在，则更新用户信息
            // 后端拿到前端发送来的新的用户名和密码数据
            String username = user.getUsername();
            String password = user.getPassword();
            int age = user.getAge();
            String sex = user.getSex();
            String address = user.getAddress();
            // 构建更新后的User对象
            User updatedUser = new User();
            updatedUser.setId(id);
            updatedUser.setUsername(username);
            updatedUser.setPassword(password);
            updatedUser.setAge(age);
            updatedUser.setSex(sex);
            updatedUser.setAddress(address);
            // 更新信息内容
            userService.update(updatedUser);
            return ResponseEntity.ok().body("{\"message\": \"更新成功\"}");
        } else {
            // 如果用户不存在，则返回更改失败的消息
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("更改失败，不存在该用户！");
        }
    }


}
