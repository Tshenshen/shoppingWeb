package com.liang.shoppingweb.service;

import com.liang.shoppingweb.entity.user.User;
import com.liang.shoppingweb.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;


    public List<User> getAll(){
        return userMapper.getAll();
    }

    public void insertUser(User user){
        userMapper.insertUser(user);
    }
}
