package com.liang.shoppingweb.controller.user;

import com.liang.shoppingweb.common.MyResponse;
import com.liang.shoppingweb.entity.user.User;
import com.liang.shoppingweb.exception.MyException;
import com.liang.shoppingweb.service.user.UserService;
import com.liang.shoppingweb.utils.EncodeUtils;
import com.liang.shoppingweb.utils.LoginUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

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
        try {
            userService.insertUser(user);
        } catch (MyException e) {
            e.printStackTrace();
            model.addAttribute("errMsg", e.getErrMsg());
            return "/user/register";
        }
        return "/user/login";
    }

    @GetMapping("/isAuthenticated")
    @ResponseBody
    public MyResponse isAuthenticated() {
        MyResponse myResponse;
        User user = LoginUtils.getCurrentUser();
        if (user != null) {
            myResponse = MyResponse.getSuccessResponse("用户已登录");
        } else {
            myResponse = MyResponse.getFailedResponse("用户未登录");
        }
        return myResponse;
    }
}
