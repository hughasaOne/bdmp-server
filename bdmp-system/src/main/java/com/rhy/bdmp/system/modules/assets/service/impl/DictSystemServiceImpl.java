package com.rhy.bdmp.system.modules.assets.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.PageUtils;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.base.modules.assets.dao.BaseDictSystemDao;
import com.rhy.bdmp.base.modules.assets.domain.po.DictSystem;
import com.rhy.bdmp.system.modules.assets.dao.DictSystemDao;
import com.rhy.bdmp.system.modules.assets.domain.bo.DictBO;
import com.rhy.bdmp.system.modules.assets.service.IDictSystemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created on 2021/11/1.
 *
 * @author duke
 */
@Service
public class DictSystemServiceImpl implements IDictSystemService {
    @Resource
    private BaseDictSystemDao baseDictSystemDao;

    @Resource
    private DictSystemDao dictSystemDao;

    @Override
    public boolean create(DictSystem dictSystem) {
        dictSystem.setCreateTime(new Date());
        dictSystem.setCreateBy(WebUtils.getUserId());
        return baseDictSystemDao.insert(dictSystem)>0;
    }

    @Override
    public boolean update(DictSystem dictSystem) {
        if (StrUtil.isBlank(dictSystem.getSystemId())) {
            throw new BadRequestException("ID不存在");
        }
        DictSystem byId = baseDictSystemDao.selectById(dictSystem.getSystemId());
        if (byId != null) {
            dictSystem.setUpdateBy(WebUtils.getUserId());
            dictSystem.setUpdateTime(new Date());
            return baseDictSystemDao.updateById(dictSystem)>0;
        } else {
            throw new BadRequestException("该系统字典不存在,无法修改!");
        }
    }

    @Override
    public int delete(Set<String> systemIds) {
        if(CollectionUtil.isNotEmpty(systemIds)){
            return baseDictSystemDao.deleteBatchIds(systemIds);
        }
        return 0;
    }

    @Override
    public DictSystem detail(String systemId) {
        if(StrUtil.isBlank(systemId)){
            throw new BadRequestException("ID不存在");
        }
        return baseDictSystemDao.selectById(systemId);
    }

    @Override
    public Page<DictSystem> queryPage(Integer currentPage, Integer size, String param) {
        // 设置默认值
        if (null == currentPage || currentPage <= 0) {
            currentPage = 1;
        }
        if (null == size || size <= 0) {
            size = 20;
        }

        Page<DictSystem> page = new Page<>(currentPage, size);
        page.setOptimizeCountSql(false);
        LambdaQueryWrapper<DictSystem> wrapper = Wrappers.lambdaQuery();

        if (StrUtil.isNotBlank(param)) {
            wrapper.eq(DictSystem::getSystemId, param)
                    .or().eq(DictSystem::getSystemNo, param)
                    .or().eq(DictSystem::getSystemSerial, param)
                    .or().like(DictSystem::getSystemName, param)
                    .or().eq(DictSystem::getSystemType, param);
        }
        return baseDictSystemDao.selectPage(page, wrapper);
    }

    @Override
    public List<DictSystem> list(String param) {
        LambdaQueryWrapper<DictSystem> queryWrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(param)) {
            queryWrapper.eq(DictSystem::getSystemId, param)
                    .or().eq(DictSystem::getSystemNo, param)
                    .or().eq(DictSystem::getSystemSerial, param)
                    .or().like(DictSystem::getSystemName, param)
                    .or().eq(DictSystem::getSystemType, param);
        }
        return baseDictSystemDao.selectList(queryWrapper);
    }

    /**
     * 获取系统字典
     * @param dictBO 业务实体
     */
    @Override
    public Object getDictSystem(DictBO dictBO) {
        Integer currentPage = dictBO.getCurrentPage();
        Integer limit = dictBO.getLimit();
        if (currentPage == null){
            // 不分页
            return dictSystemDao.getDictSystem(dictBO);
        }
        // 分页
        Page<Map<String,Object>> page = new Page<>(currentPage, limit);
        page.setOptimizeCountSql(true);
        return new PageUtils(dictSystemDao.getDictSystem(page, dictBO));
    }
}
