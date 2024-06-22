package com.example.login.controller;

import com.example.login.bean.User;
import com.example.login.jwt.JwtUtil;
import com.example.login.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/")
public class UserController {
    @Autowired
    //注入UserService
    private UserService userService;
    //微服务:用户登录
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @Operation(summary = "用户登录")
    public ResponseEntity<?> login(@RequestBody User user){
        String username = user.getUsername();
        String password = user.getPassword();
        //获取用户的用户名和密码信息
        List<User> users = userService.userList();
        //遍历用户列表，检查用户名和密码是否匹配
        for(User user1 : users) {
            if(user1.getUsername().equals(username) && user1.getPassword().equals(password)){
                Map<String,String> info = new HashMap<>();
                info.put("username",username);
                info.put("password",password);
                String id = Integer.toString(user1.getId());
                info.put("id",id);
                String token = JwtUtil.createJWT(UUID.randomUUID().toString(),username,null);
                info.put("token",token);
                return ResponseEntity.ok().body(info);
            }
        }
        return ResponseEntity.ok().body("登录失败");
    }

    //微服务：修改管理员密码
    @RequestMapping(value = "/login/change_password/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> changeAdminPassword(@RequestBody User user, @PathVariable Integer id){
        System.out.println("进入了修改管理员密码微服务");
        // 检查数据库中是否存在指定的信息
        Optional<User> userOptional = Optional.ofNullable(userService.findUserById(id));
        if (userOptional.isPresent()) {
            // 如果用户存在，则更新用户信息
            User existingUser = userOptional.get();
            System.out.println(user.getPassword());
            existingUser.setPassword(user.getPassword());
            userService.update(existingUser);
            System.out.println("更新成功");
            return ResponseEntity.ok().body("{\"message\": \"更新成功\"}");
        } else {
            // 如果用户不存在，则返回更改失败的消息
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("更改失败，不存在该用户！");
        }
    }

}
