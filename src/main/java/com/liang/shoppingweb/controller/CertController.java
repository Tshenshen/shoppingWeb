package com.liang.shoppingweb.controller;

import com.liang.shoppingweb.entity.cart.Cert;
import com.liang.shoppingweb.service.CertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    }

    @GetMapping("/cert/Certs/{username}")
    @ResponseBody
    public List<Cert> getCertsByUsername(@PathVariable("username") String username){
        return certService.getCertsByUsername(username);
    }
}
