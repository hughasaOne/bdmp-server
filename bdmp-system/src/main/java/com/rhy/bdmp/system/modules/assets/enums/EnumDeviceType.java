package com.rhy.bdmp.system.modules.assets.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @auther xiabei
 * @Description 设备拓展表枚举API
 * @date 2021/4/14
 */
@Getter
@AllArgsConstructor
public enum EnumDeviceType {

    QBB("130200", "情报板", "t_bdmp_assets_device_intelligenceboard", "com.rhy.bdmp.base.modules.assets.domain.po.DeviceIntelligenceboard", "com.rhy.bdmp.base.modules.assets.dao.BaseDeviceIntelligenceboardDao"),
    CJQ("130300", "车检器", "t_bdmp_assets_device_carinspection", "com.rhy.bdmp.base.modules.assets.domain.po.DeviceCarinspection", "com.rhy.bdmp.base.modules.assets.dao.BaseDeviceCarinspectionDao"),
    GWQ("130400", "感温器", "t_bdmp_assets_device_flamestate", "com.rhy.bdmp.base.modules.assets.domain.po.DeviceFlamestate", "com.rhy.bdmp.base.modules.assets.dao.BaseDeviceFlamestateDao"),
    JTBJ("130500", "脚踏报警", "t_bdmp_assets_device_pedalalarm", "com.rhy.bdmp.base.modules.assets.domain.po.DevicePedalalarm", "com.rhy.bdmp.base.modules.assets.dao.BaseDevicePedalalarmDao"),
    JJDH("130600", "紧急电话", "t_bdmp_assets_device_emergencycall", "com.rhy.bdmp.base.modules.assets.domain.po.DeviceEmergencycall", "com.rhy.bdmp.base.modules.assets.dao.BaseDeviceEmergencycallDao"),
    ZDX("130700", "终端箱", "t_bdmp_assets_device_box", "com.rhy.bdmp.base.modules.assets.domain.po.DeviceBox", "com.rhy.bdmp.base.modules.assets.dao.BaseDeviceBoxDao");

    private final String deviceTypeCode;
    private final String deviceTypeName;
    private final String deviceExtTableName;
    private final String deviceExtPo;
    private final String deviceExtDao;


    public static EnumDeviceType find(String val) {
        for (EnumDeviceType dataScopeEnum : EnumDeviceType.values()) {
            if (val.equals(dataScopeEnum.getDeviceTypeCode())) {
                return dataScopeEnum;
            }
        }
        return null;
    }

}
