package com.rhy.bdmp.system.modules.assets.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bdmp.base.modules.assets.dao.BaseFacilitiesTunnelDao;
import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesBridge;
import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesTollStation;
import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesTunnel;
import com.rhy.bdmp.system.modules.assets.service.IFacilitiesTunnelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Created on 2021/10/25.
 *
 * @author duke
 */
@Service
public class FacilitiesTunnelServiceImpl implements IFacilitiesTunnelService {
    @Resource
    private BaseFacilitiesTunnelDao baseFacilitiesTunnelDao;

    @Override
    public int create(FacilitiesTunnel tunnel) {
        if(StrUtil.isBlank(tunnel.getFacilitiesId())){
            throw new BadRequestException("没有获取到设施ID，无法新增");
        }
        return baseFacilitiesTunnelDao.insert(tunnel);
    }

    @Override
    public int delete(Set<String> tunnelIds) {
        LambdaQueryWrapper<FacilitiesTunnel> wrapper = Wrappers.lambdaQuery();
        if (CollectionUtil.isNotEmpty(tunnelIds)) {
            wrapper.in(FacilitiesTunnel::getTunnelId, tunnelIds);
        }
        return baseFacilitiesTunnelDao.delete(wrapper);
    }

    @Override
    public int batchDelete(Set<String> facilitiesIds) {
        LambdaQueryWrapper<FacilitiesTunnel> wrapper = Wrappers.lambdaQuery();
        wrapper.in(FacilitiesTunnel::getFacilitiesId,facilitiesIds);
        return baseFacilitiesTunnelDao.delete(wrapper);
    }
}
