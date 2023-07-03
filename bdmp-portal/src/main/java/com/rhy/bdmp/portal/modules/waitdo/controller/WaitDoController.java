package com.rhy.bdmp.portal.modules.waitdo.controller;

import com.rhy.bcp.common.resutl.CommonResult;
import com.rhy.bdmp.portal.modules.waitdo.domain.vo.WaitDo;
import com.rhy.bdmp.portal.modules.waitdo.service.IWaitDoService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
* @description 门户待办
* @author jiangzhimin
* @date 2021-01-27 09:21
* @version V1.0
**/
@Api(tags = {"用户待办查询"})
@Slf4j
@RestController
@RequestMapping("/bdmp/portal/waitdo")
public class WaitDoController {

    @Resource
    private IWaitDoService waitDoService;

    @ApiOperation(value = "查询用户待办", notes = "查询用户待办")
    @ApiResponses({
            @ApiResponse(code = 200,message = "成功",response = WaitDo.class),
    })
    @PostMapping(value = "/list")
    public CommonResult list(@RequestBody(required = false) Set<String> appIds) {
        Map<String, List<WaitDo>> dataMap =  waitDoService.findWaitDoList(appIds);
        return CommonResult.ok().put("data", dataMap);
    }

}