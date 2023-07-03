package com.rhy.bdmp.base.modules.assets.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesTunnel;
import com.rhy.bdmp.base.modules.assets.dao.BaseFacilitiesTunnelDao;
import com.rhy.bdmp.base.modules.assets.service.IBaseFacilitiesTunnelService;
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
 * @description  服务实现
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Service
public class BaseFacilitiesTunnelServiceImpl extends ServiceImpl<BaseFacilitiesTunnelDao, FacilitiesTunnel> implements IBaseFacilitiesTunnelService {

    /**
     * 列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<FacilitiesTunnel> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<FacilitiesTunnel> query = new Query<FacilitiesTunnel>(queryVO);
            QueryWrapper<FacilitiesTunnel> queryWrapper = query.getQueryWrapper();
                List<FacilitiesTunnel> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<FacilitiesTunnel> queryWrapper = new QueryWrapper<>();
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<FacilitiesTunnel> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<FacilitiesTunnel> query = new Query<FacilitiesTunnel>(queryVO);
            Page<FacilitiesTunnel> page = query.getPage();
            QueryWrapper<FacilitiesTunnel> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<FacilitiesTunnel>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看(根据ID)
     * @param tunnelId
     * @return
     */
    @Override
    public FacilitiesTunnel detail(String tunnelId) {
        if (!StrUtil.isNotBlank(tunnelId)) {
            throw new BadRequestException("not exist tunnelId");
        }
        FacilitiesTunnel facilitiesTunnel = getBaseMapper().selectById(tunnelId);
        return facilitiesTunnel;
    }

    /**
     * 新增
     * @param facilitiesTunnel
     * @return
     */
    @Override
    public int create(FacilitiesTunnel facilitiesTunnel) {
        if (StrUtil.isNotBlank(facilitiesTunnel.getTunnelId())) {
            throw new BadRequestException("A new FacilitiesTunnel cannot already have an tunnelId");
        }
        int result = getBaseMapper().insert(facilitiesTunnel);
        return result;
    }

    /**
     * 修改
     * @param facilitiesTunnel
     * @return
     */
    @Override
    public int update(FacilitiesTunnel facilitiesTunnel) {
       if (!StrUtil.isNotBlank(facilitiesTunnel.getTunnelId())) {
           throw new BadRequestException("A new FacilitiesTunnel not exist tunnelId");
       }
       int result = getBaseMapper().updateById(facilitiesTunnel);
       return result;
    }

    /**
     * 删除
     * @param tunnelIds
     * @return
     */
    @Override
    public int delete(Set<String> tunnelIds) {
        int result = getBaseMapper().deleteBatchIds(tunnelIds);
        return result;
    }

}
