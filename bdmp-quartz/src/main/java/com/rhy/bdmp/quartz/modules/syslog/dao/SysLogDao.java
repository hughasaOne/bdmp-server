package com.rhy.bdmp.quartz.modules.syslog.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.datasource.annotation.DataSource;
import com.rhy.bdmp.quartz.modules.syslog.domain.bo.SysLogExt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author shuaichao
 * @create 2022-03-15 14:03
 */
@Mapper
public interface SysLogDao {

    @DataSource("basedata")
    Page<SysLogExt> selectPage(Page<SysLogExt> page,
                               @Param("sysLog") SysLogExt sysLogExt,
                               @Param("isAdmin") Boolean isAdmin,
                               @Param("userId") String userId);

    int cleanLogByDate(@Param("timed") String timed);
}
