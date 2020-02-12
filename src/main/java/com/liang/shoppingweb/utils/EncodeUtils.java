package com.liang.shoppingweb.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncodeUtils {

    public static String encodeByBCrypt(String rawString){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode(rawString);
        return encode;
    }
}
