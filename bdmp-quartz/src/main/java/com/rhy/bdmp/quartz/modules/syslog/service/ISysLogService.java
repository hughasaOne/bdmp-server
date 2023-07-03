package com.rhy.bdmp.quartz.modules.syslog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.quartz.modules.syslog.domain.bo.SysLogExt;

/**
 * @author shuaichao
 * @create 2022-03-15 13:54
 */
public interface ISysLogService {
    Page<SysLogExt> page(SysLogExt sysLogExt, Integer currentPage, Integer size);

}
