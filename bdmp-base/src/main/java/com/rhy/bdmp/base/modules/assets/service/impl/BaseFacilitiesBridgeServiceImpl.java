package com.rhy.bdmp.base.modules.assets.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesBridge;
import com.rhy.bdmp.base.modules.assets.dao.BaseFacilitiesBridgeDao;
import com.rhy.bdmp.base.modules.assets.service.IBaseFacilitiesBridgeService;
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
 * @description 桥梁 服务实现
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Service
public class BaseFacilitiesBridgeServiceImpl extends ServiceImpl<BaseFacilitiesBridgeDao, FacilitiesBridge> implements IBaseFacilitiesBridgeService {

    /**
     * 桥梁列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<FacilitiesBridge> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<FacilitiesBridge> query = new Query<FacilitiesBridge>(queryVO);
            QueryWrapper<FacilitiesBridge> queryWrapper = query.getQueryWrapper();
                List<FacilitiesBridge> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<FacilitiesBridge> queryWrapper = new QueryWrapper<>();
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 桥梁列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<FacilitiesBridge> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<FacilitiesBridge> query = new Query<FacilitiesBridge>(queryVO);
            Page<FacilitiesBridge> page = query.getPage();
            QueryWrapper<FacilitiesBridge> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<FacilitiesBridge>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看桥梁(根据ID)
     * @param bridgeId
     * @return
     */
    @Override
    public FacilitiesBridge detail(String bridgeId) {
        if (!StrUtil.isNotBlank(bridgeId)) {
            throw new BadRequestException("not exist bridgeId");
        }
        FacilitiesBridge facilitiesBridge = getBaseMapper().selectById(bridgeId);
        return facilitiesBridge;
    }

    /**
     * 新增桥梁
     * @param facilitiesBridge
     * @return
     */
    @Override
    public int create(FacilitiesBridge facilitiesBridge) {
        if (StrUtil.isNotBlank(facilitiesBridge.getBridgeId())) {
            throw new BadRequestException("A new FacilitiesBridge cannot already have an bridgeId");
        }
        int result = getBaseMapper().insert(facilitiesBridge);
        return result;
    }

    /**
     * 修改桥梁
     * @param facilitiesBridge
     * @return
     */
    @Override
    public int update(FacilitiesBridge facilitiesBridge) {
       if (!StrUtil.isNotBlank(facilitiesBridge.getBridgeId())) {
           throw new BadRequestException("A new FacilitiesBridge not exist bridgeId");
       }
       int result = getBaseMapper().updateById(facilitiesBridge);
       return result;
    }

    /**
     * 删除桥梁
     * @param bridgeIds
     * @return
     */
    @Override
    public int delete(Set<String> bridgeIds) {
        int result = getBaseMapper().deleteBatchIds(bridgeIds);
        return result;
    }

}
