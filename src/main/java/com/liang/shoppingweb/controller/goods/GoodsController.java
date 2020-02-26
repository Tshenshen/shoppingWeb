package com.liang.shoppingweb.controller.goods;

import com.liang.shoppingweb.entity.goods.Goods;
import com.liang.shoppingweb.service.goods.GoodsService;
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

    @GetMapping("/goods/goodsDetailPage/{id}")
    public String goodsDetailPage(@PathVariable Integer id, Model model) {
        model.addAttribute("goods",goodsService.getGoodsById(id));
        return "goods/goodsDetail";
    }

    @GetMapping("/goods/goodsDetail/{id}")
    @ResponseBody
    public Goods goodsDetail(@PathVariable Integer id) {
        return goodsService.getGoodsById(id);
    }

}
