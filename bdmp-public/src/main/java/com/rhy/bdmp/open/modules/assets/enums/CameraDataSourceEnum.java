package com.rhy.bdmp.open.modules.assets.enums;

/**
 * @Author: yanggj
 * @Description: 视频资源数据来源
 * @Date: 2021/10/26 16:22
 * @Version: 1.0.0
 */
public enum CameraDataSourceEnum {

    // 科技公司
    CAMERA_SOURCE_YT("1","科技公司"),
    // 楚天
    CAMERA_SOURCE_TL("2","楚天"),
    // 上云
    CAMERA_SOURCE_SY("3","上云");

    private String code;
    private String name;

    CameraDataSourceEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
