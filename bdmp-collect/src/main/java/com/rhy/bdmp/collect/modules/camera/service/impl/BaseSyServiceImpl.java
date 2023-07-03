package com.rhy.bdmp.collect.modules.camera.service.impl;

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
import com.rhy.bdmp.collect.modules.camera.dao.BaseSyDao;
import com.rhy.bdmp.collect.modules.camera.domain.po.Sy;
import com.rhy.bdmp.collect.modules.camera.service.IBaseSyService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @description 视频资源(云台) 服务实现
 * @author weicaifu
 * @date 2022-06-07 11:49
 * @version V1.0
 **/
@Service
public class BaseSyServiceImpl extends ServiceImpl<BaseSyDao, Sy> implements IBaseSyService {

    /**
     * 视频资源(云台)列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<Sy> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<Sy> query = new Query<Sy>(queryVO);
            QueryWrapper<Sy> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                List<Sy> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<Sy> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        queryWrapper.orderByDesc("create_time");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 视频资源(云台)列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<Sy> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<Sy> query = new Query<Sy>(queryVO);
            Page<Sy> page = query.getPage();
            QueryWrapper<Sy> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<Sy>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看视频资源(云台)(根据ID)
     * @param id
     * @return
     */
    @Override
    public Sy detail(String id) {
        if (!StrUtil.isNotBlank(id)) {
            throw new BadRequestException("not exist id");
        }
        Sy sy = getBaseMapper().selectById(id);
        return sy;
    }

    /**
     * 新增视频资源(云台)
     * @param sy
     * @return
     */
    @Override
    public int create(Sy sy) {
        if (StrUtil.isNotBlank(sy.getId())) {
            throw new BadRequestException("A new Sy cannot already have an id");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        sy.setCreateBy(currentUser);
        sy.setCreateTime(currentDateTime);
        sy.setUpdateBy(currentUser);
        sy.setUpdateTime(currentDateTime);
        int result = getBaseMapper().insert(sy);
        return result;
    }

    /**
     * 修改视频资源(云台)
     * @param sy
     * @return
     */
    @Override
    public int update(Sy sy) {
       if (!StrUtil.isNotBlank(sy.getId())) {
           throw new BadRequestException("A new Sy not exist id");
       }
       String currentUser = WebUtils.getUserId();
       Date currentDateTime = DateUtil.date();
       sy.setUpdateBy(currentUser);
       sy.setUpdateTime(currentDateTime);
       int result = getBaseMapper().updateById(sy);
       return result;
    }

    /**
     * 删除视频资源(云台)
     * @param ids
     * @return
     */
    @Override
    public int delete(Set<String> ids) {
        int result = getBaseMapper().deleteBatchIds(ids);
        return result;
    }

}
