package com.rhy.bdmp.open.modules.device.enums;

import cn.hutool.core.util.StrUtil;

public enum DeviceTypeEnum {
    CAMERA("摄像头","130100","com.rhy.bdmp.open.modules.device.service.impl.VideoServiceImpl"),
    INFORMATIONBOARD("情报板","130200","com.rhy.bdmp.open.modules.device.service.impl.InformationBoardServiceImpl"),
            ;

    DeviceTypeEnum(String name,String deviceType,String serviceImpl) {
        this.name = name;
        this.deviceType = deviceType;
        this.serviceImpl = serviceImpl;
    }

    private final String name;
    private final String deviceType;
    private final String serviceImpl;

    public static DeviceTypeEnum match(String deviceType){
        DeviceTypeEnum match = null;
        for (DeviceTypeEnum deviceTypeEnum : DeviceTypeEnum.values()) {
            if (StrUtil.equals(deviceTypeEnum.getDeviceType(),deviceType)){
                match = deviceTypeEnum;
                break;
            }
        }
        return match;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public String getService() {
        return serviceImpl;
    }

    public String getName() {
        return name;
    }
}
