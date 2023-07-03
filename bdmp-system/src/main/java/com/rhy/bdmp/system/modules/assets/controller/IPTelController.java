package com.rhy.bdmp.system.modules.assets.controller;

import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bdmp.base.modules.assets.domain.po.VpResource;
import com.rhy.bdmp.system.modules.assets.domain.bo.IPTelListBo;
import com.rhy.bdmp.system.modules.assets.service.IIPTelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;

@Api(tags = {"ip电话管理"})
@RestController
@RequestMapping("/bdmp/system/assets/ipTel")
public class IPTelController {

    @Resource
    private IIPTelService ipTelService;

    @ApiOperation("获取目录")
    @GetMapping("/dir/tree")
    public RespResult getDir(){
        return RespResult.ok(ipTelService.getDir());
    }

    @ApiOperation("获取资源列表")
    @PostMapping("/resource")
    public RespResult getResourceList(@RequestBody IPTelListBo ipTelListBo){
        return RespResult.ok(ipTelService.getResourceList(ipTelListBo));
    }

    @ApiOperation("详情")
    @GetMapping("/detail")
    public RespResult detail(@RequestParam String id){
        return RespResult.ok(ipTelService.detail(id));
    }

    @ApiOperation("新增")
    @PostMapping("/add")
    public RespResult add(@RequestBody VpResource vpResource){
        ipTelService.add(vpResource);
        return RespResult.ok();
    }

    @ApiOperation("修改")
    @PutMapping("/update")
    public RespResult update(@RequestBody VpResource vpResource){
        ipTelService.update(vpResource);
        return RespResult.ok();
    }

    @ApiOperation("删除")
    @PutMapping("/delete")
    public RespResult delete(@RequestBody Set<String> ids){
        ipTelService.delete(ids);
        return RespResult.ok();
    }
}
