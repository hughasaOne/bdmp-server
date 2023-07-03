package com.rhy.bdmp.system.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.Device;
import com.rhy.bdmp.base.modules.assets.domain.po.DeviceExternal;
import com.rhy.bdmp.system.modules.assets.domain.bo.DeviceExternalBo;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IDeviceExternalService {
    List<Map<String,Object>> list(String deviceId);

    List<Device> getDeviceList(DeviceExternalBo externalBo);

    void add(List<DeviceExternal> deviceExternalList);

    void delete(String deviceId,Set<String> externalIds);
}
