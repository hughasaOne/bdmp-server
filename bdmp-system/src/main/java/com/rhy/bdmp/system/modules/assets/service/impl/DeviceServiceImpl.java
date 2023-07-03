package com.rhy.bdmp.system.modules.assets.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rhy.bcp.common.domain.vo.NodeVo;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.*;
import com.rhy.bdmp.base.modules.assets.dao.BaseDeviceDao;
import com.rhy.bdmp.base.modules.assets.domain.po.*;
import com.rhy.bdmp.base.modules.assets.service.*;
import com.rhy.bdmp.base.modules.sys.domain.po.Org;
import com.rhy.bdmp.system.modules.assets.domain.bo.RequiredFieldsBo;
import com.rhy.bdmp.system.modules.assets.enums.NodeTypeEnum;
import com.rhy.bdmp.system.modules.assets.enums.PermissionsEnum;
import com.rhy.bdmp.system.modules.assets.dao.DeviceDao;
import com.rhy.bdmp.system.modules.assets.domain.po.User;
import com.rhy.bdmp.system.modules.assets.domain.vo.DeviceGridVo;
import com.rhy.bdmp.system.modules.assets.domain.vo.DeviceVo;
import com.rhy.bdmp.system.modules.assets.domain.vo.UploadBoxEcelVo;
import com.rhy.bdmp.system.modules.assets.enums.EnumDeviceType;
import com.rhy.bdmp.system.modules.assets.service.DeviceBoxService;
import com.rhy.bdmp.system.modules.assets.service.IAssetsPermissionsTreeService;
import com.rhy.bdmp.system.modules.assets.service.IDeviceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @auther xiabei
 * @Description
 * @date 2021/4/14
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<BaseDeviceDao, Device> implements IDeviceService {

    @Resource
    private BaseDeviceDao baseDeviceDao;
    @Resource
    private DeviceDao deviceDao;
    @Resource
    private IAssetsPermissionsTreeService assetsPermissionsTreeService;

    @Resource
    private IBaseDeviceService deviceService;

    @Resource
    private IBaseFacilitiesService facService;

    @Resource
    private IBaseWaysectionService wayService;

    @Resource
    private DeviceBoxService boxService;

    @Resource
    private IBaseDeviceBoxService deviceBoxService;

    @Resource
    private IBaseDictSystemService baseDictSystemService;


    @Override
    public Map<String, Object> checkRequiredFields(RequiredFieldsBo requiredFieldsBo) {
        Map<String, Object> resMap = new HashMap<>();
        String ip = requiredFieldsBo.getIp();
        String sn = requiredFieldsBo.getSeriaNumber();
        if (StrUtil.isNotBlank(ip)){
            // 同一设施下ip不重复
            String facId = requiredFieldsBo.getFacId();
            if (StrUtil.isBlank(facId)){
                throw new BadRequestException("设施id不为空");
            }

            List<Device> ipRepeatedDevice = deviceService.list(new QueryWrapper<Device>().eq("facilities_id", facId).and(wrapper -> {
                wrapper.eq("ip", ip);
            }));

            String deviceId = requiredFieldsBo.getDeviceId();
            if (StrUtil.isBlank(deviceId)){
                // 新增
                if (CollUtil.isNotEmpty(ipRepeatedDevice)){
                    throw new BadRequestException("该设施下ip重复，请检查");
                }
            }
            else {
                // 修改
                if (CollUtil.isNotEmpty(ipRepeatedDevice)){
                    for (Device device : ipRepeatedDevice) {
                        if (!device.getDeviceId().equals(deviceId)){
                            // 存在ip相同但id不同的，
                            throw new BadRequestException("该设施下ip重复，请检查");
                        }
                    }
                }
            }
            resMap.put("ip",true);
        }

        if (StrUtil.isNotBlank(sn)){
            // 传递过来的sn不重复
            List<Device> snRepeatedDevice = deviceService.list(new QueryWrapper<Device>().eq("seria_number", sn));
            if (CollUtil.isNotEmpty(snRepeatedDevice)){
                throw new BadRequestException("设备序列号重复，请检查");
            }
            resMap.put("sn",true);
        }


        return resMap;
    }

    /**
     * 查询设备（分页）
     *
     * @param currentPage          当前页
     * @param size                 页大小
     * @param isUseUserPermissions 是否使用用户权限
     * @param nodeType             节点类型(org,way,fac)
     * @param nodeId               节点ID
     * @param deviceType           设备类型
     * @param systemId             系统ID
     * @param deviceName           设备名称
     * @return
     */
    @Override
    public Page<DeviceGridVo> queryPage(Integer currentPage, Integer size, Boolean isUseUserPermissions,
                                        String nodeType, String nodeId, String deviceType,
                                        String systemId, String deviceName,String deviceCode) {
        if (!"other".equals(nodeId)){
            // 参数校验提前
            if (StrUtil.isBlank(nodeType) != StrUtil.isBlank(nodeId)) {
                throw new BadRequestException("节点类型、节点ID数据不完整,无法过滤");
            }
            if (NodeTypeEnum.excludeByName(nodeType)) {
                throw new BadRequestException("未知的节点类型: " + nodeType);
            }
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
            if (null != user.getIsAdmin() && 1 == user.getIsAdmin()) {
                isUseUserPermissions = false;
            }
            else {
                if (null == dataPermissionsLevel || !PermissionsEnum.include(String.valueOf(dataPermissionsLevel))) {
                    throw new BadRequestException("当前用户等级为:" + dataPermissionsLevel + ",数据权限不足,不能维护设备");
                }
            }
        }

        Page<DeviceGridVo> page = new Page<>(currentPage, size);
        page.setOptimizeCountSql(true);

        // 2021-11-03 update 查询 systemId 下所有子系统合集
        List<String> systemIds = new ArrayList<>();
        if (StrUtil.isNotBlank(systemId)) {
            // systemId 非空时,才去查询系统字典表
            systemIds.add(systemId);
            List<DictSystem> dictSystemList = baseDictSystemService.list(new LambdaQueryWrapper<DictSystem>().eq(DictSystem::getParentId, systemId));
            while (!dictSystemList.isEmpty()) {
                List<String> tempIds = dictSystemList.stream().map(DictSystem::getSystemId).collect(Collectors.toList());
                systemIds.addAll(tempIds);
                dictSystemList = baseDictSystemService.list(new LambdaQueryWrapper<DictSystem>().in(DictSystem::getParentId, tempIds));
            }
        }

        return deviceDao.queryPage(page, isUseUserPermissions, userId, dataPermissionsLevel, nodeType,
                nodeId, deviceType, systemIds, deviceName,deviceCode);
    }

    /**
     * 设备列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<Device> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<Device> query = new Query<Device>(queryVO);
            Page<Device> page = query.getPage();
            QueryWrapper<Device> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<Device>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看设备(根据ID)
     *
     * @param deviceId
     * @return
     */
    @Override
    public DeviceVo detail(String deviceId) throws Exception {
        if (StrUtil.isBlank(deviceId)) {
            throw new BadRequestException("设备ID不存在");
        }
        Device device = baseDeviceDao.selectById(deviceId);
        if (null != device) {
            DeviceVo deviceVo = new DeviceVo();
            BeanUtil.copyProperties(device, deviceVo);
            deviceVo.setDeviceExt(new ArrayList<Object>());

            //根据设施Id拿到设施名称和路段Id
            Facilities fac = null;
            if (StrUtil.isNotBlank(device.getFacilitiesId())) {
                fac = facService.getById(device.getFacilitiesId());
                deviceVo.setFacilitiesName(fac.getFacilitiesName());
            }

            if (null != fac && StrUtil.isNotBlank(fac.getWaysectionId())) {
                Waysection way = wayService.getById(fac.getWaysectionId());
                deviceVo.setWaysectionId(fac.getWaysectionId());
                deviceVo.setWaysectionName(way.getWaysectionName());
            }

            if (StrUtil.isNotBlank(deviceVo.getDeviceType())) {
                String deviceType = deviceVo.getDeviceType();
                EnumDeviceType enumDeviceType = EnumDeviceType.find(deviceType);
                if (null != enumDeviceType && StrUtil.isNotBlank(enumDeviceType.getDeviceExtDao())) {
                    String[] deviceExtDaoArray = enumDeviceType.getDeviceExtDao().split(",");
                    for (String deviceExtDao : deviceExtDaoArray) {
                        BaseMapper baseMapper = (BaseMapper) SpringContextHolder.getBean(Class.forName(deviceExtDao));
                        Object object = baseMapper.selectById(deviceId);
                        if (null != object) {
                            deviceVo.getDeviceExt().add(baseMapper.selectById(deviceId));
                        }
                    }
                }
            }
            return deviceVo;
        } else {
            return null;
        }
    }

    /**
     * 新增设备
     *
     * @param deviceVo
     * @return
     */
    @Override
    @Transactional
    public int create(DeviceVo deviceVo) throws Exception {
        if (StrUtil.isNotBlank(deviceVo.getDeviceId())) {
            throw new BadRequestException("设备ID已存在，不能做新增操作");
        }
        if (StrUtil.isBlank(deviceVo.getDeviceType())) {
            throw new BadRequestException("设备类型不能为空");
        }
        List<Device> boxs = deviceService.list(new QueryWrapper<Device>().eq("device_code", deviceVo.getDeviceCode()));
        if (!boxs.isEmpty()) {
            throw new BadRequestException("设备编号重复，请重新输入");
        }
        Device device = new Device();
        BeanUtil.copyProperties(deviceVo, device);
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        device.setCreateBy(currentUser);
        device.setCreateTime(currentDateTime);
        device.setUpdateBy(currentUser);
        device.setUpdateTime(currentDateTime);
        //datastatusid默认为1
        if (null == deviceVo.getDatastatusid()) {
            device.setDatastatusid(1);
        }
        //桩号等于桩号K+桩号M
        baseDeviceDao.insert(device);
        // 保存扩展表信息
        if (null != deviceVo.getDeviceExt() && 0 < deviceVo.getDeviceExt().size()) {
            List<Object> deviceExtList = deviceVo.getDeviceExt();
            String deviceType = deviceVo.getDeviceType();
            EnumDeviceType enumDeviceType = EnumDeviceType.find(deviceType);
            if (null != enumDeviceType && StrUtil.isNotBlank(enumDeviceType.getDeviceExtPo()) && StrUtil.isNotBlank(enumDeviceType.getDeviceExtDao())) {
                String[] deviceExtPoArray = enumDeviceType.getDeviceExtPo().split(",");
                String[] deviceExtDaoArray = enumDeviceType.getDeviceExtDao().split(",");
                if (deviceExtList.size() <= deviceExtPoArray.length && deviceExtList.size() <= deviceExtDaoArray.length) {
                    for (int i = 0; i < deviceExtList.size(); i++) {
                        DeviceExt deviceExt = (DeviceExt) JSONUtil.toBean(JSONUtil.parseObj(deviceExtList.get(i)), Class.forName(deviceExtPoArray[i]));
                        BaseMapper baseMapper = (BaseMapper) SpringContextHolder.getBean(Class.forName(deviceExtDaoArray[i]));
                        deviceExt.setDeviceId(device.getDeviceId());
                        if (deviceVo.getDeviceType().equals("130700")) {
                            //终端箱类型时，维护公共字段
                            deviceExt.setDevName(device.getDeviceName());
                            deviceExt.setSn(device.getDeviceCode());
                            deviceExt.setCreateBy(currentUser);
                            deviceExt.setCreateTime(currentDateTime);
                            deviceExt.setUpdateBy(currentUser);
                            deviceExt.setUpdateTime(currentDateTime);
                            deviceExt.setManufacturer(deviceVo.getManufacturer());
                        }
                        baseMapper.insert(deviceExt);
                    }
                }
            }
        }
        return 1;
    }

    /**
     * 修改设备
     *
     * @param deviceVo
     * @return
     */
    @Override
    @Transactional
    public int update(DeviceVo deviceVo) throws Exception {
        if (StrUtil.isBlank(deviceVo.getDeviceId())) {
            throw new BadRequestException("设备ID不存在，不能做修改操作");
        }
        if (StrUtil.isBlank(deviceVo.getDeviceType())) {
            throw new BadRequestException("设备类型不能为空");
        }
        List<Device> boxs = deviceService.list(new QueryWrapper<Device>().eq("device_code", deviceVo.getDeviceCode()));
        if (!boxs.isEmpty()) {
            for (Device box : boxs) {
                if (!(box.getDeviceId().equals(deviceVo.getDeviceId())) && box.getDeviceCode().equals(deviceVo.getDeviceCode())) {
                    throw new BadRequestException("设备编号重复，请重新输入");
                }
            }
        }
        Device device = new Device();
        BeanUtil.copyProperties(deviceVo, device);

        // 判断是否修改了设备类型, 如果修改了,则先删除扩展表信息
        Device deviceDb = baseDeviceDao.selectById(device.getDeviceId());
        // 设备类型是否被修改
        boolean isEditDeviceType = false;
        if (null != deviceDb && !device.getDeviceType().equals(deviceDb.getDeviceType())) {
            String deviceType = deviceDb.getDeviceType();
            if (StrUtil.isNotBlank(deviceType)) {
                List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
                EnumDeviceType enumDeviceType = EnumDeviceType.find(deviceType);
                if (null != enumDeviceType) {
                    String tableNames = enumDeviceType.getDeviceExtTableName();
                    if (StrUtil.isNotBlank(tableNames)) {
                        String[] tableNameArray = tableNames.split(",");
                        for (String tableName : tableNameArray) {
                            Map<String, String> map = new HashMap<>();
                            map.put("tableName", tableName);
                            map.put("deviceId", device.getDeviceId());
                            mapList.add(map);
                        }
                    }
                }
                if (0 < mapList.size()) {
                    deviceDao.deleteDeviceExtTable(mapList);
                    isEditDeviceType = true;
                }
            }
        }

        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        //桩号等于桩号K+桩号M
        /*if (null != deviceVo.getBeginStakeNoK() && null != deviceVo.getBeginStakeNoM()){
            String stakeNo = "K" + deviceVo.getBeginStakeNoK() + "+" + deviceVo.getBeginStakeNoM();
            device.setBeginStakeNo(stakeNo);
        }
        if (null != deviceVo.getEndStakeNoK() && null != deviceVo.getEndStakeNoM()){
            String stakeNo = "K" + deviceVo.getEndStakeNoK() + "+" + deviceVo.getEndStakeNoM();
            device.setEndStakeNo(stakeNo);
        }
        if(null != deviceVo.getCenterOffNoK() && null != deviceVo.getCenterOffNoM()){
            String centerOffNo = "K" + deviceVo.getCenterOffNoK() + "+" + deviceVo.getCenterOffNoM();
            device.setCenterOffNo(centerOffNo);
        }*/
        device.setUpdateBy(currentUser);
        device.setUpdateTime(currentDateTime);
        baseDeviceDao.updateById(device);
        // 保存扩展表信息
        if (null != deviceVo.getDeviceExt() && 0 < deviceVo.getDeviceExt().size()) {
            List<Object> deviceExtList = deviceVo.getDeviceExt();
            String deviceType = deviceVo.getDeviceType();
            EnumDeviceType enumDeviceType = EnumDeviceType.find(deviceType);
            if (null != enumDeviceType && StrUtil.isNotBlank(enumDeviceType.getDeviceExtPo()) && StrUtil.isNotBlank(enumDeviceType.getDeviceExtDao())) {
                String[] deviceExtPoArray = enumDeviceType.getDeviceExtPo().split(",");
                String[] deviceExtDaoArray = enumDeviceType.getDeviceExtDao().split(",");
                if (deviceExtList.size() <= deviceExtPoArray.length && deviceExtList.size() <= deviceExtDaoArray.length) {
                    for (int i = 0; i < deviceExtList.size(); i++) {
                        DeviceExt deviceExt = (DeviceExt) JSONUtil.toBean(JSONUtil.parseObj(deviceExtList.get(i)), Class.forName(deviceExtPoArray[i]));
                        BaseMapper baseMapper = (BaseMapper) SpringContextHolder.getBean(Class.forName(deviceExtDaoArray[i]));
                        // 修改了设备类型，扩展表信息为新增
                        if (isEditDeviceType) {
                            if (deviceVo.getDeviceType().equals("130700")) {
                                //终端箱类型时，维护公共字段
                                deviceExt.setDevName(device.getDeviceName());
                                deviceExt.setSn(device.getDeviceCode());
                                deviceExt.setCreateBy(currentUser);
                                deviceExt.setCreateTime(currentDateTime);
                                deviceExt.setUpdateBy(currentUser);
                                deviceExt.setUpdateTime(currentDateTime);
                            }
                            deviceExt.setDeviceId(device.getDeviceId());
                            baseMapper.insert(deviceExt);
                        } else {
                            if (deviceVo.getDeviceType().equals("130700")) {
                                //终端箱类型时，维护公共字段
                                deviceExt.setDevName(device.getDeviceName());
                                deviceExt.setSn(device.getDeviceCode());
                                deviceExt.setCreateBy(currentUser);
                                deviceExt.setCreateTime(currentDateTime);
                                deviceExt.setUpdateBy(currentUser);
                                deviceExt.setUpdateTime(currentDateTime);
                            }
                            baseMapper.updateById(deviceExt);
                        }
                    }
                }
            }
        }
        return 1;
    }

    /**
     * 删除设备
     *
     * @param deviceIds
     * @return
     */
    @Override
    @Transactional
    public int delete(Set<String> deviceIds) {
        if (null != deviceIds && deviceIds.size() > 0) {
            List<Device> devices = baseDeviceDao.selectBatchIds(deviceIds);
            List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
            for (Device device : devices) {
                String deviceType = device.getDeviceType();
                if (StrUtil.isNotBlank(deviceType)) {
                    EnumDeviceType enumDeviceType = EnumDeviceType.find(deviceType);
                    if (null != enumDeviceType) {
                        String tableNames = enumDeviceType.getDeviceExtTableName();
                        if (StrUtil.isNotBlank(tableNames)) {
                            String[] tableNameArray = tableNames.split(",");
                            for (String tableName : tableNameArray) {
                                Map<String, String> map = new HashMap<>();
                                map.put("tableName", tableName);
                                map.put("deviceId", device.getDeviceId());
                                mapList.add(map);
                            }
                        }
                    }
                }
            }
            if (0 < mapList.size()) {
                deviceDao.deleteDeviceExtTable(mapList);
            }
            return baseDeviceDao.deleteBatchIds(deviceIds);
        }
        return 0;
    }

    /**
     * 根据设施id查找设备管理单位
     *
     * @param facilitiesId
     * @return
     */
    @Override
    public List<NodeVo> findManageDepartment(String facilitiesId) {
        List<NodeVo> nodeList = new ArrayList<NodeVo>();
        List<Org> orgList = deviceDao.findManageDepartment(facilitiesId);
        if (null != orgList && orgList.size() > 0) {
            for (Org org : orgList) {
                NodeVo nodeVo = new NodeVo();
                nodeVo.setId(org.getOrgId());
                nodeVo.setValue(org.getOrgId());
                nodeVo.setLabel(org.getOrgShortName());
                nodeVo.setParentId(org.getParentId());
                nodeVo.setSort(org.getSort());
                nodeVo.setNoteType("org");
                nodeVo.setMoreInfo(org);
                nodeList.add(nodeVo);
            }
            return nodeList;
        }
        return null;
    }

    /**
     * 查询没有设施Id的设备
     *
     * @param currentPage          当前页
     * @param size                 页大小
     * @param isUseUserPermissions 是否使用用户权限过滤
     */
    @Override
    public Page<DeviceGridVo> getDeviceNoFacId(Integer currentPage, Integer size, Boolean isUseUserPermissions) {
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
            // 有设施权限就可以维护
            if (dataPermissionsLevel != null && !PermissionsEnum.include(String.valueOf(dataPermissionsLevel))) {
                throw new BadRequestException("当前用户等级为:" + dataPermissionsLevel + ",数据权限不足,不能维护设备");
            }
            if (null != user.getIsAdmin() && 1 == user.getIsAdmin()) {
                isUseUserPermissions = false;
            }
        }
        Page<DeviceGridVo> page = new Page<>(currentPage, size);
        page.setOptimizeCountSql(false);
        return deviceDao.getDeviceNoFacId(page, isUseUserPermissions, userId, dataPermissionsLevel);
    }

    /**
     * @param nodeVo              前端传递的参数
     * @param uploadBoxEcelVolist 解析好的终端箱excel数据
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String saveBatchBox(NodeVo nodeVo, List<UploadBoxEcelVo> uploadBoxEcelVolist) {
        // 导入前先同步终端箱系统的数据，这里只做更新，根据excel的ip和终端箱系统的ip
        // 检验必填参数
        for (int i = 0; i < uploadBoxEcelVolist.size(); i++) {
            if (StrUtil.isBlank(uploadBoxEcelVolist.get(i).getIP())) {
                throw new BadRequestException("excel存在设备IP为空，在表格的第 " + (i + 1 + 5) + " 行第" + 2 + "列请重新填写表格");
            }
        }
        // 同步终端箱
        boxService.synBoxInfo();
        // 查询刚同步的终端箱
        List<Device> dataBoxList = deviceDao.getCurSynBox();
        // 记录更新了多少条数据
        int mark = 0;
        // 将excel的数据封装为excel的数据
        Waysection waysection = wayService.getById(nodeVo.getParentId());
        String location = nodeVo.getLabel();
        if (null != waysection) {
            location = waysection.getWaysectionName() + "/" + nodeVo.getLabel();
        };
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        if (0 < uploadBoxEcelVolist.size() && null != dataBoxList && 0 < dataBoxList.size()) {
            for (UploadBoxEcelVo uploadBoxEcelVo : uploadBoxEcelVolist) {
                for (Device device : dataBoxList) {
                    if (StrUtil.isNotBlank(device.getIp()) && uploadBoxEcelVo.getIP().equals(device.getIp())) {
                        if (StrUtil.isNotBlank(uploadBoxEcelVo.getDirection())) {
                            device.setDirection(uploadBoxEcelVo.getDirection());
                        }
                        if (StrUtil.isNotBlank(uploadBoxEcelVo.getStake())) {
                            device.setCenterOffNo(uploadBoxEcelVo.getStake());
                        }
                        if (StrUtil.isNotBlank(uploadBoxEcelVo.getLongitude())) {
                            device.setLongitude(uploadBoxEcelVo.getLongitude());
                        }
                        if (StrUtil.isNotBlank(uploadBoxEcelVo.getLatitude())) {
                            device.setLatitude(uploadBoxEcelVo.getLatitude());
                        }
                        device.setUpdateBy(currentUser);
                        device.setUpdateTime(currentDateTime);
                        device.setFacilitiesId(nodeVo.getId());
                        device.setSystemId("110500");
                        device.setLocation(location);
                        mark++;
                        break;
                    }
                }
            }
        }
        // 更新数据
        deviceService.updateBatchById(dataBoxList);
        // 扩展表
        List<DeviceBox> dataDevBoxlist = deviceBoxService.list();
        if (null != dataDevBoxlist && 0 < dataDevBoxlist.size() && 0 < uploadBoxEcelVolist.size()) {
            for (UploadBoxEcelVo uploadBoxEcelVo : uploadBoxEcelVolist) {
                for (DeviceBox deviceBox : dataDevBoxlist) {
                    if (StrUtil.isNotBlank(deviceBox.getBordIp()) && uploadBoxEcelVo.getIP().equals(deviceBox.getBordIp())) {
                        deviceBox.setUpdateBy(currentUser);
                        deviceBox.setUpdateTime(currentDateTime);
                    }
                }
            }
        }
        // 更新扩展表
        deviceBoxService.updateBatchById(dataDevBoxlist);
        return "成功操作了" + mark + "个设备";
    }


}
