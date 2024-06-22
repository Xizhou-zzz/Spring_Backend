package com.example.consumer.controller;

import com.example.consumer.bean.Bike;
import com.example.consumer.bean.Lend;
import com.example.consumer.bean.Repair;
import com.example.consumer.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
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

    /*调用用户相关微服务*/
    // 获取全部用户
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<?> getAllUsers() {
        List<ServiceInstance> instances = discoveryClient.getInstances("user-service");
        if (instances.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No instances available for student-service");
        }
        String serviceUrl = instances.get(0).getUri().toString();
        String url = serviceUrl + "/";
        ResponseEntity<List<User>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {}
        );
        System.out.println(response);
        return ResponseEntity.ok().body(response.getBody());
    }

    // 添加用户
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseEntity<?> addUser(@RequestBody User user) {
        List<ServiceInstance> instances = discoveryClient.getInstances("user-service");
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
        List<ServiceInstance> instances = discoveryClient.getInstances("user-service");
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
        List<ServiceInstance> instances = discoveryClient.getInstances("user-service");
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
        List<ServiceInstance> instances = discoveryClient.getInstances("user-service");
        if (instances.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No instances available for student-service");
        }
        String serviceUrl = instances.get(0).getUri().toString();
        String url = serviceUrl + "/" + id;
        restTemplate.put(url, user);
        return ResponseEntity.ok().body("更新成功！");
    }

    /*调用单车相关微服务*/
    // 获取全部单车信息
    @RequestMapping(value = "/bike", method = RequestMethod.GET)
    public ResponseEntity<?> getAllBikes() {
        List<ServiceInstance> instances = discoveryClient.getInstances("bike-service");
        if (instances.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No instances available for bike-service");
        }
        String serviceUrl = instances.get(0).getUri().toString();
        String url = serviceUrl + "/";
        ResponseEntity<List<Bike>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Bike>>() {}
        );
        System.out.println(response);
        return ResponseEntity.ok().body(response.getBody());
    }

    // 添加单车
    @RequestMapping(value = "/bike", method = RequestMethod.POST)
    public ResponseEntity<?> addBike(@RequestBody Bike bike) {
        List<ServiceInstance> instances = discoveryClient.getInstances("bike-service");
        if (instances.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No instances available for bike-service");
        }
        String serviceUrl = instances.get(0).getUri().toString();
        String url = serviceUrl + "/";
        ResponseEntity<String> response = restTemplate.postForEntity(url, bike, String.class);
        return ResponseEntity.ok().body(response.getBody());
    }

    // 删除单车
    @RequestMapping(value = "/bike/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteBike(@PathVariable Integer id) {
        List<ServiceInstance> instances = discoveryClient.getInstances("bike-service");
        if (instances.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No instances available for bike-service");
        }
        String serviceUrl = instances.get(0).getUri().toString();
        String url = serviceUrl + "/" + id;
        restTemplate.delete(url);
        return ResponseEntity.ok().body("删除成功！");
    }

    // 查询单车
    @RequestMapping(value = "/bike/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getBikeById(@PathVariable int id) {
        List<ServiceInstance> instances = discoveryClient.getInstances("bike-service");
        if (instances.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No instances available for bike-service");
        }
        String serviceUrl = instances.get(0).getUri().toString();
        String url = serviceUrl + "/" + id;
        ResponseEntity<Bike> response = restTemplate.getForEntity(url, Bike.class);
        return ResponseEntity.ok().body(response.getBody());
    }

    // 修改单车
    @RequestMapping(value = "/bike/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateBike(@RequestBody Bike bike, @PathVariable int id) {
        List<ServiceInstance> instances = discoveryClient.getInstances("bike-service");
        if (instances.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No instances available for bike-service");
        }
        String serviceUrl = instances.get(0).getUri().toString();
        String url = serviceUrl + "/" + id;
        restTemplate.put(url, bike);
        return ResponseEntity.ok().body("更新成功！");
    }

    /*调用维修相关微服务*/
    // 获取全部维修信息
    @RequestMapping(value = "/repair", method = RequestMethod.GET)
    public ResponseEntity<?> getAllRepairments() {
        List<ServiceInstance> instances = discoveryClient.getInstances("repairment-service");
        if (instances.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No instances available for repairment-service");
        }
        String serviceUrl = instances.get(0).getUri().toString();
        String url = serviceUrl + "/";
        ResponseEntity<List<Repair>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Repair>>() {}
        );
        System.out.println(response);
        return ResponseEntity.ok().body(response.getBody());
    }

    // 添加维修信息
    @RequestMapping(value = "/repair", method = RequestMethod.POST)
    public ResponseEntity<?> addRepairment(@RequestBody Repair repair) {
        List<ServiceInstance> instances = discoveryClient.getInstances("repairment-service");
        if (instances.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No instances available for repairment-service");
        }
        String serviceUrl = instances.get(0).getUri().toString();
        String url = serviceUrl + "/";
        ResponseEntity<String> response = restTemplate.postForEntity(url, repair, String.class);
        return ResponseEntity.ok().body(response.getBody());
    }

    // 删除维修信息
    @RequestMapping(value = "/repair/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteRepairment(@PathVariable Integer id) {
        List<ServiceInstance> instances = discoveryClient.getInstances("repairment-service");
        if (instances.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No instances available for repairment-service");
        }
        String serviceUrl = instances.get(0).getUri().toString();
        String url = serviceUrl + "/" + id;
        restTemplate.delete(url);
        return ResponseEntity.ok().body("删除成功！");
    }

    // 查询维修信息
    @RequestMapping(value = "/repair/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getRepairmentById(@PathVariable int id) {
        List<ServiceInstance> instances = discoveryClient.getInstances("repairment-service");
        if (instances.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No instances available for repairment-service");
        }
        String serviceUrl = instances.get(0).getUri().toString();
        String url = serviceUrl + "/" + id;
        ResponseEntity<Repair> response = restTemplate.getForEntity(url, Repair.class);
        return ResponseEntity.ok().body(response.getBody());
    }

    // 修改维修信息
    @RequestMapping(value = "/repair/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateRepairment(@RequestBody Repair repair, @PathVariable int id) {
        List<ServiceInstance> instances = discoveryClient.getInstances("repairment-service");
        if (instances.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No instances available for repairment-service");
        }
        String serviceUrl = instances.get(0).getUri().toString();
        String url = serviceUrl + "/" + id;
        restTemplate.put(url, repair);
        return ResponseEntity.ok().body("更新成功！");
    }

    /*调用租赁相关微服务*/
    // 获取全部租赁信息
    @RequestMapping(value = "/lend", method = RequestMethod.GET)
    public ResponseEntity<?> getAllLends() {
        List<ServiceInstance> instances = discoveryClient.getInstances("lend-service");
        if (instances.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No instances available for lend-service");
        }
        String serviceUrl = instances.get(0).getUri().toString();
        String url = serviceUrl + "/";
        ResponseEntity<List<Lend>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Lend>>() {}
        );
        System.out.println(response);
        return ResponseEntity.ok().body(response.getBody());
    }

    // 添加租赁信息
    @RequestMapping(value = "/lend", method = RequestMethod.POST)
    public ResponseEntity<?> addLend(@RequestBody Lend lend) {
        List<ServiceInstance> instances = discoveryClient.getInstances("lend-service");
        if (instances.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No instances available for lend-service");
        }
        String serviceUrl = instances.get(0).getUri().toString();
        String url = serviceUrl + "/";
        ResponseEntity<String> response = restTemplate.postForEntity(url, lend, String.class);
        return ResponseEntity.ok().body(response.getBody());
    }

    // 删除租赁信息
    @RequestMapping(value = "/lend/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteLend(@PathVariable Integer id) {
        List<ServiceInstance> instances = discoveryClient.getInstances("lend-service");
        if (instances.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No instances available for lend-service");
        }
        String serviceUrl = instances.get(0).getUri().toString();
        String url = serviceUrl + "/" + id;
        restTemplate.delete(url);
        return ResponseEntity.ok().body("删除成功！");
    }

    // 查询租赁信息
    @RequestMapping(value = "/lend/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getLendById(@PathVariable int id) {
        List<ServiceInstance> instances = discoveryClient.getInstances("lend-service");
        if (instances.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No instances available for lend-service");
        }
        String serviceUrl = instances.get(0).getUri().toString();
        String url = serviceUrl + "/" + id;
        ResponseEntity<Lend> response = restTemplate.getForEntity(url, Lend.class);
        return ResponseEntity.ok().body(response.getBody());
    }

    // 修改租赁信息
    @RequestMapping(value = "/lend/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateLend(@RequestBody Lend lend, @PathVariable int id) {
        List<ServiceInstance> instances = discoveryClient.getInstances("lend-service");
        if (instances.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No instances available for lend-service");
        }
        String serviceUrl = instances.get(0).getUri().toString();
        String url = serviceUrl + "/" + id;
        System.out.println(1);
        restTemplate.put(url, lend);
        System.out.println(2);
        return ResponseEntity.ok().body("更新成功！");
    }
}
