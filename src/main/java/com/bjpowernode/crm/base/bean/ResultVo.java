package com.bjpowernode.crm.base.bean;

import lombok.Data;

import java.util.List;

/*
* 用于给前台返回数据的类
* */
@Data
public class ResultVo<T> {
    //判断用户操作是否成功
    private boolean isOk;
    //返回页面提示信息
    private String message;
    //返回给前台数据
    private T t;
    private List<T> list;
    public boolean isOk() {
        return isOk;
    }



    public void setOk(boolean ok) {
        isOk = ok;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
