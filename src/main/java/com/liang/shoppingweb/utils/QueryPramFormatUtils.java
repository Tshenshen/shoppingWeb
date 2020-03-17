package com.liang.shoppingweb.utils;

public class QueryPramFormatUtils {

    public static String arrayToIn(Object[] list) {
        StringBuffer sb = new StringBuffer("(");
        for (Object item : list) {
            sb.append("'").append(item).append("'").append(",");
        }
        sb.setCharAt(sb.length() - 1, ')');
        return sb.toString();
    }

    public static String strToIn(String str, String Separator) {
        return arrayToIn(str.split(Separator));
    }
}
