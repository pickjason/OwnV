package com.wzz.ownv.common.exception;

/**
 * @program: ownv
 * @description: 异常类
 * @author: wzz
 * @create: 2019-12-26 17:36
 */
public class CommonException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public enum ErrorType {

        /**
         * 参数非法
         */
        ILLEGAL_ARGMENT,

        /**
         * 违反业务规则
         */
        VIOLATE_BIZ_RULE,

        /**
         * 意料外的错误
         */
        UNEXPECTED_ERROR;
    }

    /**
     * 异常编码
     */
    private String errorCode;

    /**
     * 异常类型
     */
    private ErrorType type;

    public static CommonException create(ErrorType type, String message) {
        return new CommonException(type, message);
    }

    public CommonException() {
    }

    public CommonException(ErrorType type, String message) {
        super(message);
        this.type = type;
    }

    public CommonException(ErrorType type, String message, Throwable cause) {
        super(message, cause);
        this.type = type;
    }

    public CommonException withErrorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public String getErrorCode() {
        return errorCode != null ? errorCode : type.name();
    }

    public ErrorType getType() {
        return type;
    }

}
