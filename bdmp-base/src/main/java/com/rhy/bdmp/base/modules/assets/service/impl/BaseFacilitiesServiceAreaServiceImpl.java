package com.rhy.bdmp.base.modules.assets.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.Query;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.dao.BaseFacilitiesServiceAreaDao;
import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesServiceArea;
import com.rhy.bdmp.base.modules.assets.service.IBaseFacilitiesServiceAreaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @description  服务实现
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Service
public class BaseFacilitiesServiceAreaServiceImpl extends ServiceImpl<BaseFacilitiesServiceAreaDao, FacilitiesServiceArea> implements IBaseFacilitiesServiceAreaService {

    /**
     * 列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<FacilitiesServiceArea> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<FacilitiesServiceArea> query = new Query<FacilitiesServiceArea>(queryVO);
            QueryWrapper<FacilitiesServiceArea> queryWrapper = query.getQueryWrapper();
                List<FacilitiesServiceArea> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<FacilitiesServiceArea> queryWrapper = new QueryWrapper<>();
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<FacilitiesServiceArea> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<FacilitiesServiceArea> query = new Query<FacilitiesServiceArea>(queryVO);
            Page<FacilitiesServiceArea> page = query.getPage();
            QueryWrapper<FacilitiesServiceArea> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<FacilitiesServiceArea>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看(根据ID)
     * @param serviceAreaId
     * @return
     */
    @Override
    public FacilitiesServiceArea detail(String serviceAreaId) {
        if (!StrUtil.isNotBlank(serviceAreaId)) {
            throw new BadRequestException("not exist serviceAreaId");
        }
        FacilitiesServiceArea facilitiesServiceArea = getBaseMapper().selectById(serviceAreaId);
        return facilitiesServiceArea;
    }

    /**
     * 新增
     * @param facilitiesServiceArea
     * @return
     */
    @Override
    public int create(FacilitiesServiceArea facilitiesServiceArea) {
        if (null != facilitiesServiceArea.getServiceAreaId()) {
            throw new BadRequestException("A new FacilitiesServiceArea cannot already have an serviceAreaId");
        }
        int result = getBaseMapper().insert(facilitiesServiceArea);
        return result;
    }

    /**
     * 修改
     * @param facilitiesServiceArea
     * @return
     */
    @Override
    public int update(FacilitiesServiceArea facilitiesServiceArea) {
       if (null == facilitiesServiceArea.getServiceAreaId()) {
           throw new BadRequestException("A new FacilitiesServiceArea not exist serviceAreaId");
       }
       int result = getBaseMapper().updateById(facilitiesServiceArea);
       return result;
    }

    /**
     * 删除
     * @param serviceAreaIds
     * @return
     */
    @Override
    public int delete(Set<Integer> serviceAreaIds) {
        int result = getBaseMapper().deleteBatchIds(serviceAreaIds);
        return result;
    }

}
