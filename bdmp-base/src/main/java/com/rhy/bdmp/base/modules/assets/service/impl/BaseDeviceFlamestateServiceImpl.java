package com.rhy.bdmp.base.modules.assets.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.assets.domain.po.DeviceFlamestate;
import com.rhy.bdmp.base.modules.assets.dao.BaseDeviceFlamestateDao;
import com.rhy.bdmp.base.modules.assets.service.IBaseDeviceFlamestateService;
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
 * @description 感温器 服务实现
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Service
public class BaseDeviceFlamestateServiceImpl extends ServiceImpl<BaseDeviceFlamestateDao, DeviceFlamestate> implements IBaseDeviceFlamestateService {

    /**
     * 感温器列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<DeviceFlamestate> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<DeviceFlamestate> query = new Query<DeviceFlamestate>(queryVO);
            QueryWrapper<DeviceFlamestate> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                List<DeviceFlamestate> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<DeviceFlamestate> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        queryWrapper.orderByDesc("create_time");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 感温器列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<DeviceFlamestate> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<DeviceFlamestate> query = new Query<DeviceFlamestate>(queryVO);
            Page<DeviceFlamestate> page = query.getPage();
            QueryWrapper<DeviceFlamestate> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<DeviceFlamestate>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看感温器(根据ID)
     * @param deviceId
     * @return
     */
    @Override
    public DeviceFlamestate detail(String deviceId) {
        if (!StrUtil.isNotBlank(deviceId)) {
            throw new BadRequestException("not exist deviceId");
        }
        DeviceFlamestate deviceFlamestate = getBaseMapper().selectById(deviceId);
        return deviceFlamestate;
    }

    /**
     * 新增感温器
     * @param deviceFlamestate
     * @return
     */
    @Override
    public int create(DeviceFlamestate deviceFlamestate) {
        if (StrUtil.isNotBlank(deviceFlamestate.getDeviceId())) {
            throw new BadRequestException("A new DeviceFlamestate cannot already have an deviceId");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        deviceFlamestate.setCreateBy(currentUser);
        deviceFlamestate.setCreateTime(currentDateTime);
        deviceFlamestate.setUpdateBy(currentUser);
        deviceFlamestate.setUpdateTime(currentDateTime);
        int result = getBaseMapper().insert(deviceFlamestate);
        return result;
    }

    /**
     * 修改感温器
     * @param deviceFlamestate
     * @return
     */
    @Override
    public int update(DeviceFlamestate deviceFlamestate) {
       if (!StrUtil.isNotBlank(deviceFlamestate.getDeviceId())) {
           throw new BadRequestException("A new DeviceFlamestate not exist deviceId");
       }
           String currentUser = WebUtils.getUserId();
       Date currentDateTime = DateUtil.date();
       deviceFlamestate.setUpdateBy(currentUser);
       deviceFlamestate.setUpdateTime(currentDateTime);
       int result = getBaseMapper().updateById(deviceFlamestate);
       return result;
    }

    /**
     * 删除感温器
     * @param deviceIds
     * @return
     */
    @Override
    public int delete(Set<String> deviceIds) {
        int result = getBaseMapper().deleteBatchIds(deviceIds);
        return result;
    }

}
