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
import java.util.Arrays;
import java.util.LinkedList;
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
                    List<String> recommendIds = new LinkedList<>(Arrays.asList(cookie.getValue().split("#")));
                    if (!recommendIds.contains(shopVo.getStyle())) {
//                        如果是新的类别，加入队列末尾，取后五个
                        recommendIds.add(shopVo.getStyle());
                        recommendIds = recommendIds.subList(recommendIds.size() > 5 ? recommendIds.size() - 5 : 0, recommendIds.size());
                    } else {
//                        如果是已有的类别，调至末尾
                        recommendIds.remove(shopVo.getStyle());
                        recommendIds.add(shopVo.getStyle());
                    }
                    System.out.println(String.join("#", recommendIds));
                    cookie.setValue(String.join("#", recommendIds));
                    cookie.setPath(request.getContextPath());
                    response.addCookie(cookie);
                    return true;
                }
            }
        }
//        首次进入该网站，或第一次浏览店铺
        Cookie recommendCookie = new Cookie(UserConstant.recommend, shopVo.getStyle());
        recommendCookie.setPath(request.getContextPath());
        response.addCookie(recommendCookie);
        return true;
    }
}
