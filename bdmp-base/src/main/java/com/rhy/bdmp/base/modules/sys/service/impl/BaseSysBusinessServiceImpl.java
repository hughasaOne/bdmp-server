package com.rhy.bdmp.base.modules.sys.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.sys.domain.po.SysBusiness;
import com.rhy.bdmp.base.modules.sys.dao.BaseSysBusinessDao;
import com.rhy.bdmp.base.modules.sys.service.IBaseSysBusinessService;
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
 * @description  服务实现
 * @author shuaichao
 * @date 2022-03-21 10:14
 * @version V1.0
 **/
@Service
public class BaseSysBusinessServiceImpl extends ServiceImpl<BaseSysBusinessDao, SysBusiness> implements IBaseSysBusinessService {

    /**
     * 列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<SysBusiness> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<SysBusiness> query = new Query<SysBusiness>(queryVO);
            QueryWrapper<SysBusiness> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByDesc("create_time");
                queryWrapper.orderByAsc("sort");
                List<SysBusiness> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<SysBusiness> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        queryWrapper.orderByAsc("sort");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<SysBusiness> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<SysBusiness> query = new Query<SysBusiness>(queryVO);
            Page<SysBusiness> page = query.getPage();
            QueryWrapper<SysBusiness> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByDesc("create_time");
                queryWrapper.orderByAsc("sort");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<SysBusiness>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看(根据ID)
     * @param businessId
     * @return
     */
    @Override
    public SysBusiness detail(String businessId) {
        if (!StrUtil.isNotBlank(businessId)) {
            throw new BadRequestException("not exist businessId");
        }
        SysBusiness sysBusiness = getBaseMapper().selectById(businessId);
        return sysBusiness;
    }

    /**
     * 新增
     * @param sysBusiness
     * @return
     */
    @Override
    public int create(SysBusiness sysBusiness) {
        if (StrUtil.isNotBlank(sysBusiness.getBusinessId())) {
            throw new BadRequestException("A new SysBusiness cannot already have an businessId");
        }
        Date currentDateTime = DateUtil.date();
        String currentUser = WebUtils.getUserId();
        sysBusiness.setUpdateTime(currentDateTime);
        sysBusiness.setUpdateBy(currentUser);
        sysBusiness.setCreateTime(currentDateTime);
        sysBusiness.setCreateBy(currentUser);
        int result = getBaseMapper().insert(sysBusiness);
        return result;
    }

    /**
     * 修改
     * @param sysBusiness
     * @return
     */
    @Override
    public int update(SysBusiness sysBusiness) {
       if (!StrUtil.isNotBlank(sysBusiness.getBusinessId())) {
           throw new BadRequestException("A new SysBusiness not exist businessId");
       }
       Date currentDateTime = DateUtil.date();
       String currentUser = WebUtils.getUserId();
       sysBusiness.setUpdateTime(currentDateTime);
       sysBusiness.setUpdateBy(currentUser);
       int result = getBaseMapper().updateById(sysBusiness);
       return result;
    }

    /**
     * 删除
     * @param businessIds
     * @return
     */
    @Override
    public int delete(Set<String> businessIds) {
        int result = getBaseMapper().deleteBatchIds(businessIds);
        return result;
    }

}
