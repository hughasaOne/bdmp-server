package com.rhy.bdmp.base.modules.assets.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.assets.domain.po.CameraResourceTl;
import com.rhy.bdmp.base.modules.assets.dao.BaseCameraResourceTlDao;
import com.rhy.bdmp.base.modules.assets.service.IBaseCameraResourceTlService;
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
 * @description 视频资源(腾路) 服务实现
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Service
public class BaseCameraResourceTlServiceImpl extends ServiceImpl<BaseCameraResourceTlDao, CameraResourceTl> implements IBaseCameraResourceTlService {

    /**
     * 视频资源(腾路)列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<CameraResourceTl> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<CameraResourceTl> query = new Query<CameraResourceTl>(queryVO);
            QueryWrapper<CameraResourceTl> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                List<CameraResourceTl> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<CameraResourceTl> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        queryWrapper.orderByDesc("create_time");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 视频资源(腾路)列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<CameraResourceTl> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<CameraResourceTl> query = new Query<CameraResourceTl>(queryVO);
            Page<CameraResourceTl> page = query.getPage();
            QueryWrapper<CameraResourceTl> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<CameraResourceTl>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看视频资源(腾路)(根据ID)
     * @param cameraId
     * @return
     */
    @Override
    public CameraResourceTl detail(String cameraId) {
        if (!StrUtil.isNotBlank(cameraId)) {
            throw new BadRequestException("not exist cameraId");
        }
        CameraResourceTl cameraResourceTl = getBaseMapper().selectById(cameraId);
        return cameraResourceTl;
    }

    /**
     * 新增视频资源(腾路)
     * @param cameraResourceTl
     * @return
     */
    @Override
    public int create(CameraResourceTl cameraResourceTl) {
        if (StrUtil.isNotBlank(cameraResourceTl.getCameraId())) {
            throw new BadRequestException("A new CameraResourceTl cannot already have an cameraId");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        cameraResourceTl.setCreateBy(currentUser);
        cameraResourceTl.setCreateTime(currentDateTime);
        cameraResourceTl.setUpdateBy(currentUser);
        cameraResourceTl.setUpdateTime(currentDateTime);
        int result = getBaseMapper().insert(cameraResourceTl);
        return result;
    }

    /**
     * 修改视频资源(腾路)
     * @param cameraResourceTl
     * @return
     */
    @Override
    public int update(CameraResourceTl cameraResourceTl) {
       if (!StrUtil.isNotBlank(cameraResourceTl.getCameraId())) {
           throw new BadRequestException("A new CameraResourceTl not exist cameraId");
       }
           String currentUser = WebUtils.getUserId();
       Date currentDateTime = DateUtil.date();
       cameraResourceTl.setUpdateBy(currentUser);
       cameraResourceTl.setUpdateTime(currentDateTime);
       int result = getBaseMapper().updateById(cameraResourceTl);
       return result;
    }

    /**
     * 删除视频资源(腾路)
     * @param cameraIds
     * @return
     */
    @Override
    public int delete(Set<String> cameraIds) {
        int result = getBaseMapper().deleteBatchIds(cameraIds);
        return result;
    }

}
