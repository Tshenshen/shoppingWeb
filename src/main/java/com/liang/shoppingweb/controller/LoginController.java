package com.liang.shoppingweb.controller;

import com.liang.shoppingweb.entity.user.User;
import com.liang.shoppingweb.mapper.UserMapper;
import com.liang.shoppingweb.service.UserService;
import com.liang.shoppingweb.utils.EncodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class LoginController {

    @Resource
    private UserService userService;

    @GetMapping("/userLogin")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/userRegister")
    public String userRegisterPage() {
        return "register";
    }

    @PostMapping("/userRegister")
    public String userRegister(User user) {
        user.setPassword(EncodeUtils.encodeByBCrypt(user.getPassword()));
        user.setEnable('1');
        user.setCreateDate(new Date());
        userService.insertUser(user);
        return "login";
    }
}
