package com.wzz.ownv.common.utils;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @program: ownv
 * @description: MD5加密
 * @author: wzz
 * @create: 2019-12-26 17:52
 */
public class Md5Util {
    /**利用MD5进行加密*/
    public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5=MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String newStr=base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newStr;
    }

    public static boolean checkMd5(String target,String source) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        return Md5Util.EncoderByMd5(target).equals(source);
    }

}
