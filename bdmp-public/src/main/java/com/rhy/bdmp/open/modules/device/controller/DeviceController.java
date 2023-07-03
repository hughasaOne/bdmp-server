package com.rhy.bdmp.open.modules.device.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.SpringContextHolder;
import com.rhy.bdmp.open.modules.device.domain.bo.*;
import com.rhy.bdmp.open.modules.device.domain.vo.DeviceVo;
import com.rhy.bdmp.open.modules.device.domain.vo.StatDeviceNumByTypeVo;
import com.rhy.bdmp.open.modules.device.enums.DeviceTypeEnum;
import com.rhy.bdmp.open.modules.device.service.AbstractDeviceService;
import com.rhy.bdmp.open.modules.device.service.IDeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author weicaifu
 */
@Api(tags = "设备服务")
@RestController
@RequestMapping("/bdmp/public/device")
@Component(value = "DeviceControllerV1")
public class DeviceController {

    @Resource(name = "deviceServiceImplV1")
    private IDeviceService deviceService;

    @ApiOperation(value = "统计指定类型的设备数", position = 1)
    @PostMapping("/statistics/num/v1")
    public RespResult<List<StatDeviceNumByTypeVo>> statDeviceNumByType(@RequestBody StatDeviceNumByTypeBo commonBo){
        return RespResult.ok(deviceService.statDeviceNumByType(commonBo));
    }

    @ApiOperation(value = "获取设备列表", position = 2)
    @PostMapping("/list/v1")
    public RespResult getDeviceList(@RequestBody DeviceListBo deviceListBo){
        Set<String> codes = deviceListBo.getCodes();
        if (CollUtil.isNotEmpty(codes) && (null == deviceListBo.getCategoryType() || 1 == deviceListBo.getCategoryType())){
            // 根据设备大类的查询
            List<Object> resList = new ArrayList<>();
            String nodeType = deviceListBo.getNodeType();
            for (String deviceType : codes) {
                DeviceTypeEnum match = DeviceTypeEnum.match(deviceType);
                deviceListBo.setNodeType(nodeType);
                if (null == match){
                    List<?> list = deviceService.list(deviceListBo);
                    if (CollUtil.isNotEmpty(list)){
                        resList.addAll(list);
                    }
                }
                else {
                    try {
                        Class<?> aClass = Class.forName(match.getService());
                        AbstractDeviceService deviceService = (AbstractDeviceService)SpringContextHolder.getBean(aClass);
                        Set<String> deviceTypes = new HashSet<>();
                        deviceTypes.add(deviceType);
                        deviceListBo.setCodes(deviceTypes);
                        // 获取到某一类设备类型的集合
                        List<?> list = deviceService.list(deviceListBo);
                        if (CollUtil.isNotEmpty(list)){
                            resList.addAll(list);
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return RespResult.ok(resList);
        }
        return RespResult.ok(deviceService.list(deviceListBo));
    }

    @ApiOperation(value = "设备分页查询", position = 2)
    @PostMapping("/page/v1")
    public RespResult<PageUtil<DeviceVo>> getDevicePage(@RequestBody DevicePageBo devicePageBo){
        return RespResult.ok(deviceService.getDevicePage(devicePageBo));
    }

    @ApiOperation(value = "获取设备详情", position = 3)
    @PostMapping("/detail/v1")
    public RespResult<DeviceVo> getDeviceDetail(@RequestBody DeviceDetailBo deviceDetailBo){
        String deviceType = deviceDetailBo.getDeviceType();
        if (StrUtil.isBlank(deviceType)){
            return RespResult.ok(deviceService.detail(deviceDetailBo));
        }

        DeviceTypeEnum match = DeviceTypeEnum.match(deviceType);
        if (null == match){
            return RespResult.ok(deviceService.detail(deviceDetailBo));
        }
        DeviceVo detail = null;
        try {
            Class<?> aClass = Class.forName(match.getService());
            AbstractDeviceService deviceService = (AbstractDeviceService)SpringContextHolder.getBean(aClass);
            detail = deviceService.detail(deviceDetailBo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RespResult.ok(detail);
    }

    @ApiOperation(value = "组织路段设施设备类型设备树", position = 4)
    @PostMapping("/all/node/tree/v1")
    public RespResult getAllNodeTree(@RequestBody AllNodeTreeBo allNodeTreeBo){
        return RespResult.ok(deviceService.getAllNodeTree(allNodeTreeBo));
    }

    @ApiOperation(value = "获取设备的所属设备", position = 5)
    @GetMapping("/belongToDevice/v1")
    public RespResult<DeviceVo> belongToDevice(@RequestParam String deviceId){
        return RespResult.ok(deviceService.belongToDevice(deviceId));
    }
}
