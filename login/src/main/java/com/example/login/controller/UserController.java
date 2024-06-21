package com.example.login.controller;

import com.example.login.bean.User;
import com.example.login.jwt.JwtUtil;
import com.example.login.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
                String token = JwtUtil.createJWT(UUID.randomUUID().toString(),username,null);
                info.put("token",token);
                return ResponseEntity.ok().body(info);
            }
        }
        return ResponseEntity.ok().body("登录失败");
    }
}
