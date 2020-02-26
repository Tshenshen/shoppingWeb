package com.liang.shoppingweb.controller.user;

import com.liang.shoppingweb.entity.user.User;
import com.liang.shoppingweb.exception.MyException;
import com.liang.shoppingweb.service.user.UserService;
import com.liang.shoppingweb.utils.EncodeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import java.util.Date;

@Controller
public class LoginController {

    @Resource
    private UserService userService;

    @GetMapping("/userLogin")
    public String loginPage() {
        return "/user/login";
    }

    @GetMapping("/userRegister")
    public String userRegisterPage() {
        return "/user/register";
    }

    @PostMapping("/userRegister")
    public String userRegister(User user, Model model) {
        user.setPassword(EncodeUtils.encodeByBCrypt(user.getPassword()));
        user.setEnable('1');
        user.setCreateDate(new Date());
        try {
            userService.insertUser(user);
        } catch (MyException e) {
            e.printStackTrace();
            model.addAttribute("errMsg", e.getErrMsg());
            return "/user/register";
        }
        return "/user/login";
    }
}
