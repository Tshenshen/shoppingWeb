package com.liang.shoppingweb.utils;

public class QueryPramFormatUtils {

    public static String strToIn(Object[] list) {
        StringBuffer sb = new StringBuffer("(");
        for (Object item : list) {
            sb.append("'").append(item).append("'").append(",");
        }
        sb.setCharAt(sb.length() - 1, ')');
        return sb.toString();
    }
}
