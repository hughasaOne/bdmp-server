package com.rhy.bdmp.quartz.modules.sysjob.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.sys.domain.po.SysJob;
import com.rhy.bdmp.quartz.modules.common.to.UserAppPermissions;
import com.rhy.bdmp.quartz.modules.sysjob.domain.bo.SysJobBo;
import com.rhy.bdmp.quartz.modules.sysjob.domain.bo.SysJobExt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @description  数据操作接口
 * @author shuaichao
 * @date 2022-03-11 17:47
 * @version V1.0
 **/
@Mapper
public interface SysJobDao extends BaseMapper<SysJob> {


    Page<SysJobExt> selectSysJobPage(Page page,
                                     @Param("sysJobBo") SysJobBo sysJobBo,
                                     @Param("userPermissions") UserAppPermissions userPermissions);

    SysJobExt selectSysJobExtById(@Param("id") String id);

    List<SysJobExt> queryList(@Param("sysJob") SysJob sysJob);

    List<SysJobExt> selectSysJobExtByIds(@Param("ids") List<String> ids);
}