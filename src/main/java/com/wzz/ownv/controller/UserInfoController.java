package com.wzz.ownv.controller;

import com.alibaba.fastjson.JSON;
import com.wzz.ownv.common.exception.CommonException;
import com.wzz.ownv.common.result.Result;
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
              jedis.set(userDto.getAccount(), JSON.toJSONString(userInfo));
              pass=Md5Util.checkMd5(userDto.getPassword(),userInfo.getUserPassword());
          } } catch (NoSuchAlgorithmException e) {
              e.printStackTrace();
          } catch (UnsupportedEncodingException e) {
              e.printStackTrace();
          }finally {
              jedis.close();
          }
          if (pass){
              userInfo.setUserPassword(null);
              return new Result("success",userInfo,200);
          }else {
              return new Result("failed check you account and password",202);
          }
      }else {
          String code = jedis.get(userDto.getPhone());
          if (code==null){
              return new Result("The verification code has expired",401);
          }else {
              if (code.equals(userDto.getMsgCode())){
               return new Result("success",200);
              }else {
                  return new Result("The verification code has expired ",401);
              }

          }
      }
    }

  @PostMapping("/register")
    public Result register(@RequestBody UserInfo userInfo){
       if (userInfo!=null) {
           try {
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
       return new Result("success",200);
   }

   @GetMapping("/sendMessage")
    public Result sendMessage(@RequestParam("phone") String phone){
       Jedis jedis = RedisUtil.getJedis();
       String code = jedis.get(phone);
       if (code!=null){
           return new Result("success",code,200);
       }else {
           int v = (int)( Math.random() * (8000) + 1000);
           jedis.set(phone,v+"");
           jedis.expire(phone,300);
           jedis.close();
           return new Result("success",v,200);
       }
   }
}
