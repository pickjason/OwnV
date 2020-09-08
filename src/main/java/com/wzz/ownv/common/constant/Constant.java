package com.wzz.ownv.common.constant;/**
 * Created by Enzo Cotter on 2020/9/8.
 */

/**
 * @program: ownv
 * @description: 常量
 * @author: wzz
 * @create: 2020-09-08 14:43
 */
public class Constant {

    //Token过期
    public static final int JWT_ERRCODE_EXPIRE = 4001;
    //验证不通过
    public static final int JWT_ERRCODE_FAIL = 4002;

    /**
     * jwt
     */
    //jwtid
    public static final String JWT_ID = "5236A";
    //密匙
    public static final String JWT_SECERT = "7786df7fc3a34e26a61c034d5ec8245d";
    //token有效时间,60分钟
    public static final long JWT_TTL = 1 * 10000* 60 * 6;


}
