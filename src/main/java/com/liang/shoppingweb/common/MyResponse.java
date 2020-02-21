package com.liang.shoppingweb.common;


import lombok.Data;

@Data
public class MyResponse {

    private boolean success;
    private Object content;
    private String message;

    public MyResponse() {
    }

    public MyResponse(boolean success, String message, Object content) {
        this.success = success;
        this.message = message;
        this.content = content;
    }

    public static MyResponse getFailedResponse(String message){
        return getFailedResponse(message,null);
    }

    public static MyResponse getFailedResponse( String message, Object content) {
        return new MyResponse(false,message,content);
    }

    public static MyResponse getSuccessResponse(String message){
        return getSuccessResponse(message,null);
    }

    public static MyResponse getSuccessResponse( String message, Object content) {
        return new MyResponse(true,message,content);
    }

}
