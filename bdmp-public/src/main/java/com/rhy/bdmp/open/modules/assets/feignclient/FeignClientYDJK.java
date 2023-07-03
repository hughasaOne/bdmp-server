package com.rhy.bdmp.open.modules.assets.feignclient;

import com.rhy.bcp.common.resutl.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient("ydjk-public")
public interface FeignClientYDJK {
    @PostMapping("/ydjk/public/videoPlays")
    CommonResult getUrlByFacId(@RequestBody Map<String, Object> params);
}
