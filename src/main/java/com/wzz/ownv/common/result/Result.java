package com.wzz.ownv.common.result;

/**
 * @program: ownv
 * @description: 自定义返回模板
 * @author: wzz
 * @create: 2019-12-26 17:22
 */
public class Result <T>{

    private String msg;

    private T data;

    private int  code;


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


   public Result(String msg,T data,int code){
        this.msg=msg;
        this.data =data;
        this.code=code;
   }


    public Result(String msg,int code){
             this(msg,null,code);
    }


}
