package com.wzz.ownv.service;/**
 * Created by Enzo Cotter on 2020/9/10.
 */

import com.baomidou.mybatisplus.extension.service.IService;
import com.wzz.ownv.entity.WriteLog;

import java.util.List;

/**
 * @program: ownv
 * @description: 日志接口
 * @author: wzz
 * @create: 2020-09-10 16:39
 */
public interface IWriteLogService extends IService<WriteLog> {

    List<WriteLog> getWriteLogListByUserId(String userId);

}
