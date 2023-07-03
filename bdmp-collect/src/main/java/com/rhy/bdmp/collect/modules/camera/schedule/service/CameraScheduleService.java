package com.rhy.bdmp.collect.modules.camera.schedule.service;

import com.rhy.bdmp.collect.modules.camera.service.ICameraService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
* @description
* @author jiangzhimin
* @date 2021-11-30 15:35
* @version V1.0
**/
@Component
public class CameraScheduleService {

    @Resource
    private ICameraService cameraService;

    @Scheduled(cron = "0 0 0/1 * * ?")
    public void sendCameraStatus(){
        cameraService.cameraTree(true, "");
    }

}