package com.rhy.bdmp.collect.modules.vp.controller;

import com.rhy.bdmp.collect.modules.vp.service.impl.VPTask;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "ip电话采集")
@RestController
@RequestMapping("/bdmp/collect/vp")
public class VPController {
    @Resource
    private VPTask vpTask;

    @PostMapping("/sync")
    public void sync(){
        vpTask.collectContact();
    }
}
