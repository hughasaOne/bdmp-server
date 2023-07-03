package com.rhy.bdmp.quartz.modules.sysbusiness.dao;

import com.rhy.bcp.common.datasource.annotation.DataSource;
import com.rhy.bdmp.base.modules.sys.domain.po.SysBusiness;
import com.rhy.bdmp.quartz.modules.sysbusiness.domain.bo.SysBusinessExt;
import com.rhy.bdmp.quartz.modules.sysbusiness.domain.vo.SysBusinessExtVo;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author shuaichao
 * @create 2022-03-15 14:55
 */
@Mapper
public interface SysBusinessDao {

    List<SysBusinessExt> findByPIds(@Param("ids") List<String> sysBusinessIds);

    @DataSource("basedata")
    List<SysBusinessExtVo> findByUserId(@Param("userId") String userId,@Param("isAdmin") Boolean isAdmin);

    List<SysBusinessExt> findByParam(@Param("sysBusiness") SysBusiness sysBusiness);
}
