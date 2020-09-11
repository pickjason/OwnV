package com.wzz.ownv.entity;/**
 * Created by Enzo Cotter on 2020/9/10.
 */

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @program: ownv
 * @description: 日志
 * @author: wzz
 * @create: 2020-09-10 16:35
 */
@Data
public class WriteLog {
    @TableId(value = "write_log_id")
    private String writeLogId;

    private String userId;

    private String content;

    private String weather;

    private String labelId;

    private Integer isShare;

    private String mood;

    private Long createTime;


}
