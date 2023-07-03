package com.rhy.bdmp.base.modules.sys.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.sys.domain.po.Org;
import com.rhy.bdmp.base.modules.sys.dao.BaseOrgDao;
import com.rhy.bdmp.base.modules.sys.service.IBaseOrgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.Query;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bcp.common.util.WebUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @description 组织机构 服务实现
 * @author yanggj
 * @date 2021-09-24 16:32
 * @version V1.0
 **/
@Service
public class BaseOrgServiceImpl extends ServiceImpl<BaseOrgDao, Org> implements IBaseOrgService {

    /**
     * 组织机构列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<Org> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<Org> query = new Query<Org>(queryVO);
            QueryWrapper<Org> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                List<Org> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<Org> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        queryWrapper.orderByDesc("create_time");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 组织机构列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<Org> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<Org> query = new Query<Org>(queryVO);
            Page<Org> page = query.getPage();
            QueryWrapper<Org> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<Org>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看组织机构(根据ID)
     * @param orgId
     * @return
     */
    @Override
    public Org detail(String orgId) {
        if (!StrUtil.isNotBlank(orgId)) {
            throw new BadRequestException("not exist orgId");
        }
        Org org = getBaseMapper().selectById(orgId);
        return org;
    }

    /**
     * 新增组织机构
     * @param org
     * @return
     */
    @Override
    public int create(Org org) {
        if (StrUtil.isNotBlank(org.getOrgId())) {
            throw new BadRequestException("A new Org cannot already have an orgId");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        org.setCreateBy(currentUser);
        org.setCreateTime(currentDateTime);
        org.setUpdateBy(currentUser);
        org.setUpdateTime(currentDateTime);
        int result = getBaseMapper().insert(org);
        return result;
    }

    /**
     * 修改组织机构
     * @param org
     * @return
     */
    @Override
    public int update(Org org) {
       if (!StrUtil.isNotBlank(org.getOrgId())) {
           throw new BadRequestException("A new Org not exist orgId");
       }
           String currentUser = WebUtils.getUserId();
       Date currentDateTime = DateUtil.date();
       org.setUpdateBy(currentUser);
       org.setUpdateTime(currentDateTime);
       int result = getBaseMapper().updateById(org);
       return result;
    }

    /**
     * 删除组织机构
     * @param orgIds
     * @return
     */
    @Override
    public int delete(Set<String> orgIds) {
        int result = getBaseMapper().deleteBatchIds(orgIds);
        return result;
    }

}
