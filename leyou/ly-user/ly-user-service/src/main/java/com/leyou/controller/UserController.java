package com.leyou.controller;

import com.leyou.pojo.User;
import com.leyou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    /*
    * 实现用户数据的校验，主要包括对：手机号、用户名的唯一性校验
    * */
    @GetMapping("/check/{data}/{type}")
    public Boolean check(@PathVariable("data") String data,
                         @PathVariable("type") Integer type){
        System.out.println("校验："+data+"======"+type);

        return userService.check(data,type);
    }
    /*
    * 根据用户输入的手机号，生成随机验证码
    * */
    @PostMapping("/code")
    public void code(@RequestParam("phone") String phone){
        System.out.println("校验："+phone);
    }

    /*
    * 用户注册
    * */
    @PostMapping("/register")
    public void register(@RequestBody User user,@RequestParam String code){

        System.out.println("校验："+user.getUsername()+"======"+code);
    }

    /*
    * 根据用户名和密码查询用户
    * */
    @GetMapping("/query")
    public User query(@RequestParam String username,@RequestParam String password){
        User user = new User();

        System.out.println("校验："+username+"======"+password);
        return user;
    }
}
