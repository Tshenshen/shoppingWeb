package com.liang.shoppingweb.component;

import com.liang.shoppingweb.common.MyResponse;
import com.liang.shoppingweb.entity.user.User;
import com.liang.shoppingweb.utils.JSONUtil;
import com.liang.shoppingweb.utils.LoginUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = LoginUtils.getCurrentUser();
        if (user == null) {
            String data = JSONUtil.object2Json(MyResponse.getFailedResponse("用户未登录！！"));
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            PrintWriter responseWriter = response.getWriter();
            responseWriter.append(data);
            return false;
        }
        return true;
    }
}
