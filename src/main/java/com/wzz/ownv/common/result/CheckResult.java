package com.wzz.ownv.common.result;/**
 * Created by Enzo Cotter on 2020/9/8.
 */

import io.jsonwebtoken.Claims;
import lombok.Data;

/**
 * @program: ownv
 * @description: token校验
 * @author: wzz
 * @create: 2020-09-08 14:45
 */
@Data
public class CheckResult {
    private int errCode;

    private boolean success;

    private Claims claims;

}
