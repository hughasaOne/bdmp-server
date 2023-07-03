package com.rhy.bdmp.base.modules.sys.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.sys.domain.po.App;
import com.rhy.bdmp.base.modules.sys.dao.BaseAppDao;
import com.rhy.bdmp.base.modules.sys.service.IBaseAppService;
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
 * @description 应用信息 服务实现
 * @author yanggj
 * @date 2021-09-24 16:32
 * @version V1.0
 **/
@Service
public class BaseAppServiceImpl extends ServiceImpl<BaseAppDao, App> implements IBaseAppService {

    /**
     * 应用信息列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<App> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<App> query = new Query<App>(queryVO);
            QueryWrapper<App> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                List<App> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<App> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        queryWrapper.orderByDesc("create_time");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 应用信息列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<App> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<App> query = new Query<App>(queryVO);
            Page<App> page = query.getPage();
            QueryWrapper<App> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<App>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看应用信息(根据ID)
     * @param appId
     * @return
     */
    @Override
    public App detail(String appId) {
        if (!StrUtil.isNotBlank(appId)) {
            throw new BadRequestException("not exist appId");
        }
        App app = getBaseMapper().selectById(appId);
        return app;
    }

    /**
     * 新增应用信息
     * @param app
     * @return
     */
    @Override
    public int create(App app) {
        if (StrUtil.isNotBlank(app.getAppId())) {
            throw new BadRequestException("A new App cannot already have an appId");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        app.setCreateBy(currentUser);
        app.setCreateTime(currentDateTime);
        app.setUpdateBy(currentUser);
        app.setUpdateTime(currentDateTime);
        int result = getBaseMapper().insert(app);
        return result;
    }

    /**
     * 修改应用信息
     * @param app
     * @return
     */
    @Override
    public int update(App app) {
       if (!StrUtil.isNotBlank(app.getAppId())) {
           throw new BadRequestException("A new App not exist appId");
       }
           String currentUser = WebUtils.getUserId();
       Date currentDateTime = DateUtil.date();
       app.setUpdateBy(currentUser);
       app.setUpdateTime(currentDateTime);
       int result = getBaseMapper().updateById(app);
       return result;
    }

    /**
     * 删除应用信息
     * @param appIds
     * @return
     */
    @Override
    public int delete(Set<String> appIds) {
        int result = getBaseMapper().deleteBatchIds(appIds);
        return result;
    }

}
