package com.wzz.ownv.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzz.ownv.entity.UserInfo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2019-12-26
 */
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    UserInfo getUserInfoByAccount(@Param("account") String account);

}
