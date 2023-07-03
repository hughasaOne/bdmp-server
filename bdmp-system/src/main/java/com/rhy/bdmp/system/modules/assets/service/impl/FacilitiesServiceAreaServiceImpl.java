package com.rhy.bdmp.system.modules.assets.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bdmp.base.modules.assets.dao.BaseFacilitiesServiceAreaDao;
import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesGantry;
import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesServiceArea;
import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesTunnel;
import com.rhy.bdmp.system.modules.assets.service.IFacilitiesServiceAreaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Created on 2021/10/25.
 *
 * @author duke
 */
@Service
public class FacilitiesServiceAreaServiceImpl implements IFacilitiesServiceAreaService {
    @Resource
    private BaseFacilitiesServiceAreaDao baseFacilitiesServiceAreaDao;
    @Override
    public int create(FacilitiesServiceArea serviceArea) {
        if(StrUtil.isBlank(serviceArea.getFacilitiesId())){
            throw new BadRequestException("没有获取到设施ID，无法新增");
        }
        return baseFacilitiesServiceAreaDao.insert(serviceArea);
    }

    @Override
    public int delete(Set<String> serviceAreaIds) {
        LambdaQueryWrapper<FacilitiesServiceArea> wrapper = Wrappers.lambdaQuery();
        if (CollectionUtil.isNotEmpty(serviceAreaIds)) {
            wrapper.in(FacilitiesServiceArea::getServiceAreaId, serviceAreaIds);
        }
        return baseFacilitiesServiceAreaDao.delete(wrapper);
    }

    @Override
    public int batchDelete(Set<String> facilitiesIds) {
        LambdaQueryWrapper<FacilitiesServiceArea> wrapper = Wrappers.lambdaQuery();
        wrapper.in(FacilitiesServiceArea::getFacilitiesId,facilitiesIds);
        return baseFacilitiesServiceAreaDao.delete(wrapper);
    }
}
