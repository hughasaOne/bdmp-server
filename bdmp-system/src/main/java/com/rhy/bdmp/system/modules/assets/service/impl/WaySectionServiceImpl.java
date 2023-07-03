package com.rhy.bdmp.system.modules.assets.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.Query;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.base.modules.assets.dao.BaseFacilitiesDao;
import com.rhy.bdmp.base.modules.assets.dao.BaseWaysectionDao;
import com.rhy.bdmp.base.modules.assets.domain.po.Facilities;
import com.rhy.bdmp.base.modules.assets.domain.po.Route;
import com.rhy.bdmp.base.modules.assets.domain.po.Waysection;
import com.rhy.bdmp.system.modules.assets.dao.WaySectionDao;
import com.rhy.bdmp.system.modules.assets.domain.po.User;
import com.rhy.bdmp.system.modules.assets.domain.vo.WaysectionRouteVo;
import com.rhy.bdmp.system.modules.assets.domain.vo.WaysectionVo;
import com.rhy.bdmp.system.modules.assets.enums.PermissionsEnum;
import com.rhy.bdmp.system.modules.assets.service.IAssetsPermissionsTreeService;
import com.rhy.bdmp.system.modules.assets.service.IRouteService;
import com.rhy.bdmp.system.modules.assets.service.IWaySectionService;
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
public class WaySectionServiceImpl extends ServiceImpl<BaseWaysectionDao, Waysection> implements IWaySectionService {

    @Resource
    private BaseWaysectionDao baseWaysectionDao;
    @Resource
    private BaseFacilitiesDao baseFacilitiesDao;
    @Resource
    private WaySectionDao waySectionDao;
    @Resource
    private IAssetsPermissionsTreeService assetsPermissionsTreeService;
    @Resource
    private IRouteService routeService;
    /**
     * 查询路段（分页）
     *
     * @param currentPage          当前页
     * @param size                 页大小
     * @param isUseUserPermissions 是否使用用户权限
     * @param orgId                组织ID
     * @param waySectionName       路段名称
     * @return
     */
    @Override
    public Page<WaysectionVo> queryPage(Integer currentPage, Integer size, Boolean isUseUserPermissions,
                                        String orgId, String waySectionName,String waynetId) {
        if (null == currentPage || 0 >= currentPage) {
            currentPage = 1;
        }
        if (null == size || 0 >= size) {
            size = 20;
        }
        String userId = null;
        Integer dataPermissionsLevel = null;
        if (isUseUserPermissions) {
            // 带用户权限
            User user = assetsPermissionsTreeService.getCurrentUser();
            dataPermissionsLevel = user.getDataPermissionsLevel();
            userId = user.getUserId();
            // 用户权限需要是org或者way才可以维护路段
            if (null != user.getIsAdmin() && 1 == user.getIsAdmin()) {
                isUseUserPermissions = false;
            }
            else {
                if (null == dataPermissionsLevel || (dataPermissionsLevel != Integer.parseInt(PermissionsEnum.ORG.getCode()) && dataPermissionsLevel != Integer.parseInt(PermissionsEnum.WAY.getCode()))) {
                    throw new BadRequestException("当前用户等级为:" + dataPermissionsLevel + ",数据权限不足,不能维护设备");
                }
            }
        }
        Page<WaysectionVo> page = new Page<>(currentPage, size);
        page.setOptimizeCountSql(false);
        return waySectionDao.queryPage(page, isUseUserPermissions, userId, orgId,
                waySectionName,dataPermissionsLevel,waynetId);
    }

