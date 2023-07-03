package com.rhy.bdmp.system.modules.assets.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.*;
import com.rhy.bdmp.base.modules.assets.dao.BaseDeviceDao;
import com.rhy.bdmp.base.modules.assets.dao.BaseFacilitiesDao;
import com.rhy.bdmp.base.modules.assets.domain.po.Device;
import com.rhy.bdmp.base.modules.assets.domain.po.Facilities;
import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesExt;
import com.rhy.bdmp.system.modules.assets.dao.FacilitiesDao;
import com.rhy.bdmp.system.modules.assets.domain.po.User;
import com.rhy.bdmp.system.modules.assets.domain.vo.FacilitiesInfoVo;
import com.rhy.bdmp.system.modules.assets.domain.vo.FacilitiesVo;
import com.rhy.bdmp.system.modules.assets.enums.EnumFacilitiesType;
import com.rhy.bdmp.system.modules.assets.enums.PermissionsEnum;
import com.rhy.bdmp.system.modules.assets.service.*;
import org.apache.commons.collections4.CollectionUtils;
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
public class FacilitiesServiceImpl extends ServiceImpl<BaseFacilitiesDao, Facilities> implements IFacilitiesService {

    @Resource
    private BaseDeviceDao baseDeviceDao;
    @Resource
    private BaseFacilitiesDao baseFacilitiesDao;
    @Resource
    private FacilitiesDao facilitiesDao;
    @Resource
    private IAssetsPermissionsTreeService assetsPermissionsTreeService;
    @Resource
    private IFacilitiesBridgeService facilitiesBridgeService;
    @Resource
    private IFacilitiesTunnelService facilitiesTunnelService;
    @Resource
    private IFacilitiesServiceAreaService facilitiesServiceAreaService;
    @Resource
    private IFacilitiesTollStationService facilitiesTollStationService;
    @Resource
    private IFacilitiesGantryService facilitiesGantryService;

