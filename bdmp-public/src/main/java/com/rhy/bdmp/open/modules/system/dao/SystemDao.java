package com.rhy.bdmp.open.modules.system.dao;

import com.jtkj.cloud.db.tenant.anno.IgnorePemission;
import com.jtkj.cloud.db.tenant.anno.IgnoreTenant;
import com.rhy.bdmp.open.modules.system.domain.vo.OrgVo;
import com.rhy.bdmp.open.modules.system.domain.vo.ResourceVo;
import com.rhy.bdmp.open.modules.system.domain.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统服务
 * @author weicaifu
 */
@Mapper
@IgnoreTenant
@IgnorePemission
public interface SystemDao {
    List<ResourceVo> getUserMenuByPermissions(@Param("userId") String userId, @Param("appId") String appId);

    List<ResourceVo> getUserMenu(@Param("appId") String appId);

    List<OrgVo> getOrgList();

    OrgVo getOrgById(@Param("orgId") String orgId);

    List<UserVo> getUserList();
}
