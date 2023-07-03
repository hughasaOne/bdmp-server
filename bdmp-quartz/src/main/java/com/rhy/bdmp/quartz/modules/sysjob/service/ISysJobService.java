package com.rhy.bdmp.quartz.modules.sysjob.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.sys.domain.po.SysJob;
import com.rhy.bdmp.quartz.modules.sysjob.domain.bo.SysJobBo;
import com.rhy.bdmp.quartz.modules.sysjob.domain.bo.SysJobExt;

import java.util.List;

/**
 * @author shuaichao
 * @create 2022-03-11 16:57
 */
public interface ISysJobService {

    SysJob selectByJobName(String jobName);

    List<SysJobExt> list(SysJob sysJob) ;

    Page<SysJobExt> page(SysJobBo sysJobBo, Integer currentPage, Integer size) ;

    SysJobExt detail(String sysJobId) ;

    void create(SysJobExt sysJobExt) ;

    void update(SysJobExt sysJobExt) throws  Exception;

    void remove(List<String> jobIds) throws Exception;
}
