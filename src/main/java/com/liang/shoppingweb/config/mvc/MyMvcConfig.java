package com.liang.shoppingweb.config.mvc;

import com.liang.shoppingweb.component.LoginInterceptor;
import com.liang.shoppingweb.component.LogoutInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;
    private final LogoutInterceptor logoutInterceptor;

    @Autowired
    public MyMvcConfig(LoginInterceptor loginInterceptor, LogoutInterceptor logoutInterceptor) {
        this.loginInterceptor = loginInterceptor;
        this.logoutInterceptor = logoutInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/user/**", "/cart/**", "/order/**");
        registry.addInterceptor(logoutInterceptor).addPathPatterns("/logout/success");
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                response.sendRedirect("/ShopWeb/shop/index");
                return false;
            }
        }).addPathPatterns("/", "/index", "index.html");
    }

}
