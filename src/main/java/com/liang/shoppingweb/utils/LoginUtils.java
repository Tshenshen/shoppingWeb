package com.liang.shoppingweb.utils;

import com.liang.shoppingweb.common.AuthorityConstant;
import com.liang.shoppingweb.entity.user.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginUtils {

    public static User getCurrentUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        return (User) session.getAttribute(AuthorityConstant.session_user_key);
    }

    public static void setCurrentUser(User user) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        session.setAttribute(AuthorityConstant.session_user_key,user);
    }

    public static String getCurrentUsername() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        return ((User) session.getAttribute(AuthorityConstant.session_user_key)).getUsername();
    }

    public static String getCurrentUserId() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        return ((User) session.getAttribute(AuthorityConstant.session_user_key)).getId();
    }

    public static String getSessionUserToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        return (String) session.getAttribute(AuthorityConstant.session_user_token_key);
    }

    public static String getCookieUserToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String cookieUserToken = null;
        for (Cookie cookie : request.getCookies()) {
            if(cookie.getName().equals(AuthorityConstant.session_user_token_key)){
                cookieUserToken = cookie.getValue();
                break;
            }
        }
        return cookieUserToken;
    }

    public static boolean isSameUser() {
        String sessionUserToken = getSessionUserToken();
        String cookieUserToken = getCookieUserToken();
        return sessionUserToken.equals(cookieUserToken);
    }

    public static boolean isSameUser(String paramUserToken) {
        String sessionUserToken = getSessionUserToken();
        return sessionUserToken.equals(paramUserToken);
    }

    public static String getCurrentUserRole() {
        return getCurrentUser().getRole();
    }

    public static boolean isEnterprise() {
        return AuthorityConstant.shop.equals(getCurrentUser().getRole());
    }

    public static String getCurrentUserEnterpriseId() {
        return getCurrentUser().getEnterpriseId();
    }

}
