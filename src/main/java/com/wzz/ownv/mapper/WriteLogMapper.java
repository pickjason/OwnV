package com.wzz.ownv.mapper;/**
 * Created by Enzo Cotter on 2020/9/10.
 */

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzz.ownv.entity.WriteLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: ownv
 * @description: x
 * @author: wzz
 * @create: 2020-09-10 16:42
 */
public interface WriteLogMapper extends BaseMapper<WriteLog> {

    List<WriteLog> getListByUserId(@Param("userId")String userId);

}
