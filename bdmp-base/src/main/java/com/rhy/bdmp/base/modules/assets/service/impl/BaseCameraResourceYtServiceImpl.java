package com.rhy.bdmp.base.modules.assets.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.assets.domain.po.CameraResourceYt;
import com.rhy.bdmp.base.modules.assets.dao.BaseCameraResourceYtDao;
import com.rhy.bdmp.base.modules.assets.service.IBaseCameraResourceYtService;
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
 * @description 视频资源(云台) 服务实现
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Service
public class BaseCameraResourceYtServiceImpl extends ServiceImpl<BaseCameraResourceYtDao, CameraResourceYt> implements IBaseCameraResourceYtService {

    /**
     * 视频资源(云台)列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<CameraResourceYt> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<CameraResourceYt> query = new Query<CameraResourceYt>(queryVO);
            QueryWrapper<CameraResourceYt> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                List<CameraResourceYt> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<CameraResourceYt> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        queryWrapper.orderByDesc("create_time");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 视频资源(云台)列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<CameraResourceYt> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<CameraResourceYt> query = new Query<CameraResourceYt>(queryVO);
            Page<CameraResourceYt> page = query.getPage();
            QueryWrapper<CameraResourceYt> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<CameraResourceYt>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看视频资源(云台)(根据ID)
     * @param id
     * @return
     */
    @Override
    public CameraResourceYt detail(String id) {
        if (!StrUtil.isNotBlank(id)) {
            throw new BadRequestException("not exist id");
        }
        CameraResourceYt cameraResourceYt = getBaseMapper().selectById(id);
        return cameraResourceYt;
    }

    /**
     * 新增视频资源(云台)
     * @param cameraResourceYt
     * @return
     */
    @Override
    public int create(CameraResourceYt cameraResourceYt) {
        if (StrUtil.isNotBlank(cameraResourceYt.getId())) {
            throw new BadRequestException("A new CameraResourceYt cannot already have an id");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        cameraResourceYt.setCreateBy(currentUser);
        cameraResourceYt.setCreateTime(currentDateTime);
        cameraResourceYt.setUpdateBy(currentUser);
        cameraResourceYt.setUpdateTime(currentDateTime);
        int result = getBaseMapper().insert(cameraResourceYt);
        return result;
    }

    /**
     * 修改视频资源(云台)
     * @param cameraResourceYt
     * @return
     */
    @Override
    public int update(CameraResourceYt cameraResourceYt) {
       if (!StrUtil.isNotBlank(cameraResourceYt.getId())) {
           throw new BadRequestException("A new CameraResourceYt not exist id");
       }
           String currentUser = WebUtils.getUserId();
       Date currentDateTime = DateUtil.date();
       cameraResourceYt.setUpdateBy(currentUser);
       cameraResourceYt.setUpdateTime(currentDateTime);
       int result = getBaseMapper().updateById(cameraResourceYt);
       return result;
    }

    /**
     * 删除视频资源(云台)
     * @param ids
     * @return
     */
    @Override
    public int delete(Set<String> ids) {
        int result = getBaseMapper().deleteBatchIds(ids);
        return result;
    }

}
