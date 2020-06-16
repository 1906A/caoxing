package com.leyou.controller;

import com.leyou.pojo.User;
import com.leyou.service.UserService;
import com.leyou.utils.CodeUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    AmqpTemplate amqpTemplate;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

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
        //1:生成一个6位数字的随机码
        String code= CodeUtils.messageCode(6);

        //2:使用rabbitMq发送短信
        Map<String,String> map= new HashMap<>();
        map.put("phone",phone);
        map.put("code",code);

        //amqpTemplate.convertAndSend("sms.exchanges","sms.send",map);

        //3:发送短信后存放redis
        stringRedisTemplate.opsForValue().set("lysms_"+phone,code,5,TimeUnit.MINUTES);
    }

    /*
    * 用户注册
    * */
    @PostMapping("/register")
    public void register(@Valid User user,String code){

        System.out.println("注册："+user.getUsername()+"======"+code);
        //判断用户不为空
        if(user!=null){
            //检验验证码是否一致
            String redisCode= stringRedisTemplate.opsForValue().get("lysms_"+user.getPhone());
            if(redisCode.equals(code)){
                userService.insertUser(user);
            }
        }
    }

    /*
    * 根据用户名和密码查询用户
    * */
    @GetMapping("/query")
    public User query(@RequestParam String username,@RequestParam String password){
        User user = new User();

        System.out.println("查询用户："+username+"======"+password);
        return user;
    }

    /*
    * 根据用户名和密码登录
    * */
    @PostMapping("login")
    public Boolean login(@RequestParam String username,@RequestParam String password){
        System.out.println("查询用户："+username+"======"+password);

        Boolean result = false;
        User user = userService.findUser(username);
        if(user!=null){
            String newPassword= DigestUtils.md5Hex(password+user.getSalt());
            if(newPassword.equals(user.getPassword())){
                result = true;
            }
        }

        return result;
    }
}