    /**
     * 运营路段列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<Waysection> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<Waysection> query = new Query<Waysection>(queryVO);
            Page<Waysection> page = query.getPage();
            QueryWrapper<Waysection> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<Waysection>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }


    /**
     * 新增路段
     *
     * @param waysection
     * @return
     */
    @Override
    @Transactional
    public int create(WaysectionRouteVo waysection) {
        if (StrUtil.isNotBlank(waysection.getWaysectionId())) {
            throw new BadRequestException("路段ID已存在，不能做新增操作");
        }
        if (!StrUtil.isNotBlank(waysection.getWaysectionName())) {
            throw new BadRequestException("路段名称不能为空");
        }
        if (!StrUtil.isNotBlank(waysection.getWaysectionSName())) {
            throw new BadRequestException("路段简称不能为空");
        }
        if (!StrUtil.isNotBlank(waysection.getWaysectionCode())) {
            throw new BadRequestException("路段代码不能为空");
        }
        if (!StrUtil.isNotBlank(waysection.getAreaNo())) {
            throw new BadRequestException("路段区域编号不能为空");
        }
        if (!StrUtil.isNotBlank(waysection.getWaynetId())) {
            throw new BadRequestException("路段所属路网ID不能为空");
        }
        if (!StrUtil.isNotBlank(waysection.getManageId())) {
            throw new BadRequestException("路段管理机构ID不能为空");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        waysection.setCreateBy(currentUser);
        waysection.setCreateTime(currentDateTime);
        waysection.setUpdateBy(currentUser);
        waysection.setUpdateTime(currentDateTime);
        if (null == waysection.getDatastatusid()) {
            waysection.setDatastatusid(1);
        }
        if (null != waysection.getBeginStakeNoK() && null != waysection.getBeginStakeNoM()) {
            String stakeNo = waysection.getBeginStakeNoK() + "+" + waysection.getBeginStakeNoM();
            waysection.setBeginStakeNo(stakeNo);
        }
        if (null != waysection.getEndStakeNoK() && null != waysection.getEndStakeNoM()) {
            String stakeNo = waysection.getEndStakeNoK() + "+" + waysection.getEndStakeNoM();
            waysection.setEndStakeNo(stakeNo);
        }
        int result = baseWaysectionDao.insert(waysection);

        //添加路线扩展表数据
        if(CollectionUtil.isNotEmpty(waysection.getRouteList())){
            List<Route> routeList = waysection.getRouteList();
            routeList.forEach(e->{
                e.setOperatingWaysectionId(waysection.getWaysectionId());
                routeService.create(e);
            });
        }
        return result;
    }

    /**
     * 删除路段
     *
     * @param waysectionIds
     * @return
     */
    @Override
    @Transactional
    public int delete(Set<String> waysectionIds) {
        if (null != waysectionIds && waysectionIds.size() > 0) {
            //判断是否存在下级
            List<Facilities> facilities = baseFacilitiesDao.selectList(new QueryWrapper<Facilities>().in("waysection_id", waysectionIds));
            if (null != facilities && facilities.size() > 0) {
                throw new BadRequestException("路段下存在设施，无法直接删除");
            }
        }
        waySectionDao.deleteBatchIds(waysectionIds);
        //删除关联路线表的数据
        return routeService.batchDelete(waysectionIds);
    }

    /**
     * 修改路段
     *
     * @param waysection
     * @return
     */
    @Override
    public int update(WaysectionRouteVo waysection) {
        if (!StrUtil.isNotBlank(waysection.getWaysectionId())) {
            throw new BadRequestException("路段id不存在，不能做修改操作");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        if (null != waysection.getBeginStakeNoK() && null != waysection.getBeginStakeNoM()) {
            String stakeNo = "K" + waysection.getBeginStakeNoK() + "+" + waysection.getBeginStakeNoM();
            waysection.setBeginStakeNo(stakeNo);
        }
        if (null != waysection.getEndStakeNoK() && null != waysection.getEndStakeNoM()) {
            String stakeNo = "K" + waysection.getEndStakeNoK() + "+" + waysection.getEndStakeNoM();
            waysection.setEndStakeNo(stakeNo);
        }
        waysection.setUpdateBy(currentUser);
        waysection.setUpdateTime(currentDateTime);
        int result = baseWaysectionDao.updateById(waysection);
        //更新旧数据
        routeService.batchUpdate(waysection);
        return result;
    }

    @Override
    public WaysectionRouteVo detail(String waysectionId) {
        if(StrUtil.isBlank(waysectionId)){
            throw new BadRequestException("路段id不能为空");
        }
        //查询路段
        Waysection waysection = baseWaysectionDao.selectById(waysectionId);
        WaysectionRouteVo waysectionRouteVo = BeanUtil.copyProperties(waysection, WaysectionRouteVo.class);
        if(waysection != null){
            //查询路线
            List<Route> routeList = routeService.queryAll(waysectionId);
            waysectionRouteVo.setRouteList(routeList);
        }
        return waysectionRouteVo;
    }
}
