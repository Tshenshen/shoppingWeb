package com.liang.shoppingweb.component;

import com.liang.shoppingweb.common.AuthorityConstant;
import com.liang.shoppingweb.common.MyResponse;
import com.liang.shoppingweb.utils.JSONUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class LogoutInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        HttpSession session = request.getSession();
        session.removeAttribute(AuthorityConstant.session_user_key);
        session.removeAttribute(AuthorityConstant.session_user_token_key);
        String data = JSONUtil.object2Json(MyResponse.getSuccessResponse("注销成功！！"));
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        PrintWriter responseWriter = response.getWriter();
        responseWriter.append(data);
        return false;
    }
}
