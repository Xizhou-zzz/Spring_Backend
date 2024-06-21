package com.example.consumer.controller;

import com.example.consumer.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class ConsumerController {
    private final RestTemplate restTemplate;
    private final DiscoveryClient discoveryClient;

    @Autowired
    public ConsumerController(RestTemplate restTemplate, DiscoveryClient discoveryClient) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
    }

    //调用测试微服务
//    @RequestMapping(value = "/echo/{str}", method = RequestMethod.GET)
//    public String echo(@PathVariable String str) {
//        // 获取服务提供者的实例列表
//        List<ServiceInstance> instances = discoveryClient.getInstances("login-service");
//        if (instances.isEmpty()) {
//            return "No instances available for service-provider";
//        }
//        // 获取第一个实例的URL,serviceUrl的值为http://172.22.144.1:8070
//        String serviceUrl = instances.get(0).getUri().toString();
//        // 构造请求URL
//        String url = serviceUrl + "/users/echo/" + str;
//        // 使用RestTemplate访问服务提供者
//        return restTemplate.getForObject(url, String.class);
//    }

    //调用用户登录微服务
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody User user) {
        // 获取登录服务的实例列表
        List<ServiceInstance> instances = discoveryClient.getInstances("login-service");
        if (instances.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No instances available for login-service");
        }
        // 获取第一个实例的URL
        String loginServiceUrl = instances.get(0).getUri().toString();
        // 构造请求URL
        String loginUrl = loginServiceUrl + "/login";

        // 发送登录请求到登录微服务
        ResponseEntity<String> response = restTemplate.postForEntity(loginUrl, user, String.class);
        // 根据微服务的响应处理结果
        if (response.getStatusCode() == HttpStatus.OK) {
            // 登录成功
            return ResponseEntity.ok().body(response.getBody());
        } else {
            // 登录失败
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("登录失败，用户名或密码错误！");
        }
    }

    // 添加用户
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<?> addUser(@RequestBody User user) {
        List<ServiceInstance> instances = discoveryClient.getInstances("student-service");
        if (instances.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No instances available for student-service");
        }
        String serviceUrl = instances.get(0).getUri().toString();
        String url = serviceUrl + "/";
        ResponseEntity<String> response = restTemplate.postForEntity(url, user, String.class);
        return ResponseEntity.ok().body(response.getBody());
    }

    // 删除用户
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        List<ServiceInstance> instances = discoveryClient.getInstances("student-service");
        if (instances.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No instances available for student-service");
        }
        String serviceUrl = instances.get(0).getUri().toString();
        String url = serviceUrl + "/" + id;
        restTemplate.delete(url);
        return ResponseEntity.ok().body("删除成功！");
    }

    // 查询用户
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        List<ServiceInstance> instances = discoveryClient.getInstances("student-service");
        if (instances.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No instances available for student-service");
        }
        String serviceUrl = instances.get(0).getUri().toString();
        String url = serviceUrl + "/" + id;
        ResponseEntity<User> response = restTemplate.getForEntity(url, User.class);
        return ResponseEntity.ok().body(response.getBody());
    }

    // 修改用户
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable int id) {
        List<ServiceInstance> instances = discoveryClient.getInstances("student-service");
        if (instances.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No instances available for student-service");
        }
        String serviceUrl = instances.get(0).getUri().toString();
        String url = serviceUrl + "/" + id;
        restTemplate.put(url, user);
        return ResponseEntity.ok().body("更新成功！");
    }
}
