package com.rhy.bdmp.base.modules.assets.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.assets.domain.po.BoxExternal;
import com.rhy.bdmp.base.modules.assets.dao.BaseBoxExternalDao;
import com.rhy.bdmp.base.modules.assets.service.IBaseBoxExternalService;
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
 * @description 终端箱外接设备信息表 服务实现
 * @author yanggj
 * @date 2021-09-24 15:36
 * @version V1.0
 **/
@Service
public class BaseBoxExternalServiceImpl extends ServiceImpl<BaseBoxExternalDao, BoxExternal> implements IBaseBoxExternalService {

    /**
     * 终端箱外接设备信息表列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<BoxExternal> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<BoxExternal> query = new Query<BoxExternal>(queryVO);
            QueryWrapper<BoxExternal> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                List<BoxExternal> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<BoxExternal> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        queryWrapper.orderByDesc("create_time");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 终端箱外接设备信息表列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<BoxExternal> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<BoxExternal> query = new Query<BoxExternal>(queryVO);
            Page<BoxExternal> page = query.getPage();
            QueryWrapper<BoxExternal> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<BoxExternal>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看终端箱外接设备信息表(根据ID)
     * @param externalId
     * @return
     */
    @Override
    public BoxExternal detail(String externalId) {
        if (!StrUtil.isNotBlank(externalId)) {
            throw new BadRequestException("not exist externalId");
        }
        BoxExternal boxExternal = getBaseMapper().selectById(externalId);
        return boxExternal;
    }

    /**
     * 新增终端箱外接设备信息表
     * @param boxExternal
     * @return
     */
    @Override
    public int create(BoxExternal boxExternal) {
        if (StrUtil.isNotBlank(boxExternal.getExternalId())) {
            throw new BadRequestException("A new BoxExternal cannot already have an externalId");
        }
        Date currentDateTime = DateUtil.date();
        String currentUser = WebUtils.getUserId();
        boxExternal.setCreateTime(currentDateTime);
        boxExternal.setCreateBy(currentUser);
        boxExternal.setUpdateTime(currentDateTime);
        boxExternal.setUpdateBy(currentUser);
        int result = getBaseMapper().insert(boxExternal);
        return result;
    }

    /**
     * 修改终端箱外接设备信息表
     * @param boxExternal
     * @return
     */
    @Override
    public int update(BoxExternal boxExternal) {
       if (!StrUtil.isNotBlank(boxExternal.getExternalId())) {
           throw new BadRequestException("A new BoxExternal not exist externalId");
       }
       Date currentDateTime = DateUtil.date();
           String currentUser = WebUtils.getUserId();
       boxExternal.setUpdateTime(currentDateTime);
       boxExternal.setUpdateBy(currentUser);
       int result = getBaseMapper().updateById(boxExternal);
       return result;
    }

    /**
     * 删除终端箱外接设备信息表
     * @param externalIds
     * @return
     */
    @Override
    public int delete(Set<String> externalIds) {
        int result = getBaseMapper().deleteBatchIds(externalIds);
        return result;
    }

}
