package com.liang.shoppingweb.utils;

import com.liang.shoppingweb.common.AuthorityConstant;
import com.liang.shoppingweb.entity.user.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginUtils {

    public static User getCurrentUser() {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        return (User) session.getAttribute(AuthorityConstant.session_user_key);
    }
}
