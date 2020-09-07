package com.wzz.ownv.common.result;

import com.wzz.ownv.common.enums.HttpCodeEnum;
import lombok.Data;

/**
 * @program: ownv
 * @description: 自定义返回模板
 * @author: wzz
 * @create: 2019-12-26 17:22
 */
@Data
public class Result <T>{
    private  Integer code;

    private  String message;

    private T data;

    public Result (Integer code,String message,T data){
        this.code=code;
        this.message=message;
        this.data=data;

    }
    /**
     * 请求成功
     * @return
     */
    public static Result success() {


        return new Result(HttpCodeEnum.OK.getCode(),HttpCodeEnum.OK.getMessage(),null);
    }

    /**
     * 请求成功（无消息）
     * @return
     */
    public static Result successAndNoMsg() {
        return new Result(HttpCodeEnum.OK.getCode(),"",null);
    }

    /**
     * 成功请求
     *
     * @param data
     * @return
     */
    public static Result success(Object data) {
        return new Result(HttpCodeEnum.OK.getCode(), HttpCodeEnum.OK.getMessage() , data);
    }

    /**
     * 成功请求（无消息）
     *
     * @param data
     * @return
     */
    public static Result successAndNoMsg(Object data) {
        return new Result(HttpCodeEnum.OK.getCode(), "", data);
    }

    /**
     * 操作失败
     * @return
     */
    public static Result fail() {
        return new Result(HttpCodeEnum.FAIL.getCode(), HttpCodeEnum.FAIL.getMessage(),null);
    }
    /**
     * 操作失败
     * @return
     */
    public static Result fail(Object data) {
        return new Result(HttpCodeEnum.FAIL.getCode(), HttpCodeEnum.FAIL.getMessage() ,data);
    }

    /**
     * 服务器错误
     * @return
     */
    public static Result error() {
        return new Result(HttpCodeEnum.INTERNAL_SERVER_ERROR.getCode(),HttpCodeEnum.INTERNAL_SERVER_ERROR.getMessage(),null);
    }


    /**
     * 服务器错误
     * @param data
     * @return
     */
    public static Result error(Object data) {
        return new Result(HttpCodeEnum.INTERNAL_SERVER_ERROR.getCode(),HttpCodeEnum.INTERNAL_SERVER_ERROR.getMessage(), data);
    }
    /**
     * 参数错误
     * @return
     */
    public static Result paramError() {
        return new Result(HttpCodeEnum.INVALID_REQUEST.getCode(), HttpCodeEnum.INVALID_REQUEST.getMessage(),null);
    }

    /**
     * 参数错误
     * @param data
     * @return
     */
    public static Result paramError(Object data) {
        return new Result(HttpCodeEnum.INVALID_REQUEST.getCode(), HttpCodeEnum.INVALID_REQUEST.getMessage(), data);
    }

    /**
     * 没有权限
     * @return
     */
    public static Result unAuthorized() {
        return new Result(HttpCodeEnum.UNAUTHORIZED.getCode(),HttpCodeEnum.UNAUTHORIZED.getMessage(),null);
    }

    /**
     * 没有权限
     * @param data
     * @return
     */
    public static Result unAuthorized(Object data) {
        return new Result(HttpCodeEnum.UNAUTHORIZED.getCode(),HttpCodeEnum.UNAUTHORIZED.getMessage(),data);
    }


    /**
     *  禁止访问
     * @return
     */
    public static Result forbidden() {
        return new Result(HttpCodeEnum.FORBIDDEN.getCode(),HttpCodeEnum.FORBIDDEN.getMessage(),null);
    }

    /**
     * 禁止访问
     * @param data
     * @return
     */
    public static Result forbidden(Object data) {
        return new Result(HttpCodeEnum.FORBIDDEN.getCode(),HttpCodeEnum.FORBIDDEN.getMessage(), data);
    }


    /**
     * 资源不存在
     * @return
     */
    public static Result notFound() {
        return new Result(HttpCodeEnum.NOT_FOUND.getCode(),HttpCodeEnum.NOT_FOUND.getMessage(),null);
    }


    /**
     * 资源不存在
     * @param data
     * @return
     */
    public static Result notFound(Object data) {
        return new Result(HttpCodeEnum.NOT_FOUND.getCode(),HttpCodeEnum.NOT_FOUND.getMessage(), data);
    }


    /**
     * 请求的格式不正确
     * @return
     */
    public static Result notAcceptable() {
        return new Result(HttpCodeEnum.NOT_ACCEPTABLE.getCode(),HttpCodeEnum.NOT_ACCEPTABLE.getMessage(),null);
    }


    /**
     * 请求的格式不正确
     * @param data
     * @return
     */
    public static Result notAcceptable(Object data) {
        return new Result(HttpCodeEnum.NOT_ACCEPTABLE.getCode(),HttpCodeEnum.NOT_ACCEPTABLE.getMessage(), data);
    }


    /**
     * 数据已经被删除
     * @return
     */
    public static Result gone() {
        return new Result(HttpCodeEnum.GONE.getCode(),HttpCodeEnum.GONE.getMessage(),null);
    }


    /**
     * 数据已经被删除
     * @param data
     * @return
     */
    public static Result gone(Object data) {
        return new Result(HttpCodeEnum.GONE.getCode(),HttpCodeEnum.GONE.getMessage(), data);
    }

    /**
     * 实体参数校验错误
     * @return
     */
    public static Result unprocesableEntity() {
        return new Result(HttpCodeEnum.UNPROCESABLE_ENTITY.getCode(),HttpCodeEnum.UNPROCESABLE_ENTITY.getMessage(),null);
    }

    /**
     * 实体参数校验错误
     * @param data
     * @return
     */
    public static Result unprocesableEntity(Object data) {
        return new Result(HttpCodeEnum.UNPROCESABLE_ENTITY.getCode(),HttpCodeEnum.UNPROCESABLE_ENTITY.getMessage(), data);
    }

    /**
     * 未知错误
     * @return
     */
    public static Result unKnowError() {
        return new Result(HttpCodeEnum.UN_KNOW_ERROR.getCode(),HttpCodeEnum.UN_KNOW_ERROR.getMessage(),null);
    }

    /**
     * 未知错误
     * @param data
     * @return
     */
    public static Result unKnowError(Object data) {
        return new Result(HttpCodeEnum.UN_KNOW_ERROR.getCode(),HttpCodeEnum.UN_KNOW_ERROR.getMessage(), data);
    }

    /**
     * 业务逻辑验证未通过
     * @return
     */
    public static Result verificationFailed() {
        return new Result(HttpCodeEnum.VERIFICATION_FAILED.getCode(),HttpCodeEnum.VERIFICATION_FAILED.getMessage(),null);
    }

    /**
     * 业务逻辑验证未通过
     * @param data
     * @return
     */
    public static Result verificationFailed(Object data) {
        return new Result(HttpCodeEnum.VERIFICATION_FAILED.getCode(),HttpCodeEnum.VERIFICATION_FAILED.getMessage(), data);
    }

    /**
     * 自定义返回
     * @param e
     * @return
     */
    public static Result custom(HttpCodeEnum e) {
        return new Result(e.getCode(),e.getMessage(),forbidden());
    }
    /**
     * 自定义返回
     * @param error
     * @return
     */
    public static Result custom(int code,String error) {
        return new Result(code,error,null);
    }

    /**
     * 自定义返回
     * @param error
     * @param data
     * @return
     */
    public static Result custom(int code,String error,Object data) {
        return new Result(code,error,data);
    }



}
