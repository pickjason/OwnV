package com.wzz.ownv.controller;


import com.alibaba.fastjson.JSON;
import com.wzz.ownv.common.constant.Constant;
import com.wzz.ownv.common.exception.CommonException;
import com.wzz.ownv.common.result.Result;
import com.wzz.ownv.common.utils.JwtUtils;
import com.wzz.ownv.common.utils.Md5Util;
import com.wzz.ownv.common.utils.RedisUtil;
import com.wzz.ownv.dao.UserDto;
import com.wzz.ownv.entity.UserInfo;
import com.wzz.ownv.service.IUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;


/**
 * @program: ownv
 * @description: 用户restful
 * @author: wzz
 * @create: 2019-12-26 17:21
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserInfoController {
    @Autowired
    private IUserInfoService  iUserInfoService;

   @PostMapping("/login")
    public Result login(@RequestBody UserDto userDto){
       boolean pass=false;
       UserInfo userInfo=null;
       Jedis jedis = RedisUtil.getJedis();
      if (userDto.getAccount()!=null){
          String redis = jedis.get(userDto.getAccount());
          try {
          if (redis!=null){
              userInfo= JSON.parseObject(redis, UserInfo.class);
              pass=Md5Util.checkMd5(userDto.getPassword(),userInfo.getUserPassword());
          }else {
              userInfo = iUserInfoService.getUserInfoByAccount(userDto.getAccount());
              pass=Md5Util.checkMd5(userDto.getPassword(),userInfo.getUserPassword());
          } } catch (NoSuchAlgorithmException e) {
              e.printStackTrace();
          } catch (UnsupportedEncodingException e) {
              e.printStackTrace();
          }finally {
              jedis.close();
          }
          if (pass){
              String token =null;
              token = JwtUtils.createJWT(userInfo.getUserAccount(), userInfo.getUserId(), Constant.JWT_TTL);
              userInfo.setUserPassword(token);
              return new Result(200,"success",userInfo);
          }else {
              return new Result(202,"failed check you account and password",null);
          }
      }else {
          String code = jedis.get(userDto.getPhone());
          if (code==null){
              return new Result(401,"The verification code has expired",null);
          }else {
              if (code.equals(userDto.getMsgCode())){
               return new Result(200,"success",null);
              }else {
                  return new Result(401,"The verification code has expired ",null);
              }

          }
      }
    }

  @PostMapping("/register")
    public Result register(@RequestBody UserInfo userInfo){
       if (userInfo!=null) {
           try {
               userInfo.setCreateTime(System.currentTimeMillis());
               //32位uuid
               userInfo.setUserId(UUID.randomUUID().toString().replace("-", ""));
            if(StringUtils.isNotBlank(userInfo.getUserPassword())){
                   String md5 = Md5Util.EncoderByMd5(userInfo.getUserPassword());
                    userInfo.setUserPassword(md5);
               }else {
                throw CommonException.create(CommonException.ErrorType.ILLEGAL_ARGMENT,"检查你的参数好吗？");
            }

           } catch (NoSuchAlgorithmException e) {
             log.error("md5:",e);
           } catch (UnsupportedEncodingException e) {
               log.error("md5:",e);
           }
           iUserInfoService.save(userInfo);
       }else {
           throw CommonException.create(CommonException.ErrorType.ILLEGAL_ARGMENT,"检查你的参数好吗？");
       }
       return new Result(200,"success",null);
   }

   @GetMapping("/sendMessage")
    public Result sendMessage(@RequestParam("phone") String phone){
       Jedis jedis = RedisUtil.getJedis();
       String code = jedis.get(phone);
       if (code!=null){
           return new Result(200,"success",code);
       }else {
           int v = (int)( Math.random() * (8000) + 1000);
           jedis.set(phone,v+"");
           jedis.expire(phone,300);
           jedis.close();
           return new Result(200,"success",v);
       }
   }
}
