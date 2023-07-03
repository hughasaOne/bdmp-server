package com.rhy.bdmp.system.modules.assets.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bdmp.base.modules.assets.dao.BaseFacilitiesTollStationDao;
import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesServiceArea;
import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesTollStation;
import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesTunnel;
import com.rhy.bdmp.system.modules.assets.service.IFacilitiesTollStationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Created on 2021/10/25.
 *
 * @author duke
 */
@Service
public class FacilitiesTollStationServiceImpl implements IFacilitiesTollStationService {
    @Resource
    private BaseFacilitiesTollStationDao baseFacilitiesTollStationDao;

    @Override
    public int create(FacilitiesTollStation tollStation) {
        if(StrUtil.isBlank(tollStation.getFacilitiesId())){
            throw new BadRequestException("没有获取到设施ID，无法新增");
        }
        return baseFacilitiesTollStationDao.insert(tollStation);
    }

    @Override
    public int delete(Set<String> tollStationIds) {
        LambdaQueryWrapper<FacilitiesTollStation> wrapper = Wrappers.lambdaQuery();
        if (CollectionUtil.isNotEmpty(tollStationIds)) {
            wrapper.in(FacilitiesTollStation::getTollStationId, tollStationIds);
        }
        return baseFacilitiesTollStationDao.delete(wrapper);
    }

    @Override
    public int batchDelete(Set<String> facilitiesIds) {
        LambdaQueryWrapper<FacilitiesTollStation> wrapper = Wrappers.lambdaQuery();
        wrapper.in(FacilitiesTollStation::getFacilitiesId,facilitiesIds);
        return baseFacilitiesTollStationDao.delete(wrapper);
    }
}
