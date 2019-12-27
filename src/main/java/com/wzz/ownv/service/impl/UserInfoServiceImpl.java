package com.wzz.ownv.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzz.ownv.entity.UserInfo;
import com.wzz.ownv.mapper.UserInfoMapper;
import com.wzz.ownv.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wzz
 * @since 2019-12-26
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo getUserInfoByAccount(String account) {
        return userInfoMapper.getUserInfoByAccount(account);
    }

    @Override
    public UserInfo getUserInfoByPhone(String phone) {
        return null;
    }
}
