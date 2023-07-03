package com.rhy.bdmp.open.modules.fac.controller;

import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bdmp.open.modules.common.domain.bo.CommonBo;
import com.rhy.bdmp.open.modules.fac.domain.bo.FacDetailBo;
import com.rhy.bdmp.open.modules.fac.service.IFacService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author weicaifu
 */
@Api(tags = "设施服务")
@RestController
@RequestMapping("/bdmp/public/fac")
@Component(value = "FacControllerV1")
public class FacController {

    @Resource
    private IFacService facService;

    @ApiOperation(value = "设施详情")
    @PostMapping("/detail/v1")
    public RespResult getFacDetail(@RequestBody FacDetailBo facDetailBo){
        return RespResult.ok(facService.getFacDetail(facDetailBo));
    }

    @ApiOperation(value = "设施列表")
    @PostMapping("/list/v1")
    public RespResult getFacList(@RequestBody CommonBo commonBo){
        return RespResult.ok(facService.getFacList(commonBo));
    }
}
