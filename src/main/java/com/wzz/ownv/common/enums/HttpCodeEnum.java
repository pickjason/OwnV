package com.wzz.ownv.common.enums;

/**
 * @program: ownv
 * @description: 状态枚举
 * @author: wzz
 * @create: 2020-04-15 17:54
 */
public enum HttpCodeEnum {

    OK(200,"操作成功"),
    INVALID_REQUEST(400,"参数错误"),
    UNAUTHORIZED(401,"没有权限"),
    FORBIDDEN(403,"禁止访问"),
    NOT_FOUND(404,"资源不存在"),
    NOT_ACCEPTABLE(406,"请求的格式不正确"),
    GONE(410,"数据被删除"),
    UNPROCESABLE_ENTITY(422,"参数验证错误"),
    INTERNAL_SERVER_ERROR(500,"服务器发生错误"),
    UN_KNOW_ERROR(500,"未知错误"),
    FAIL(501,"操作失败"),

    MODEL_NOT_EXIST(1000, "模型不存在"),
    VERIFICATION_FAILED(1001, "业务逻辑验证未通过"),
    USERNAME_OR_PASSWORD_ERR(2000,"用户未登录或token已失效"),
    DELETE_DEFAULT_PHOTO_ERR(2001,"默认头像不可删除"),
    TOKEN_ERR(2002, "token无效");

    private final int code;
    private final String message;

    HttpCodeEnum(final int code, final String message){
        this.code=code;
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
