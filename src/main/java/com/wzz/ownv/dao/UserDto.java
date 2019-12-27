package com.wzz.ownv.dao;

import lombok.Data;

/**
 * @program: ownv
 * @description: 用户登入
 * @author: wzz
 * @create: 2019-12-26 17:38
 */
@Data
public class UserDto {

    private String account;

    private String password;

    private String phone;

    private int msgCode;


}
