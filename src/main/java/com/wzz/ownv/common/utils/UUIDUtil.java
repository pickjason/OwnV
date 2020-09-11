package com.wzz.ownv.common.utils;/**
 * Created by Enzo Cotter on 2020/9/11.
 */

import java.util.UUID;

/**
 * @program: ownv
 * @description: x
 * @author: wzz
 * @create: 2020-09-11 16:07
 */
public class UUIDUtil {


   public static String  UUId(){

       return UUID.randomUUID().toString().replace("-", "");
   }


}
