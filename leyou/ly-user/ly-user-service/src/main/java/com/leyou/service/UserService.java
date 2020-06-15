package com.leyou.service;

import com.leyou.dao.UserMapper;
import com.leyou.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

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
}
