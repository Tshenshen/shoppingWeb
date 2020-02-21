package com.liang.shoppingweb.utils;

public class QueryPramFormatUtils {

    public static String toIn(Object[] list) {
        StringBuffer sb = new StringBuffer("(");
        for (Object item : list) {
            sb.append(item).append(",");
        }
        sb.setCharAt(sb.length() - 1, ')');
        return sb.toString();
    }
}
