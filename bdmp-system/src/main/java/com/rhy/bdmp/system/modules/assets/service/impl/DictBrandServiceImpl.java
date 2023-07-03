package com.rhy.bdmp.system.modules.assets.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.PageUtils;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.base.modules.assets.dao.BaseDictBrandDao;
import com.rhy.bdmp.base.modules.assets.domain.po.DictBrand;
import com.rhy.bdmp.system.modules.assets.dao.DictBrandDao;
import com.rhy.bdmp.system.modules.assets.domain.bo.DictBO;
import com.rhy.bdmp.system.modules.assets.service.IDictBrandService;
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
public class DictBrandServiceImpl implements IDictBrandService {
    @Resource
    private BaseDictBrandDao baseDictBrandDao;

    @Resource
    private DictBrandDao dictBrandDao;

    @Override
    public Object getDictBrand(DictBO dictBO) {
        Integer currentPage = dictBO.getCurrentPage();
        Integer limit = dictBO.getLimit();
        if (currentPage == null){
            // 不分页
            return dictBrandDao.getDictBrand(dictBO);
        }
        // 分页
        Page<Map<String,Object>> page = new Page<>(currentPage, limit);
        page.setOptimizeCountSql(true);
        return new PageUtils(dictBrandDao.getDictBrand(page, dictBO));
    }

    @Override
    public boolean create(DictBrand dictBrand) {
        dictBrand.setCreateTime(new Date());
        dictBrand.setCreateBy(WebUtils.getUserId());
        return baseDictBrandDao.insert(dictBrand)>0;
    }

    @Override
    public boolean update(DictBrand dictBrand) {
        if (StrUtil.isBlank(dictBrand.getBrandId())) {
            throw new BadRequestException("ID不存在");
        }
        DictBrand byId = baseDictBrandDao.selectById(dictBrand.getBrandId());
        if (byId != null) {
            dictBrand.setUpdateBy(WebUtils.getUserId());
            dictBrand.setUpdateTime(new Date());
            return baseDictBrandDao.updateById(dictBrand)>0;
        } else {
            throw new BadRequestException("该品牌字典不存在,无法修改!");
        }
    }

    @Override
    public int delete(Set<String> brandIds) {
        if(CollectionUtil.isNotEmpty(brandIds)){
            return baseDictBrandDao.deleteBatchIds(brandIds);
        }
        return 0;
    }

    @Override
    public Map<String,Object> detail(String brandId) {
        if(StrUtil.isBlank(brandId)){
            throw new BadRequestException("ID不存在");
        }
        return dictBrandDao.selectById(brandId);
    }

    @Override
    public Page<DictBrand> queryPage(Integer currentPage, Integer size, String param) {

        // 设置默认值
        if (null == currentPage || currentPage <= 0) {
            currentPage = 1;
        }
        if (null == size || size <= 0) {
            size = 20;
        }

        Page<DictBrand> page = new Page<>(currentPage, size);
        page.setOptimizeCountSql(false);
        LambdaQueryWrapper<DictBrand> wrapper = Wrappers.lambdaQuery();

        if (StrUtil.isNotBlank(param)) {
            wrapper.eq(DictBrand::getBrandId, param)
                    .or().like(DictBrand::getBrandName, param)
                    .or().eq(DictBrand::getBrandNo, param);
        }
        return baseDictBrandDao.selectPage(page, wrapper);
    }

    @Override
    public List<DictBrand> list(String param) {
        LambdaQueryWrapper<DictBrand> wrapper = Wrappers.lambdaQuery();
        if (StrUtil.isNotBlank(param)) {
            wrapper.eq(DictBrand::getBrandId, param)
                    .or().like(DictBrand::getBrandName, param)
                    .or().eq(DictBrand::getBrandNo, param);
        }
        return baseDictBrandDao.selectList(wrapper);
    }

    @Override
    public List<Map<String,Object>> getDictListByParentId(String parentId) {
        return dictBrandDao.selectMaps(parentId);
    }
}
