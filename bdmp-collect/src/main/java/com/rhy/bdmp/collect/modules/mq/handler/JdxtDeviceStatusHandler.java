package com.rhy.bdmp.collect.modules.mq.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rhy.bdmp.base.modules.assets.domain.po.Device;
import com.rhy.bdmp.base.modules.assets.service.IBaseDeviceService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class JdxtDeviceStatusHandler {
    @Resource
    private IBaseDeviceService deviceService;

    @Async
    public void syncDeviceStatus(String msg) {
        JSONObject deviceStatus = JSONUtil.parseObj(msg);
        String oldId = deviceStatus.get("device_id").toString();
        Object workStatus = deviceStatus.get("work_status");
        List<Device> databaseDevice = deviceService.list(new QueryWrapper<Device>().eq("device_id_old", oldId));
        if (CollUtil.isNotEmpty(databaseDevice)){
            for (Device device : databaseDevice) {
                device.setWorkStatus(null == workStatus ? null : workStatus.toString());
                deviceService.updateById(device);
            }
        }
    }
}
