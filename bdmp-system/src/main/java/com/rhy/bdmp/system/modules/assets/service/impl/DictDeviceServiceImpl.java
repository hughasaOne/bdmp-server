package com.rhy.bdmp.system.modules.assets.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.PageUtils;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.base.modules.assets.dao.BaseDictDeviceDao;
import com.rhy.bdmp.base.modules.assets.domain.po.DictDevice;
import com.rhy.bdmp.system.modules.assets.dao.DictDeviceDao;
import com.rhy.bdmp.system.modules.assets.domain.bo.DictBO;
import com.rhy.bdmp.system.modules.assets.service.IDictDeviceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: yanggj
 * @Description: 设备字典管理
 * @Date: 2021/10/26 10:21
 * @Version: 1.0.0
 */
@Service
public class DictDeviceServiceImpl implements IDictDeviceService {

    @Resource
    private BaseDictDeviceDao baseDeviceDictDao;
    @Resource
    private DictDeviceDao dictDeviceDao;

    @Override
    public Object getDictDevice(DictBO dictBO) {
        Integer currentPage = dictBO.getCurrentPage();
        Integer limit = dictBO.getLimit();
        if (currentPage == null){
            // 不分页
            return dictDeviceDao.getDictDevice(dictBO);
        }
        // 分页
        Page<Map<String,Object>> page = new Page<>(currentPage, limit);
        page.setOptimizeCountSql(true);
        return new PageUtils(dictDeviceDao.getDictDevice(page, dictBO));
    }

    @Override
    public List<DictDevice> list(String param) {
        LambdaQueryWrapper<DictDevice> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(param)) {
            wrapper.eq(DictDevice::getDeviceDictId, param)
                    .or().like(DictDevice::getName, param)
                    .or().eq(DictDevice::getCode, param)
                    .or().like(DictDevice::getAliasName, param);
        }
        return baseDeviceDictDao.selectList(wrapper);
    }

    @Override
    public List<Map<String, Object>> getChildren(String parentId) {
        return dictDeviceDao.getChildren(parentId);
    }

    @Override
    public Page<DictDevice> queryPage(Integer currentPage, Integer size, String param) {
        // 设置默认值
        if (null == currentPage || currentPage <= 0) {
            currentPage = 1;
        }
        if (null == size || size <= 0) {
            size = 20;
        }
        Page<DictDevice> page = new Page<>(currentPage, size);
        page.setOptimizeCountSql(false);
        LambdaQueryWrapper<DictDevice> wrapper = Wrappers.lambdaQuery();

        if (StrUtil.isNotBlank(param)) {
            wrapper.like(DictDevice::getName, param)
                    .or().eq(DictDevice::getCode, param)
                    .or().like(DictDevice::getAliasName, param)
                    .or().eq(DictDevice::getPDeviceDictId,param);
        }
        return baseDeviceDictDao.selectPage(page, wrapper);
    }

    @Override
    public Map<String,Object> detail(String deviceDictId) {
        return dictDeviceDao.selectById(deviceDictId);
    }

    @Override
    public boolean create(DictDevice deviceDict) {
        String deviceDictId = dictDeviceDao.getDictDeviceId(deviceDict.getDeviceTypeId());
        // 此类型能到达的最大id
        String maxId = dictDeviceDao.getMaxDeviceDictId(deviceDict.getDeviceTypeId());
        if (StrUtil.isBlank(deviceDictId)){
            throw new BadRequestException("当前设备类型："+deviceDict.getDeviceTypeId()+"不存在！");
        }
        long deviceDictIdL = Long.parseLong(deviceDictId);
        long maxIdL = Long.parseLong(maxId);
        if (deviceDictIdL > maxIdL){
            throw new BadRequestException("当前类型的设备字典id已使用完，请联系管理员！");
        }
        deviceDict.setDeviceDictId(deviceDictId);
        deviceDict.setCreateBy(WebUtils.getUserId());
        deviceDict.setCreateTime(new Date());
        int result = baseDeviceDictDao.insert(deviceDict);
        return result >= 1;
    }

    @Override
    public boolean update(DictDevice deviceDict) {
        if (StrUtil.isBlank(deviceDict.getDeviceDictId())) {
            throw new BadRequestException("ID不存在");
        }
        DictDevice byId = baseDeviceDictDao.selectById(deviceDict.getDeviceDictId());
        if (byId != null) {
            deviceDict.setUpdateBy(WebUtils.getUserId());
            deviceDict.setUpdateTime(new Date());
            int i = baseDeviceDictDao.updateById(deviceDict);
            return i >= 1;
        } else {
            throw new BadRequestException("该设备字典不存在,无法修改!");
        }
    }

    @Override
    public int delete(Set<String> deviceDictIds) {
        return baseDeviceDictDao.deleteBatchIds(deviceDictIds);
    }
}
