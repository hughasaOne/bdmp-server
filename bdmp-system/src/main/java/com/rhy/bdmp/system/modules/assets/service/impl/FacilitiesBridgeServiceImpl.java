package com.rhy.bdmp.system.modules.assets.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.base.modules.assets.dao.BaseFacilitiesBridgeDao;
import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesBridge;
import com.rhy.bdmp.system.modules.assets.service.IFacilitiesBridgeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created on 2021/10/25.
 *
 * @author duke
 */
@Service
public class FacilitiesBridgeServiceImpl implements IFacilitiesBridgeService {
    @Resource
    private BaseFacilitiesBridgeDao baseFacilitiesBridgeDao;

    @Override
    public int create(FacilitiesBridge bridge) {
        if(StrUtil.isBlank(bridge.getFacilitiesId())){
            throw new BadRequestException("没有获取到设施ID，无法新增");
        }
        return baseFacilitiesBridgeDao.insert(bridge);
    }

    @Override
    public int delete(Set<String> bridgeIds) {
        LambdaQueryWrapper<FacilitiesBridge> wrapper = Wrappers.lambdaQuery();
        if (CollectionUtil.isNotEmpty(bridgeIds)) {
            wrapper.in(FacilitiesBridge::getBridgeId, bridgeIds);
        }
        return baseFacilitiesBridgeDao.delete(wrapper);
    }

    @Override
    public int batchDelete(Set<String> facilitiesIds) {
        LambdaQueryWrapper<FacilitiesBridge> wrapper = Wrappers.lambdaQuery();
        wrapper.in(FacilitiesBridge::getFacilitiesId,facilitiesIds);
        return baseFacilitiesBridgeDao.delete(wrapper);
    }
}
