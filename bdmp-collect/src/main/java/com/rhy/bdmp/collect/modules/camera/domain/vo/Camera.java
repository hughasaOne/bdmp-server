package com.rhy.bdmp.collect.modules.camera.domain.vo;

import lombok.Data;

@Data
public class Camera {
    private long cameraId;
    private String cameraName;
    private String cameraIp;
    private int deviceType;
    private String latitude;
    private String longitude;
    private String onlineStatus;
    private String uuid;
}