    /**
     * 查询路段设施（分页）
     *
     * @param currentPage          当前页
     * @param size                 页大小
     * @param isUseUserPermissions 是否使用用户权限
     * @param nodeType             节点类型(org,way,fac)
     * @param nodeId               节点ID
     * @param facilitiesType       设施类型
     * @param facilitiesName       设施名称
     * @param isShowChildren       是否获取子设施
     * @return
     */
    @Override
    public Page<FacilitiesVo> queryPage(Integer currentPage, Integer size, Boolean isUseUserPermissions,
                                        String nodeType, String nodeId, String facilitiesType,
                                        String facilitiesName, Boolean isShowChildren,String parentId) {
        // 参数校验提前
        if (StrUtil.isBlank(nodeType) != StrUtil.isBlank(nodeId)) {
            throw new BadRequestException("节点类型、节点ID数据不完整,无法过滤");
        }
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

            if (dataPermissionsLevel!=null && !PermissionsEnum.include(String.valueOf(dataPermissionsLevel))) {
                throw new BadRequestException("当前用户等级为:" + dataPermissionsLevel + ",数据权限不足,不能维护设备");
            }
            if (null != user.getIsAdmin() && 1 == user.getIsAdmin()) {
                isUseUserPermissions = false;
            }
        }
        Page<FacilitiesVo> page = new Page<>(currentPage, size);
        page.setOptimizeCountSql(true);
        return facilitiesDao.query(page, isUseUserPermissions, userId, dataPermissionsLevel,
                nodeType, nodeId, facilitiesType, facilitiesName, isShowChildren, parentId);
    }

    /**
     * 列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<Facilities> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<Facilities> query = new Query<Facilities>(queryVO);
            Page<Facilities> page = query.getPage();
            QueryWrapper<Facilities> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<Facilities>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查询路段设施（不分页）
     *
     * @param isUseUserPermissions 是否使用用户权限
     * @param nodeType             节点类型(org,way,fac)
     * @param nodeId               节点ID
     * @param facilitiesType       设施类型
     * @param facilitiesName       设施名称
     * @param isShowChildren       是否获取子设施
     * @return
     */
    @Override
    public List<FacilitiesVo> query(Boolean isUseUserPermissions, String nodeType, String nodeId, String facilitiesType, String facilitiesName, Boolean isShowChildren,String parentId) {
        // 参数校验提前
        if (StrUtil.isBlank(nodeType) != StrUtil.isBlank(nodeId)) {
            throw new BadRequestException("节点类型、节点ID数据不完整,无法过滤");
        }
        String userId = null;
        Integer dataPermissionsLevel = null;
        if (isUseUserPermissions) {
            // 带用户权限
            User user = assetsPermissionsTreeService.getCurrentUser();
            dataPermissionsLevel = user.getDataPermissionsLevel();
            userId = user.getUserId();
            // 有设施权限就可以维护
            if (dataPermissionsLevel != null && !PermissionsEnum.include(String.valueOf(dataPermissionsLevel))) {
                throw new BadRequestException("当前用户等级为:" + dataPermissionsLevel + ",数据权限不足,不能维护设备");
            }
            if (null != user.getIsAdmin() && 1 == user.getIsAdmin()) {
                isUseUserPermissions = false;
            }
        }
        return facilitiesDao.query(isUseUserPermissions, userId, dataPermissionsLevel, nodeType, nodeId, facilitiesType, facilitiesName, isShowChildren,parentId);
    }

    /**
     * 新增路段设施
     *
     * @param facilities
     * @return
     */
    @Override
    @Transactional
    public int create(FacilitiesInfoVo facilities) throws Exception {
        if (StrUtil.isNotBlank(facilities.getFacilitiesId())) {
            throw new BadRequestException("设施ID已存在，不能做新增操作");
        }
        if (StrUtil.isBlank(facilities.getFacilitiesName())) {
            throw new BadRequestException("设施名称不能为空");
        }
        if (StrUtil.isBlank(facilities.getFacilitiesCode())) {
            throw new BadRequestException("设施代码不能为空");
        }
        if (StrUtil.isBlank(facilities.getFacilitiesType())) {
            throw new BadRequestException("设施类型不能为空");
        }
        if (StrUtil.isBlank(facilities.getWaysectionId())) {
            throw new BadRequestException("设施所属路段ID不能为空");
        }
        if (StrUtil.isNotBlank(facilities.getParentId())) {
            Facilities fac = baseFacilitiesDao.selectById(facilities.getParentId());
            if (null != fac) {
                if (null != fac.getLevel()) {
                    if (2 <= fac.getLevel()) {
                        throw new BadRequestException("子设施级别不能超过2级");
                    } else {
                        facilities.setLevel(fac.getLevel() + 1);
                    }
                } else {
                    //有parent，但是parentLevel为null
                    facilities.setLevel(2);
                }
            } else {
                throw new BadRequestException("该节点不存在");
            }
        } else {
            facilities.setLevel(1);
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        facilities.setCreateBy(currentUser);
        facilities.setCreateTime(currentDateTime);
        facilities.setUpdateBy(currentUser);
        facilities.setUpdateTime(currentDateTime);
        if (null == facilities.getDatastatusid()) {
            facilities.setDatastatusid(1);
        }
        if (null != facilities.getBeginStakeNoK() && null != facilities.getBeginStakeNoM()) {
            String stakeNo = "K" + facilities.getBeginStakeNoK() + "+" + facilities.getBeginStakeNoM();
            facilities.setBeginStakeNo(stakeNo);
        }
        if (null != facilities.getEndStakeNoK() && null != facilities.getEndStakeNoM()) {
            String stakeNo = "K" + facilities.getEndStakeNoK() + "+" + facilities.getEndStakeNoM();
            facilities.setEndStakeNo(stakeNo);
        }
        int result = baseFacilitiesDao.insert(facilities);
        //保持设施扩展表数据
        if (CollectionUtil.isNotEmpty(facilities.getFacilitiesExt())) {
            List<Object> facilitiesExt = facilities.getFacilitiesExt();
            // 门架特殊处理
            String[] split = facilities.getFacilitiesType().split("323307");
            if (ArrayUtil.isNotEmpty(split) && !split[0].equals(facilities.getFacilitiesType())){
                facilities.setFacilitiesType("32330700");
            }
            EnumFacilitiesType facilitiesType = EnumFacilitiesType.find(facilities.getFacilitiesType());

            if (facilitiesType != null) {
                String po = facilitiesType.getFacilitiesExtPo();
                String dao = facilitiesType.getFacilitiesExtDao();
                BaseMapper baseMapper = (BaseMapper) SpringContextHolder.getBean(Class.forName(dao));
                for (int i = 0; i < facilitiesExt.size(); i++) {
                    FacilitiesExt extVo = (FacilitiesExt) JSONUtil.toBean(JSONUtil.parseObj(facilitiesExt.get(i)), Class.forName(po));
                    extVo.setFacilitiesId(facilities.getFacilitiesId());
                    baseMapper.insert(extVo);
                }
            }
        }
        return result;
    }

    /**
     * 删除路段设施
     *
     * @param facilitiesIds
     * @return
     */
    @Override
    @Transactional
    public int delete(Set<String> facilitiesIds) {
        if (null != facilitiesIds && facilitiesIds.size() > 0) {
            //判断是否存在下级
            List<Facilities> facilitiesList = facilitiesDao.selectList(new QueryWrapper<Facilities>().in("parent_id", facilitiesIds));
            if (null != facilitiesList && facilitiesList.size() > 0) {
                throw new BadRequestException("设施下存在子设施，无法直接删除");
            }
            List<Device> deviceList = baseDeviceDao.selectList(new QueryWrapper<Device>().in("facilities_id", facilitiesIds));
            if (null != deviceList && deviceList.size() > 0) {
                throw new BadRequestException("设施下存在设备，无法直接删除");
            }
        }
        //删除设施主表
        facilitiesDao.deleteBatchIds(facilitiesIds);
        //删除设施扩展表数据
        facilitiesBridgeService.batchDelete(facilitiesIds);
        facilitiesTunnelService.batchDelete(facilitiesIds);
        facilitiesServiceAreaService.batchDelete(facilitiesIds);
        facilitiesTollStationService.batchDelete(facilitiesIds);
        facilitiesGantryService.batchDelete(facilitiesIds);
        return 1;
    }

    /**
     * 修改路段设施
     *
     * @param facilities
     * @return
     */
    @Transactional
    @Override
    public int update(FacilitiesInfoVo facilities) throws Exception {
        if (!StrUtil.isNotBlank(facilities.getFacilitiesId())) {
            throw new BadRequestException("设施Id不存在，不能做修改操作");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        if (null != facilities.getBeginStakeNoK() && null != facilities.getBeginStakeNoM()) {
            String stakeNo = "K" + facilities.getBeginStakeNoK() + "+" + facilities.getBeginStakeNoM();
            facilities.setBeginStakeNo(stakeNo);
        }
        if (null != facilities.getEndStakeNoK() && null != facilities.getEndStakeNoM()) {
            String stakeNo = "K" + facilities.getEndStakeNoK() + "+" + facilities.getEndStakeNoM();
            facilities.setEndStakeNo(stakeNo);
        }

        // 判断是否修改了所属路段, 如果修改了,则需更新子设施所属路段
        Facilities facilitiesDb = baseFacilitiesDao.selectById(facilities.getFacilitiesId());
        if (null != facilitiesDb && !facilitiesDb.getWaysectionId().equals(facilities.getWaysectionId())) {
            String waySectionId = facilities.getWaysectionId();
            if (StrUtil.isNotBlank(waySectionId)) {
                facilitiesDao.updateWaySectionByFacChlild(facilities.getFacilitiesId(), waySectionId);
            }
        }

        //判断选择上级设施
        List<Facilities> facilitiesList = facilitiesDao.selectList(new QueryWrapper<Facilities>().in("parent_id", facilities.getFacilitiesId()));
        if (CollectionUtils.isNotEmpty(facilitiesList)) {
            facilitiesList.forEach(f -> {
                if (f.getFacilitiesId().equals(facilities.getParentId())) {
                    throw new BadRequestException("不能选择自己或下级为上级设施");
                } else if (!f.getFacilitiesId().equals(facilities.getParentId()) && facilities.getParentId() != null && facilities.getParentId() != "") {
                    throw new BadRequestException("该设施存在子设施，设施级别不能超过2级");
                }
            });
        }

        facilities.setUpdateBy(currentUser);
        facilities.setUpdateTime(currentDateTime);
        int result = baseFacilitiesDao.updateById(facilities);
        //批量更新设施扩展表数据
        // 门架特殊处理
        String[] split = facilities.getFacilitiesType().split("323307");
        if (ArrayUtil.isNotEmpty(split)  && !split[0].equals(facilities.getFacilitiesType())){
            facilities.setFacilitiesType("32330700");
            facilitiesDb.setFacilitiesType("32330700");
        }
        EnumFacilitiesType facilitiesType = EnumFacilitiesType.find(facilities.getFacilitiesType());
        if (facilitiesType != null) {
            LambdaQueryWrapper<Facilities> wrapper = Wrappers.lambdaQuery();
            wrapper.eq(Facilities::getFacilitiesId, facilities.getFacilitiesId());
            //先删除原数据
            EnumFacilitiesType old = EnumFacilitiesType.find(facilitiesDb.getFacilitiesType());
            BaseMapper baseMapper = (BaseMapper) SpringContextHolder.getBean(Class.forName(old.getFacilitiesExtDao()));
            baseMapper.delete(wrapper);

        }
        List<Object> facilitiesExt = facilities.getFacilitiesExt();
        if (CollectionUtil.isNotEmpty(facilities.getFacilitiesExt())) {
            //再插入新数据
            for (Object obj : facilitiesExt) {
                FacilitiesExt extVo = (FacilitiesExt) JSONUtil.toBean(JSONUtil.parseObj(obj), Class.forName(facilitiesType.getFacilitiesExtPo()));
                extVo.setFacilitiesId(facilities.getFacilitiesId());
                BaseMapper extMapper = (BaseMapper) SpringContextHolder.getBean(Class.forName(facilitiesType.getFacilitiesExtDao()));
                extMapper.insert(extVo);
            }
        }
        return result;
    }

    /**
     * 根据路段查收费站集合
     *
     * @param waysectionId
     * @return
     */
    @Override
    public List<Facilities> findTollStations(String waysectionId) {
        List<Facilities> facilitiesList = facilitiesDao.findTollStations(waysectionId);
        return facilitiesList;
    }

    @Override
    public FacilitiesInfoVo detail(String facilitiesId) throws Exception {
        if (!StrUtil.isNotBlank(facilitiesId)) {
            throw new BadRequestException("设施Id不能为空");
        }
        Facilities facilities = baseFacilitiesDao.selectById(facilitiesId);
        //获取设施扩展数据
        if (facilities != null) {
            // 门架特殊处理
            String tempFacType = facilities.getFacilitiesType();
            String[] split = facilities.getFacilitiesType().split("323307");
            if (ArrayUtil.isNotEmpty(split) && !split[0].equals(facilities.getFacilitiesType())){
                facilities.setFacilitiesType("32330700");
            }
            FacilitiesInfoVo facilitiesInfo = BeanUtil.copyProperties(facilities, FacilitiesInfoVo.class);
            EnumFacilitiesType facilitiesType = EnumFacilitiesType.find(facilitiesInfo.getFacilitiesType());
            if (facilitiesType != null) {
                String dao = facilitiesType.getFacilitiesExtDao();
                BaseMapper baseMapper = (BaseMapper) SpringContextHolder.getBean(Class.forName(dao));
                LambdaQueryWrapper<Facilities> wrapper = Wrappers.lambdaQuery();
                wrapper.eq(Facilities::getFacilitiesId, facilitiesInfo.getFacilitiesId());
                List<Object> list = baseMapper.selectList(wrapper);
                facilitiesInfo.setFacilitiesExt(list);
            }
            facilitiesInfo.setFacilitiesType(tempFacType);
            return facilitiesInfo;
        }
        return null;
    }
}
