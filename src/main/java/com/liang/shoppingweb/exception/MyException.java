package com.liang.shoppingweb.exception;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

public class MyException extends Exception{
    private Map<Object, Object> errMsg;
    private Exception exception;

    public MyException(Exception e) {
        errMsg = new HashMap<>();
        this.exception = e;
    }

    public MyException addErrMsg(Object key ,Object value){
        this.errMsg.put(key,value);
        return this;
    }

    @Override
    public void printStackTrace() {
        exception.printStackTrace();
    }

    public Exception getException() {
        return exception;
    }

    public Map<Object, Object> getErrMsg() {
        return errMsg;
    }
}
