package com.liang.shoppingweb.controller;

import com.liang.shoppingweb.common.MyResponse;
import com.liang.shoppingweb.entity.user.User;
import com.liang.shoppingweb.utils.LoginUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller()
@RequestMapping("/user")
public class UserController {

    @GetMapping("/getReceiveSettingPage")
    public String getReceiveSettingPage(){
        return "/user/receiveSetting";
    }


    @GetMapping("/getUserCenterPage")
    public String getUserCenterPage(Model model){
        User userInfo = LoginUtils.getCurrentUser();
        model.addAttribute("userInfo",userInfo);
        return "/user/center";
    }


    @GetMapping("/getCurrentUser")
    @ResponseBody
    public MyResponse getCurrentUser(HttpSession session) {
        MyResponse myResponse = new MyResponse();
        User user = (User) session.getAttribute("SW_USER");
        if (user == null) {
            myResponse.setSuccess(false);
            myResponse.setMessage("用户未登录");
        } else {
            myResponse.setSuccess(true);
            myResponse.setContent(user);
            myResponse.setMessage("获取当前用户成功");
            System.out.println(myResponse);
        }
        return myResponse;
    }
}
