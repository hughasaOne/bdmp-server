package com.rhy.bdmp.open.modules.assets.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.open.modules.assets.domain.bo.OrgListBo;
import com.rhy.bdmp.open.modules.assets.domain.vo.OrgTreeVo;
import com.rhy.bdmp.open.modules.assets.domain.vo.OrgVo;

import java.util.List;
import java.util.Set;

/**
 * 组织服务
 * @author weicaifu
 */
public interface IOrgService {

    /**
     * 查询组织列表
     */
    Object getOrgList(OrgListBo orgListBo);

    /**
     * 获取组织详情
     * @param orgId 组织id
     */
    OrgVo getOrgInfoById(String orgId);

    /**
     * 获取组织详情
     * @param parentId 父组织id
     */
    List<OrgTreeVo> getOrgInfoByParentId(String parentId);
}
