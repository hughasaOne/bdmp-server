package com.rhy.bdmp.base.modules.assets.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesTollStationLane;
import com.rhy.bdmp.base.modules.assets.dao.BaseFacilitiesTollStationLaneDao;
import com.rhy.bdmp.base.modules.assets.service.IBaseFacilitiesTollStationLaneService;
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
 * @author jiangzhimin
 * @date 2021-12-02 10:06
 * @version V1.0
 **/
@Service
public class BaseFacilitiesTollStationLaneServiceImpl extends ServiceImpl<BaseFacilitiesTollStationLaneDao, FacilitiesTollStationLane> implements IBaseFacilitiesTollStationLaneService {

    /**
     * 列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<FacilitiesTollStationLane> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<FacilitiesTollStationLane> query = new Query<FacilitiesTollStationLane>(queryVO);
            QueryWrapper<FacilitiesTollStationLane> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                List<FacilitiesTollStationLane> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<FacilitiesTollStationLane> queryWrapper = new QueryWrapper<>();
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
    public PageUtil<FacilitiesTollStationLane> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<FacilitiesTollStationLane> query = new Query<FacilitiesTollStationLane>(queryVO);
            Page<FacilitiesTollStationLane> page = query.getPage();
            QueryWrapper<FacilitiesTollStationLane> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<FacilitiesTollStationLane>(page);
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
    public FacilitiesTollStationLane detail(String id) {
        if (!StrUtil.isNotBlank(id)) {
            throw new BadRequestException("not exist id");
        }
        FacilitiesTollStationLane facilitiesTollStationLane = getBaseMapper().selectById(id);
        return facilitiesTollStationLane;
    }

    /**
     * 新增
     * @param facilitiesTollStationLane
     * @return
     */
    @Override
    public int create(FacilitiesTollStationLane facilitiesTollStationLane) {
        if (StrUtil.isNotBlank(facilitiesTollStationLane.getId())) {
            throw new BadRequestException("A new FacilitiesTollStationLane cannot already have an id");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        facilitiesTollStationLane.setCreateBy(currentUser);
        facilitiesTollStationLane.setCreateTime(currentDateTime);
        facilitiesTollStationLane.setUpdateBy(currentUser);
        facilitiesTollStationLane.setUpdateTime(currentDateTime);
        int result = getBaseMapper().insert(facilitiesTollStationLane);
        return result;
    }

    /**
     * 修改
     * @param facilitiesTollStationLane
     * @return
     */
    @Override
    public int update(FacilitiesTollStationLane facilitiesTollStationLane) {
       if (!StrUtil.isNotBlank(facilitiesTollStationLane.getId())) {
           throw new BadRequestException("A new FacilitiesTollStationLane not exist id");
       }
           String currentUser = WebUtils.getUserId();
       Date currentDateTime = DateUtil.date();
       facilitiesTollStationLane.setUpdateBy(currentUser);
       facilitiesTollStationLane.setUpdateTime(currentDateTime);
       int result = getBaseMapper().updateById(facilitiesTollStationLane);
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
