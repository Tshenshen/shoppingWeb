package com.liang.shoppingweb.controller.common;

import com.liang.shoppingweb.common.MyResponse;
import com.liang.shoppingweb.service.common.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TagController {

    @Autowired
    private TagService tagService;



}
