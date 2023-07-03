package com.rhy.bdmp.base.modules.assets.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.Query;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.base.modules.assets.dao.BaseDictDeviceDao;
import com.rhy.bdmp.base.modules.assets.domain.po.DictDevice;
import com.rhy.bdmp.base.modules.assets.service.IBaseDictDeviceService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @description 设备字典 服务实现
 * @author yanggj
 * @date 2021-10-26 09:05
 * @version V1.0
 **/
@Service
public class BaseDictDeviceServiceImpl extends ServiceImpl<BaseDictDeviceDao, DictDevice> implements IBaseDictDeviceService {

    /**
     * 设备字典列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<DictDevice> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<DictDevice> query = new Query<DictDevice>(queryVO);
            QueryWrapper<DictDevice> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                List<DictDevice> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<DictDevice> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        queryWrapper.orderByDesc("create_time");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 设备字典列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<DictDevice> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<DictDevice> query = new Query<DictDevice>(queryVO);
            Page<DictDevice> page = query.getPage();
            QueryWrapper<DictDevice> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<DictDevice>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看设备字典(根据ID)
     * @param deviceDictId
     * @return
     */
    @Override
    public DictDevice detail(String deviceDictId) {
        if (!StrUtil.isNotBlank(deviceDictId)) {
            throw new BadRequestException("not exist deviceDictId");
        }
        DictDevice deviceDict = getBaseMapper().selectById(deviceDictId);
        return deviceDict;
    }

    /**
     * 新增设备字典
     * @param deviceDict
     * @return
     */
    @Override
    public int create(DictDevice deviceDict) {
        if (StrUtil.isNotBlank(deviceDict.getDeviceDictId())) {
            throw new BadRequestException("A new DeviceDict cannot already have an deviceDictId");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        deviceDict.setCreateBy(currentUser);
        deviceDict.setCreateTime(currentDateTime);
        deviceDict.setUpdateBy(currentUser);
        deviceDict.setUpdateTime(currentDateTime);
        int result = getBaseMapper().insert(deviceDict);
        return result;
    }

    /**
     * 修改设备字典
     * @param deviceDict
     * @return
     */
    @Override
    public int update(DictDevice deviceDict) {
       if (!StrUtil.isNotBlank(deviceDict.getDeviceDictId())) {
           throw new BadRequestException("A new DeviceDict not exist deviceDictId");
       }
           String currentUser = WebUtils.getUserId();
       Date currentDateTime = DateUtil.date();
       deviceDict.setUpdateBy(currentUser);
       deviceDict.setUpdateTime(currentDateTime);
       int result = getBaseMapper().updateById(deviceDict);
       return result;
    }

    /**
     * 删除设备字典
     * @param deviceDictIds
     * @return
     */
    @Override
    public int delete(Set<String> deviceDictIds) {
        int result = getBaseMapper().deleteBatchIds(deviceDictIds);
        return result;
    }

}
