package com.wzz.ownv.service.impl;/**
 * Created by Enzo Cotter on 2020/9/10.
 */

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wzz.ownv.entity.WriteLog;
import com.wzz.ownv.mapper.WriteLogMapper;
import com.wzz.ownv.service.IWriteLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: ownv
 * @description: 实现
 * @author: wzz
 * @create: 2020-09-10 16:40
 */
@Service
public class IWriteLogServiceImpl extends ServiceImpl<WriteLogMapper, WriteLog> implements IWriteLogService {
    @Autowired
    private WriteLogMapper writeLogMapper;

    @Override
    public List<WriteLog> getWriteLogListByUserId(String userId) {
        return  writeLogMapper.getListByUserId(userId);
    }
}
