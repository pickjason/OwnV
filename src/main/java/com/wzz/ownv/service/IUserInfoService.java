package com.wzz.ownv.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzz.ownv.entity.UserInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jobob
 * @since 2019-12-26
 */
public interface IUserInfoService extends IService<UserInfo> {

    UserInfo getUserInfoByAccount(String account);

    UserInfo getUserInfoByPhone(String phone);


}
