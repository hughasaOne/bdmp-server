package com.rhy.bdmp.base.modules.sys.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.Query;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.base.modules.sys.dao.BaseWayMappingDao;
import com.rhy.bdmp.base.modules.sys.domain.po.WayMapping;
import com.rhy.bdmp.base.modules.sys.service.IBaseWayMappingService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @description  服务实现
 * @author weicaifu
 * @date 2023-01-09 09:19
 * @version V1.0
 **/
@Service
public class BaseWayMappingServiceImpl extends ServiceImpl<BaseWayMappingDao, WayMapping> implements IBaseWayMappingService {

    /**
     * 列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<WayMapping> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<WayMapping> query = new Query<WayMapping>(queryVO);
            QueryWrapper<WayMapping> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                List<WayMapping> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<WayMapping> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        queryWrapper.orderByDesc("create_time");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<WayMapping> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<WayMapping> query = new Query<WayMapping>(queryVO);
            Page<WayMapping> page = query.getPage();
            QueryWrapper<WayMapping> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<WayMapping>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看(根据ID)
     * @param id
     * @return
     */
    @Override
    public WayMapping detail(String id) {
        if (!StrUtil.isNotBlank(id)) {
            throw new BadRequestException("not exist id");
        }
        WayMapping wayMapping = getBaseMapper().selectById(id);
        return wayMapping;
    }

    /**
     * 新增
     * @param wayMapping
     * @return
     */
    @Override
    public int create(WayMapping wayMapping) {
        if (StrUtil.isNotBlank(wayMapping.getId())) {
            throw new BadRequestException("A new WayMapping cannot already have an id");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        wayMapping.setCreateBy(currentUser);
        wayMapping.setCreateTime(currentDateTime);
        wayMapping.setUpdateBy(currentUser);
        wayMapping.setUpdateTime(currentDateTime);
        int result = getBaseMapper().insert(wayMapping);
        return result;
    }

    /**
     * 修改
     * @param wayMapping
     * @return
     */
    @Override
    public int update(WayMapping wayMapping) {
       if (!StrUtil.isNotBlank(wayMapping.getId())) {
           throw new BadRequestException("A new WayMapping not exist id");
       }
       String currentUser = WebUtils.getUserId();
       Date currentDateTime = DateUtil.date();
       wayMapping.setUpdateBy(currentUser);
       wayMapping.setUpdateTime(currentDateTime);
       int result = getBaseMapper().updateById(wayMapping);
       return result;
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @Override
    public int delete(Set<String> ids) {
        int result = getBaseMapper().deleteBatchIds(ids);
        return result;
    }

}
