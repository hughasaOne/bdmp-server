package com.rhy.bdmp.collect.modules.jdxt.service.impl;

import com.rhy.bdmp.BdmpCollectApplication;
import com.rhy.bdmp.collect.modules.jdxt.service.IJdxtService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = BdmpCollectApplication.class)
public class JdxtServiceImplTest {

    @Resource
    private IJdxtService jdxtService;

    /**
     * 同步数据字典
     * @return
     */
    @Test
    void syncDict() {
        jdxtService.syncDict();
    }

    /**
     *  同步运营公司信息
     * @return
     */
    @Test
    void syncOperatingCompany() {
        jdxtService.syncOperatingCompany();
    }

    /**
     *  同步路段信息
     * @return
     */
    @Test
    void syncWaySection() {
        jdxtService.syncWaySection();
    }

    /**
     *  同步设施信息
     * @return
     */
    @Test
    void syncFacilities() {
        jdxtService.syncFacilities();
    }

    /**
     *  同步设备信息
     * @return
     */
    @Test
    void syncDevice(){
        jdxtService.syncDevice();
    }

    /**
     *  同步视频资源（腾路）
     * @return
     */
    @Test
    void syncVideoResourceTl(){
        jdxtService.syncVideoResourceTl();
    }


}