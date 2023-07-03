package com.rhy.bdmp.open.modules.assets.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bdmp.open.modules.assets.dao.OrgDao;
import com.rhy.bdmp.open.modules.assets.domain.bo.OrgListBo;
import com.rhy.bdmp.open.modules.assets.domain.vo.OrgTreeVo;
import com.rhy.bdmp.open.modules.assets.domain.vo.OrgVo;
import com.rhy.bdmp.open.modules.assets.service.IOrgService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * 组织服务
 * @author weicaifu
 */
@Service
public class OrgServiceImpl implements IOrgService {

    @Resource
    private OrgDao orgDao;

    /**
     * 查询组织列表
     */
    @Override
    public Object getOrgList(OrgListBo orgListBo) {
        Set<String> orgTypes = orgListBo.getOrgTypes();
        Integer currentPage = orgListBo.getCurrentPage();
        Integer pageSize = orgListBo.getPageSize();

        Page<OrgVo> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(pageSize);
        page.setOptimizeCountSql(false);

        if (CollUtil.isEmpty(orgTypes)){
            return null;
        }

        if (0 == pageSize){
            return orgDao.getOrgList(orgTypes);
        }else{
            return new PageUtil<OrgVo>(orgDao.getOrgList(page, orgTypes));
        }
    }

    /**
     * 获取组织详情
     * @param orgId 组织id
     */
    @Override
    public OrgVo getOrgInfoById(String orgId) {
        return orgDao.getOrgInfoById(orgId);
    }

    /**
     * 获取组织详情
     * @param parentId 父组织id
     */
    @Override
    public List<OrgTreeVo> getOrgInfoByParentId(String parentId) {
        return orgDao.getOrgInfoByParentId(parentId);
    }

}
