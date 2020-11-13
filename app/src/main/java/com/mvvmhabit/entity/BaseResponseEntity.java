package com.mvvmhabit.entity;

public class BaseResponseEntity<T> {
    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T result) {
        this.data = result;
    }

    public boolean isOk() {
        return code == 200;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
