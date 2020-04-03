package com.liang.shoppingweb.config.mvc;

import com.liang.shoppingweb.component.EnterpriseInterceptor;
import com.liang.shoppingweb.component.LoginInterceptor;
import com.liang.shoppingweb.component.LogoutInterceptor;
import com.liang.shoppingweb.component.RecommendInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    @Autowired
    private  LoginInterceptor loginInterceptor;
    @Autowired
    private  LogoutInterceptor logoutInterceptor;
    @Autowired
    private  RecommendInterceptor recommendInterceptor;
    @Autowired
    private  EnterpriseInterceptor enterpriseInterceptor;
    @Autowired
    WebApplicationContext webApplicationContext;

    public String getCurrentWebApplicationContextPath() {
        return webApplicationContext.getServletContext().getContextPath();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/user/**", "/cart/**", "/order/**");
        registry.addInterceptor(logoutInterceptor).addPathPatterns("/logout/success");
        registry.addInterceptor(recommendInterceptor).addPathPatterns("/shop/detailPage");
        registry.addInterceptor(enterpriseInterceptor).addPathPatterns("/enterprise/**");
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                response.sendRedirect(getCurrentWebApplicationContextPath() + "/shop/index");
                return false;
            }
        }).addPathPatterns("/", "/index", "index.html");
    }

}
