package com.rhy.bdmp.collect.modules.camera.service;

import feign.QueryMap;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.URI;
import java.util.Map;

@FeignClient(url = "http://106.52.117.168:8998", name = "VideoComprehensiveManageFeignClient")
public interface VideoComprehensiveManageFeignClient {

    @PostMapping("api/video/getCameraList")
    String getCameraList(URI url,@QueryMap Map<String, Object> params);

}