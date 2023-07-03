package com.rhy.bdmp.system.modules.analysis.controller;

import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bdmp.system.modules.analysis.domain.vo.RanKOperatingCompanyVO;
import com.rhy.bdmp.system.modules.analysis.service.AnalysisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;



/**
 * @ClassName: AnalysisController
 * @Description: 台账资源统计
 * @Author: 魏财富
 * @Date: 2021/4/15 9:57
 */
@Api(tags = "统计分析")
@RestController
@RequestMapping("/bdmp/system/analysis")
public class AnalysisController {
    @Resource
    private AnalysisService analysisService;

    @ApiOperation("统计系统资源总数")
    @GetMapping("/statSysResourcesNum")
    public RespResult statSysResourcesNum(){
        Map<String,Object> result = analysisService.statSysResourcesNum();
        return RespResult.ok(result);
    }

    @ApiOperation("系统应用人员分布统计")
    @GetMapping("/statUserOfApp")
    public RespResult statUserOfApp(){
        List<Map<String,Object>> result = analysisService.statUserOfApp();
        return RespResult.ok(result);
    }

    @ApiOperation("机构人员分布图")
    @GetMapping("/statUserOrgStructure")
    public RespResult statUserOrgStructure(){
        List<Map<String,Object>> result = analysisService.statUserOrgStructure();
        return RespResult.ok(result);
    }

    @ApiOperation("统计台账资源总数")
    @GetMapping("/statAssetsResourcesNum")
    public RespResult statAssetsResourcesNum(){
        Map<String,Object> result = analysisService.countAssetsSum();
        return RespResult.ok(result);
    }

    @ApiOperation("运营公司排名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rankType", value = "排名大类", dataType = "String", paramType = "query", required = true, example = "1:路段、里程数,2:重要设施,3:设备系统"),
            @ApiImplicitParam(name = "typeIds", value = "排名小类ID集", dataType = "set", paramType = "query", required = false, example = "1:{1:路段数,2里程数} 2:{120100:桥梁,120200:隧道,120300:收费站,120400:服务区,120500:门架,120600:互通立交} 3:{110100:监控系统,110200:收费系统,110300:通信系统,110400:供配电系统,110500:隧道机电系统110600:隧道消防系统110700:隧道通风系统,110800:隧道照明系统,110900:门架系统}")
    })
    @PostMapping("/operationCompanyRank")
    public RespResult ranKOperatingCompany(@RequestBody(required = false) Set<String> typeIds,@RequestParam(required = true) String rankType){
        Map<String, Map<String, List<RanKOperatingCompanyVO>>> result= analysisService.rankOperatingCompany(rankType, typeIds);
        return RespResult.ok(result);
    }

    @ApiOperation("获取系统类型")
    @GetMapping("/getSysType")
    public RespResult getSysType(){
        return RespResult.ok(analysisService.getSysType());
    }
}
