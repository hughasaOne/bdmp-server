package com.rhy.bdmp.base.modules.assets.domain.po;

import java.util.Date;

/**
* @description
* @author jiangzhimin
* @date 2021-04-18 18:27
* @version V1.0
**/
public abstract class DeviceExt {

    public void setDeviceId(String deviceId){
    }

    public void setDevName(String devName){
    }

    public void setSn(String sn){
    }

    public void setCreateTime(Date createTime) {
    }

    public void setCreateBy(String createBy) {
    }

    public void setUpdateTime(Date updateTime) {
    }

    public void setUpdateBy(String updateBy) {
    }

    public void setManufacturer(String manufacturer) {
    }

    public void setBordIp(String ip){};

    public void setLinkType(String linkType){};
}