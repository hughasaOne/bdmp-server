package com.rhy.bdmp.system.modules.assets.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.Query;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.dao.BaseWaynetDao;
import com.rhy.bdmp.base.modules.assets.dao.BaseWaysectionDao;
import com.rhy.bdmp.base.modules.assets.domain.po.Waynet;
import com.rhy.bdmp.base.modules.assets.domain.po.Waysection;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.system.modules.assets.dao.WaynetDao;
import com.rhy.bdmp.system.modules.assets.service.IWaynetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @auther xiabei
 * @Description
 * @date 2021/4/14
 */
@Service
public class WaynetServiceImpl extends ServiceImpl<BaseWaynetDao, Waynet> implements IWaynetService {

    @Resource
    private WaynetDao waynetDao;
    @Resource
    private BaseWaysectionDao baseWaysectionDao;

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
     * 删除高速路网
     * @param waynetIds
     * @return
     */
    @Override
    public int delete(Set<String> waynetIds) {
        if (null != waynetIds && waynetIds.size() > 0 ){
            //判断是否存在下级
            List<Waysection> waysections = baseWaysectionDao.selectList(new QueryWrapper<Waysection>().in("waynet_id",waynetIds));
            if (null != waysections && waysections.size() > 0){
                throw new BadRequestException("路网下存在路段，无法直接删除");
            }
        }
        return waynetDao.deleteBatchIds(waynetIds);
    }

    @Override
    public List<Waynet> getWaynetList() {
        return list();
    }

    @Override
    public int create(Waynet waynet) {
        if (StrUtil.isNotBlank(waynet.getWaynetId())) {
            throw new BadRequestException("路网ID已存在，不能做新增操作");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        waynet.setCreateBy(currentUser);
        waynet.setCreateTime(currentDateTime);
        waynet.setUpdateBy(currentUser);
        waynet.setUpdateTime(currentDateTime);
        if (null == waynet.getDatastatusid()){
            waynet.setDatastatusid(1);
        }
        int result = waynetDao.insert(waynet);
        return result;
    }

}
