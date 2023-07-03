package com.rhy.bdmp.collect.modules.camera.service.impl;

import com.rhy.bdmp.BdmpCollectApplication;
import com.rhy.bdmp.collect.modules.camera.service.ICameraService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = BdmpCollectApplication.class)
class CameraServiceImplTest {

    @Resource
    private ICameraService cameraService;

    @Test
    void syncCameraByYt() {
        cameraService.syncCameraByYt();
    }

    @Test
    void syncCameraByTl() {
        cameraService.syncCameraByTl();
    }

    @Test
    void author(){
        Map<String,String> param = new HashMap<>();
        param.put("client_id","lwjc");
        param.put("client_secret","lwjc");
        param.put("grant_type","password");
        param.put("username","jt_yyjt");
        param.put("password","jt_yyjt");
    }
}