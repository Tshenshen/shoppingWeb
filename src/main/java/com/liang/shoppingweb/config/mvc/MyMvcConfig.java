package com.liang.shoppingweb.config.mvc;

import com.liang.shoppingweb.component.LoginInterceptor;
import com.liang.shoppingweb.component.LogoutInterceptor;
import com.liang.shoppingweb.component.RecommendInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;
    private final LogoutInterceptor logoutInterceptor;
    private final RecommendInterceptor recommendInterceptor;
    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    public MyMvcConfig(LoginInterceptor loginInterceptor, LogoutInterceptor logoutInterceptor, RecommendInterceptor recommendInterceptor) {
        this.loginInterceptor = loginInterceptor;
        this.logoutInterceptor = logoutInterceptor;
        this.recommendInterceptor = recommendInterceptor;
    }

    public String getCurrentWebApplicationContextPath() {
        return webApplicationContext.getServletContext().getContextPath();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/user/**", "/cart/**", "/order/**");
        registry.addInterceptor(logoutInterceptor).addPathPatterns("/logout/success");
        registry.addInterceptor(recommendInterceptor).addPathPatterns("/shop/detailPage");
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                response.sendRedirect(getCurrentWebApplicationContextPath() + "/shop/index");
                return false;
            }
        }).addPathPatterns("/", "/index", "index.html");
    }

}
