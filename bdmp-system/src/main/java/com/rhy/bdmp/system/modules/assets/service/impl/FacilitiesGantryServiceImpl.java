package com.rhy.bdmp.system.modules.assets.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bdmp.base.modules.assets.dao.BaseFacilitiesGantryDao;
import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesBridge;
import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesGantry;
import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesServiceArea;
import com.rhy.bdmp.system.modules.assets.service.IFacilitiesGantryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Created on 2021/10/25.
 *
 * @author duke
 */
@Service
public class FacilitiesGantryServiceImpl implements IFacilitiesGantryService {
    @Resource
    private BaseFacilitiesGantryDao baseFacilitiesGantryDao;

    @Override
    public int create(FacilitiesGantry gantry) {
        if(StrUtil.isBlank(gantry.getFacilitiesId())){
            throw new BadRequestException("没有获取到设施ID，无法新增");
        }
        return baseFacilitiesGantryDao.insert(gantry);
    }

    @Override
    public int delete(Set<String> gantryIds) {
        LambdaQueryWrapper<FacilitiesGantry> wrapper = Wrappers.lambdaQuery();
        if (CollectionUtil.isNotEmpty(gantryIds)) {
            wrapper.in(FacilitiesGantry::getGantryId, gantryIds);
        }
        return baseFacilitiesGantryDao.delete(wrapper);
    }

    @Override
    public int batchDelete(Set<String> facilitiesIds) {
        LambdaQueryWrapper<FacilitiesGantry> wrapper = Wrappers.lambdaQuery();
        wrapper.in(FacilitiesGantry::getFacilitiesId,facilitiesIds);
        return baseFacilitiesGantryDao.delete(wrapper);
    }
}
