package com.rhy.bdmp.open.modules.assets.controller.dir;

import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bdmp.open.modules.assets.domain.bo.AppDirBo;
import com.rhy.bdmp.open.modules.assets.domain.bo.UpdateVPStatusBo;
import com.rhy.bdmp.open.modules.assets.domain.vo.AppDirVo;
import com.rhy.bdmp.open.modules.assets.domain.vo.RequestCameraDirVo;
import com.rhy.bdmp.open.modules.assets.service.IAppService;
import com.rhy.bdmp.open.modules.assets.service.ICameraService;
import com.rhy.bdmp.open.modules.assets.service.IVPService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "资源目录服务")
@RestController
@RequestMapping("/bdmp/public/assets")
@Slf4j
public class DirController {
    @Resource
    private IVPService vpService;

    @Resource
    private ICameraService cameraService;

    @Resource
    private IAppService appService;

    @ApiOperation(value = "更新ip电话状态")
    @PostMapping("/vp/update")
    public RespResult updateVPStatus(@RequestBody List<UpdateVPStatusBo> updateVPStatusBos){
        vpService.updateVPStatus(updateVPStatusBos);
        return RespResult.ok();
    }

    @ApiOperation(value = "IP电话资源目录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orgId", value = "节点id", dataType = "string", required = false),
            @ApiImplicitParam(name = "nodeType", value = "0：集团，1: 运营公司,2: 路段,3: 设施", dataType = "string", required = false),
            @ApiImplicitParam(name = "name", value = "资源名称", dataType = "string", required = false)
    })
    @GetMapping("/getContactTree")
    public RespResult getContactTree(@RequestParam(value = "orgId",required = false) String orgId,@RequestParam(value = "nodeType",required = false) String nodeType,@RequestParam(value = "name",required = false) String name,@RequestParam(value = "exclude",required = false,defaultValue = "0") String exclude){
        return RespResult.ok(vpService.getContactTree(orgId,nodeType,name,exclude));
    }

    @ApiOperation(value = "云台视频资源目录")
    @PostMapping("/getCameraDirYt")
    public RespResult getCameraDirYt(@RequestBody(required = false) RequestCameraDirVo requestCameraDirVo){
        return RespResult.ok(cameraService.getCameraDirYt(requestCameraDirVo));
    }

    @ApiOperation(value = "应用资源目录", position = 48)
    @PostMapping("/app/dir")
    public RespResult<List<AppDirVo>> getAppDir(@Validated @RequestBody(required = false) AppDirBo appDirBo) {
        appDirBo = null == appDirBo ? new AppDirBo() : appDirBo;
        boolean isUseUserPermissions = null == appDirBo.getIsUseUserPermissions() || appDirBo.getIsUseUserPermissions();
        appDirBo.setIsUseUserPermissions(isUseUserPermissions);
        return RespResult.ok(appService.getAppDir(appDirBo));
    }
}
