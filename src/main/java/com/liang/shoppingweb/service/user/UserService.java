package com.liang.shoppingweb.service.user;

import com.liang.shoppingweb.entity.user.User;
import com.liang.shoppingweb.exception.MyException;
import com.liang.shoppingweb.mapper.user.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;


    public List<User> getAll() {
        return userMapper.getAll();
    }

    public void insertUser(User user) throws MyException {
        try {
            userMapper.insertUser(user);
        } catch (Exception e) {
            MyException exception = new MyException(e);
            exception.addErrMsg("username", "用户名已存在");
            throw exception;
        }
    }

    public void updateLastLoginDateByUsername(String username) {
        User user = new User();
        user.setUsername(username);
        user.setLastLoginDate(new Date());
        System.out.println(new Date());
        userMapper.updateLastLoginDateByUsername(user);
    }

    public User getUserByName(String username) {
        return userMapper.getUserByName(username);
    }
}
