package com.wzz.ownv.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author wzz
 * @since 2019-12-26
 */
@Data
public class UserInfo  {

    private static final long serialVersionUID = 1L;

    /**
     * 用户主键
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    private String userNickname;

    private String userAccount;

    private String userPassword;

    private String userPhone;

    private String userGender;

    private Long userBirthday;

    private Long createTime;


}
