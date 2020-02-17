package com.liang.shoppingweb.controller;

import com.liang.shoppingweb.entity.cart.Cert;
import com.liang.shoppingweb.service.CertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CertController {

    @Autowired
    private CertService certService;

    @PostMapping("/cert/addGoods")
    @ResponseBody
    public void addGoods(@RequestBody Cert cert){
        System.out.println(cert);
        certService.addGoods(cert);
    }

    @GetMapping("/cert/certPage")
    public String certPage(){
        return "cert/certPage";
//        certService.addGoods(cert);
    }
}
