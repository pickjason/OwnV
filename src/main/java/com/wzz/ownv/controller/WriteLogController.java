package com.wzz.ownv.controller;/**
 * Created by Enzo Cotter on 2020/9/8.
 */

import com.wzz.ownv.common.result.Result;
import com.wzz.ownv.common.utils.UUIDUtil;
import com.wzz.ownv.domain.dto.WriteDTO;
import com.wzz.ownv.entity.WriteLog;
import com.wzz.ownv.service.IWriteLogService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: ownv
 * @description: 写日志咯
 * @author: wzz
 * @create: 2020-09-08 15:25
 */

@RestController
@Slf4j
@RequestMapping("/api/wlog")
public class WriteLogController {
@Autowired
private IWriteLogService iWriteLogService;

    @GetMapping("/wlog_list")
    public Result getWriteLogList(HttpServletRequest httpServletRequest) {
        String userId = (String) httpServletRequest.getAttribute("userId");
        if (StringUtils.isNotBlank(userId)) {
            return new Result(200, "success", iWriteLogService.getWriteLogListByUserId(userId));
        } else {
            return new Result(401, "not user", null);
        }
    }


    @PostMapping("/write")
    public Result addWrite(@RequestBody WriteDTO writeDTO,HttpServletRequest httpServletRequest){
        String userId = (String) httpServletRequest.getAttribute("userId");
        WriteLog writeLog = new WriteLog();
        writeLog.setWriteLogId(UUIDUtil.UUId());
        writeLog.setUserId(userId);
        writeLog.setContent(writeDTO.getContent());
        writeLog.setCreateTime(System.currentTimeMillis());
        writeLog.setMood("happy");
        writeLog.setIsShare(0);
        writeLog.setWeather("晴");
        boolean save = iWriteLogService.save(writeLog);
        return new Result(200, "success", null);
    }





}
