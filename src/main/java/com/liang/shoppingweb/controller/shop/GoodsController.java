package com.liang.shoppingweb.controller.shop;

import com.liang.shoppingweb.entity.shop.Goods;
import com.liang.shoppingweb.service.shop.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/shop/goodsDetailPage/{id}")
    public String goodsDetailPage(@PathVariable String id, Model model) {
        model.addAttribute("goods",goodsService.getGoodsById(id));
        return "shop/goodsDetail";
    }

    @GetMapping("/shop/goodsDetail/{id}")
    @ResponseBody
    public Goods goodsDetail(@PathVariable String id) {
        return goodsService.getGoodsById(id);
    }

}
