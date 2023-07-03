package com.rhy.bdmp.base.modules.assets.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesGantry;
import com.rhy.bdmp.base.modules.assets.dao.BaseFacilitiesGantryDao;
import com.rhy.bdmp.base.modules.assets.service.IBaseFacilitiesGantryService;
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
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Service
public class BaseFacilitiesGantryServiceImpl extends ServiceImpl<BaseFacilitiesGantryDao, FacilitiesGantry> implements IBaseFacilitiesGantryService {

    /**
     * 列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<FacilitiesGantry> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<FacilitiesGantry> query = new Query<FacilitiesGantry>(queryVO);
            QueryWrapper<FacilitiesGantry> queryWrapper = query.getQueryWrapper();
                List<FacilitiesGantry> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<FacilitiesGantry> queryWrapper = new QueryWrapper<>();
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<FacilitiesGantry> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<FacilitiesGantry> query = new Query<FacilitiesGantry>(queryVO);
            Page<FacilitiesGantry> page = query.getPage();
            QueryWrapper<FacilitiesGantry> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<FacilitiesGantry>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看(根据ID)
     * @param gantryId
     * @return
     */
    @Override
    public FacilitiesGantry detail(String gantryId) {
        if (!StrUtil.isNotBlank(gantryId)) {
            throw new BadRequestException("not exist gantryId");
        }
        FacilitiesGantry facilitiesGantry = getBaseMapper().selectById(gantryId);
        return facilitiesGantry;
    }

    /**
     * 新增
     * @param facilitiesGantry
     * @return
     */
    @Override
    public int create(FacilitiesGantry facilitiesGantry) {
        if (StrUtil.isNotBlank(facilitiesGantry.getGantryId())) {
            throw new BadRequestException("A new FacilitiesGantry cannot already have an gantryId");
        }
        int result = getBaseMapper().insert(facilitiesGantry);
        return result;
    }

    /**
     * 修改
     * @param facilitiesGantry
     * @return
     */
    @Override
    public int update(FacilitiesGantry facilitiesGantry) {
       if (!StrUtil.isNotBlank(facilitiesGantry.getGantryId())) {
           throw new BadRequestException("A new FacilitiesGantry not exist gantryId");
       }
       int result = getBaseMapper().updateById(facilitiesGantry);
       return result;
    }

    /**
     * 删除
     * @param gantryIds
     * @return
     */
    @Override
    public int delete(Set<String> gantryIds) {
        int result = getBaseMapper().deleteBatchIds(gantryIds);
        return result;
    }

}
