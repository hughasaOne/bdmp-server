package com.rhy.bdmp.base.modules.assets.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesTollStation;
import com.rhy.bdmp.base.modules.assets.dao.BaseFacilitiesTollStationDao;
import com.rhy.bdmp.base.modules.assets.service.IBaseFacilitiesTollStationService;
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
public class BaseFacilitiesTollStationServiceImpl extends ServiceImpl<BaseFacilitiesTollStationDao, FacilitiesTollStation> implements IBaseFacilitiesTollStationService {

    /**
     * 列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<FacilitiesTollStation> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<FacilitiesTollStation> query = new Query<FacilitiesTollStation>(queryVO);
            QueryWrapper<FacilitiesTollStation> queryWrapper = query.getQueryWrapper();
                List<FacilitiesTollStation> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<FacilitiesTollStation> queryWrapper = new QueryWrapper<>();
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<FacilitiesTollStation> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<FacilitiesTollStation> query = new Query<FacilitiesTollStation>(queryVO);
            Page<FacilitiesTollStation> page = query.getPage();
            QueryWrapper<FacilitiesTollStation> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<FacilitiesTollStation>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看(根据ID)
     * @param tollStationId
     * @return
     */
    @Override
    public FacilitiesTollStation detail(String tollStationId) {
        if (!StrUtil.isNotBlank(tollStationId)) {
            throw new BadRequestException("not exist tollStationId");
        }
        FacilitiesTollStation facilitiesTollStation = getBaseMapper().selectById(tollStationId);
        return facilitiesTollStation;
    }

    /**
     * 新增
     * @param facilitiesTollStation
     * @return
     */
    @Override
    public int create(FacilitiesTollStation facilitiesTollStation) {
        if (StrUtil.isNotBlank(facilitiesTollStation.getTollStationId())) {
            throw new BadRequestException("A new FacilitiesTollStation cannot already have an tollStationId");
        }
        int result = getBaseMapper().insert(facilitiesTollStation);
        return result;
    }

    /**
     * 修改
     * @param facilitiesTollStation
     * @return
     */
    @Override
    public int update(FacilitiesTollStation facilitiesTollStation) {
       if (!StrUtil.isNotBlank(facilitiesTollStation.getTollStationId())) {
           throw new BadRequestException("A new FacilitiesTollStation not exist tollStationId");
       }
       int result = getBaseMapper().updateById(facilitiesTollStation);
       return result;
    }

    /**
     * 删除
     * @param tollStationIds
     * @return
     */
    @Override
    public int delete(Set<String> tollStationIds) {
        int result = getBaseMapper().deleteBatchIds(tollStationIds);
        return result;
    }

}
