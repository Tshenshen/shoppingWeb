package com.liang.shoppingweb.config.security;

import com.liang.shoppingweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        updateLastLoginDate(authentication);
        super.onAuthenticationSuccess(request, response, authentication);
    }

    private void updateLastLoginDate(Authentication authentication) {
        //获得用户详情
        String username = authentication.getName();
        logger.info(" 用户登录 username = " + username);
        userService.updateLastLoginDateByUsername(username);
    }
}
