package com.rhy.bdmp.base.modules.assets.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.assets.domain.po.Waysection;
import com.rhy.bdmp.base.modules.assets.dao.BaseWaysectionDao;
import com.rhy.bdmp.base.modules.assets.service.IBaseWaysectionService;
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
 * @description 运营路段 服务实现
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Service
public class BaseWaysectionServiceImpl extends ServiceImpl<BaseWaysectionDao, Waysection> implements IBaseWaysectionService {

    /**
     * 运营路段列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<Waysection> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<Waysection> query = new Query<Waysection>(queryVO);
            QueryWrapper<Waysection> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                List<Waysection> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<Waysection> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        queryWrapper.orderByDesc("create_time");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 运营路段列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<Waysection> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<Waysection> query = new Query<Waysection>(queryVO);
            Page<Waysection> page = query.getPage();
            QueryWrapper<Waysection> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<Waysection>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看运营路段(根据ID)
     * @param waysectionId
     * @return
     */
    @Override
    public Waysection detail(String waysectionId) {
        if (!StrUtil.isNotBlank(waysectionId)) {
            throw new BadRequestException("not exist waysectionId");
        }
        Waysection waysection = getBaseMapper().selectById(waysectionId);
        return waysection;
    }

    /**
     * 新增运营路段
     * @param waysection
     * @return
     */
    @Override
    public int create(Waysection waysection) {
        if (StrUtil.isNotBlank(waysection.getWaysectionId())) {
            throw new BadRequestException("A new Waysection cannot already have an waysectionId");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        waysection.setCreateBy(currentUser);
        waysection.setCreateTime(currentDateTime);
        waysection.setUpdateBy(currentUser);
        waysection.setUpdateTime(currentDateTime);
        int result = getBaseMapper().insert(waysection);
        return result;
    }

    /**
     * 修改运营路段
     * @param waysection
     * @return
     */
    @Override
    public int update(Waysection waysection) {
       if (!StrUtil.isNotBlank(waysection.getWaysectionId())) {
           throw new BadRequestException("A new Waysection not exist waysectionId");
       }
           String currentUser = WebUtils.getUserId();
       Date currentDateTime = DateUtil.date();
       waysection.setUpdateBy(currentUser);
       waysection.setUpdateTime(currentDateTime);
       int result = getBaseMapper().updateById(waysection);
       return result;
    }

    /**
     * 删除运营路段
     * @param waysectionIds
     * @return
     */
    @Override
    public int delete(Set<String> waysectionIds) {
        int result = getBaseMapper().deleteBatchIds(waysectionIds);
        return result;
    }

}
