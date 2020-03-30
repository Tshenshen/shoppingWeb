package com.liang.shoppingweb.component;

import com.liang.shoppingweb.common.UserConstant;
import com.liang.shoppingweb.entity.shop.ShopVo;
import com.liang.shoppingweb.service.shop.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class RecommendInterceptor implements HandlerInterceptor {

    @Autowired
    private ShopService shopService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String shopId = request.getParameter("shopId");
        ShopVo shopVo = shopService.getShopVoById(shopId);
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName() != null && cookie.getName().equals(UserConstant.recommend)) {
                    List<String> recommendIds = new ArrayList<>(Arrays.asList(cookie.getValue().split("#")));
                    if (!recommendIds.contains(shopVo.getStyle())) {
                        recommendIds.add(0, shopVo.getStyle());
                        recommendIds.subList(0, recommendIds.size() < 5 ? recommendIds.size() : 5);
                        System.out.println(String.join("#",recommendIds));
                        cookie.setValue(String.join("#",recommendIds));
                        cookie.setPath(request.getContextPath());
                        response.addCookie(cookie);
                        return true;
                    }
                }
            }
        }
        Cookie recommendCookie = new Cookie(UserConstant.recommend,shopVo.getStyle());
        recommendCookie.setPath(request.getContextPath());
        response.addCookie(recommendCookie);
        return true;
    }
}
