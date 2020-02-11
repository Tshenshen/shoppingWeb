package com.liang.shoppingweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.liang.shoppingweb.mapper")
@SpringBootApplication
public class ShoppingWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingWebApplication.class, args);
    }

}
