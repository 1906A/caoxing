package com.leyou.service;

import com.leyou.dao.UserMapper;
import com.leyou.pojo.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;
    
    //判断校验用户名还是电话
    public Boolean check(String data, Integer type) {
        Boolean result=false;
        User user = new User();

        if(type==1){
            user.setUsername(data);
        }else if(type==2){
            user.setPhone(data);
        }

        User user1 = userMapper.selectOne(user);
        if(user1==null){
            result=true;
        }

        return result;
    }
    
    //注册用户
    public void insertUser(User user) {
        //盐值
        String salt = UUID.randomUUID().toString().substring(0, 32);

        //调用方法 加密
        String me5Hex = this.getPwd(user.getPassword(), salt);

        user.setPassword(me5Hex);
        user.setSalt(salt);
        user.setCreated(new Date());
        userMapper.insert(user);
    }
    
    /*
    * 密码=原生密码+盐值 并用MD5加密
    * 密码加密
    * */
    public String getPwd(String password,String salt){

        //通过调用工具类使用MD5加密
        String md5Hex = DigestUtils.md5Hex(password + salt);

        return md5Hex;
    }

    public User findUser(String username) {
        User user = new User();
        user.setUsername(username);

        User user1 = userMapper.selectOne(user);
        return user1;
    }
}
