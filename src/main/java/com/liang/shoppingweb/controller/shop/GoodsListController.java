package com.liang.shoppingweb.controller.shop;

import com.github.pagehelper.PageInfo;
import com.liang.shoppingweb.common.PageConstant;
import com.liang.shoppingweb.entity.shop.Goods;
import com.liang.shoppingweb.service.shop.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class GoodsListController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping({"/index.html", "/", "/index"})
    public String indexHtml(Model model) {
        PageInfo<Goods> pageInfo = goodsService.getAllByPage(1, PageConstant.pageSize);
        model.addAttribute("pageInfo", pageInfo);
        return "index";
    }

    @GetMapping("/index/{pageNumber}")
    public String indexByPageNumber(Model model, @PathVariable int pageNumber) {
        PageInfo<Goods> pageInfo = goodsService.getAllByPage(pageNumber, PageConstant.pageSize);
        model.addAttribute("pageInfo", pageInfo);
        return "index";
    }
}
