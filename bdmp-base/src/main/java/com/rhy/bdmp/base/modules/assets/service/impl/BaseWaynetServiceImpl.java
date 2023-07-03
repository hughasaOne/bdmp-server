package com.rhy.bdmp.base.modules.assets.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.assets.domain.po.Waynet;
import com.rhy.bdmp.base.modules.assets.dao.BaseWaynetDao;
import com.rhy.bdmp.base.modules.assets.service.IBaseWaynetService;
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
 * @description 高速路网 服务实现
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Service
public class BaseWaynetServiceImpl extends ServiceImpl<BaseWaynetDao, Waynet> implements IBaseWaynetService {

    /**
     * 高速路网列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<Waynet> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<Waynet> query = new Query<Waynet>(queryVO);
            QueryWrapper<Waynet> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                List<Waynet> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<Waynet> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        queryWrapper.orderByDesc("create_time");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 高速路网列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<Waynet> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<Waynet> query = new Query<Waynet>(queryVO);
            Page<Waynet> page = query.getPage();
            QueryWrapper<Waynet> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<Waynet>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看高速路网(根据ID)
     * @param waynetId
     * @return
     */
    @Override
    public Waynet detail(String waynetId) {
        if (!StrUtil.isNotBlank(waynetId)) {
            throw new BadRequestException("not exist waynetId");
        }
        Waynet waynet = getBaseMapper().selectById(waynetId);
        return waynet;
    }

    /**
     * 新增高速路网
     * @param waynet
     * @return
     */
    @Override
    public int create(Waynet waynet) {
        if (StrUtil.isNotBlank(waynet.getWaynetId())) {
            throw new BadRequestException("A new Waynet cannot already have an waynetId");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        waynet.setCreateBy(currentUser);
        waynet.setCreateTime(currentDateTime);
        waynet.setUpdateBy(currentUser);
        waynet.setUpdateTime(currentDateTime);
        int result = getBaseMapper().insert(waynet);
        return result;
    }

    /**
     * 修改高速路网
     * @param waynet
     * @return
     */
    @Override
    public int update(Waynet waynet) {
       if (!StrUtil.isNotBlank(waynet.getWaynetId())) {
           throw new BadRequestException("A new Waynet not exist waynetId");
       }
           String currentUser = WebUtils.getUserId();
       Date currentDateTime = DateUtil.date();
       waynet.setUpdateBy(currentUser);
       waynet.setUpdateTime(currentDateTime);
       int result = getBaseMapper().updateById(waynet);
       return result;
    }

    /**
     * 删除高速路网
     * @param waynetIds
     * @return
     */
    @Override
    public int delete(Set<String> waynetIds) {
        int result = getBaseMapper().deleteBatchIds(waynetIds);
        return result;
    }

}
