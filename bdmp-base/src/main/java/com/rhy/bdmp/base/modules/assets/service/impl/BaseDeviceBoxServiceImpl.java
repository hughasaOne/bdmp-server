package com.rhy.bdmp.base.modules.assets.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.assets.domain.po.DeviceBox;
import com.rhy.bdmp.base.modules.assets.dao.BaseDeviceBoxDao;
import com.rhy.bdmp.base.modules.assets.service.IBaseDeviceBoxService;
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
 * @description 终端箱基本信息表 服务实现
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Service
public class BaseDeviceBoxServiceImpl extends ServiceImpl<BaseDeviceBoxDao, DeviceBox> implements IBaseDeviceBoxService {

    /**
     * 终端箱基本信息表列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<DeviceBox> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<DeviceBox> query = new Query<DeviceBox>(queryVO);
            QueryWrapper<DeviceBox> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                List<DeviceBox> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<DeviceBox> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        queryWrapper.orderByDesc("create_time");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 终端箱基本信息表列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<DeviceBox> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<DeviceBox> query = new Query<DeviceBox>(queryVO);
            Page<DeviceBox> page = query.getPage();
            QueryWrapper<DeviceBox> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<DeviceBox>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看终端箱基本信息表(根据ID)
     * @param deviceId
     * @return
     */
    @Override
    public DeviceBox detail(String deviceId) {
        if (!StrUtil.isNotBlank(deviceId)) {
            throw new BadRequestException("not exist deviceId");
        }
        DeviceBox deviceBox = getBaseMapper().selectById(deviceId);
        return deviceBox;
    }

    /**
     * 新增终端箱基本信息表
     * @param deviceBox
     * @return
     */
    @Override
    public int create(DeviceBox deviceBox) {
        if (StrUtil.isNotBlank(deviceBox.getDeviceId())) {
            throw new BadRequestException("A new DeviceBox cannot already have an deviceId");
        }
        Date currentDateTime = DateUtil.date();
        String currentUser = WebUtils.getUserId();
        deviceBox.setCreateTime(currentDateTime);
        deviceBox.setCreateBy(currentUser);
        deviceBox.setUpdateTime(currentDateTime);
        deviceBox.setUpdateBy(currentUser);
        int result = getBaseMapper().insert(deviceBox);
        return result;
    }

    /**
     * 修改终端箱基本信息表
     * @param deviceBox
     * @return
     */
    @Override
    public int update(DeviceBox deviceBox) {
       if (!StrUtil.isNotBlank(deviceBox.getDeviceId())) {
           throw new BadRequestException("A new DeviceBox not exist deviceId");
       }
       Date currentDateTime = DateUtil.date();
           String currentUser = WebUtils.getUserId();
       deviceBox.setUpdateTime(currentDateTime);
       deviceBox.setUpdateBy(currentUser);
       int result = getBaseMapper().updateById(deviceBox);
       return result;
    }

    /**
     * 删除终端箱基本信息表
     * @param deviceIds
     * @return
     */
    @Override
    public int delete(Set<String> deviceIds) {
        int result = getBaseMapper().deleteBatchIds(deviceIds);
        return result;
    }

}
