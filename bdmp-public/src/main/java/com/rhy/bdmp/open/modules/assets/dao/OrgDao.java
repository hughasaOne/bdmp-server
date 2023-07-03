package com.rhy.bdmp.open.modules.assets.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.open.modules.assets.domain.vo.OrgTreeVo;
import com.rhy.bdmp.open.modules.assets.domain.vo.OrgVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 组织服务
 *
 * @author weicaifu
 */
@Mapper
public interface OrgDao {
    Page<OrgVo> getOrgList(Page<OrgVo> page, @Param("orgTypes") Set<String> orgTypes);

    List<OrgVo> getOrgList(@Param("orgTypes") Set<String> orgTypes);

    OrgVo getOrgInfoById(@Param("orgId") String orgId);

    List<OrgTreeVo> getOrgInfoByParentId(@Param("parentId") String parentId);
}
