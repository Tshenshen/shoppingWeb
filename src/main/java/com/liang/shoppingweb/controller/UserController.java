package com.liang.shoppingweb.controller;

import com.liang.shoppingweb.entity.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @GetMapping("/getCurrentUser")
    @ResponseBody
    public User getCurrentUser(HttpSession session){
        User user = (User)session.getAttribute("SW_USER");
        System.out.println(user);
        return (User) session.getAttribute("SW_USER");
    }
}
