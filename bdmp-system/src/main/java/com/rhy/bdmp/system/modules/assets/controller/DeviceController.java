package com.rhy.bdmp.system.modules.assets.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.domain.vo.NodeVo;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.PageUtils;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.domain.po.Device;
import com.rhy.bdmp.system.modules.assets.domain.bo.RequiredFieldsBo;
import com.rhy.bdmp.system.modules.assets.domain.vo.DeviceGridVo;
import com.rhy.bdmp.system.modules.assets.domain.vo.DeviceVo;
import com.rhy.bdmp.system.modules.assets.domain.vo.ExcelBoxVo;
import com.rhy.bdmp.system.modules.assets.domain.vo.UploadBoxEcelVo;
import com.rhy.bdmp.system.modules.assets.handler.DownloadExcelBoxHandler;
import com.rhy.bdmp.system.modules.assets.handler.ReadBoxExcelHandler;
import com.rhy.bdmp.system.modules.assets.service.IDeviceService;
import com.rhy.bdmp.system.modules.logging.enums.LogTypeEnum;
import com.rhy.bdmp.system.modules.logging.util.LogUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @auther xiabei
 * @Description 设备 前端控制器
 * @date 2021/4/14
 */
@Api(tags = {"设备管理"})
@Slf4j
@RestController
@RequestMapping("/bdmp/system/assets/device")
public class DeviceController  {

    @Resource
    private IDeviceService deviceService;

    @ApiOperation("校验字段")
    @PostMapping(value = "/checkRequiredFields")
    public RespResult checkRequiredFields(@RequestBody RequiredFieldsBo requiredFieldsBo) {
        return RespResult.ok(deviceService.checkRequiredFields(requiredFieldsBo));
    }

    @ApiOperation("查询设备(分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "当前页", dataType = "int", paramType = "query", example = "1"),
            @ApiImplicitParam(name = "size", value = "页大小", dataType = "int", paramType = "query", example = "20"),
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否使用用户权限过滤", dataType = "boolean", paramType = "query", example = "true"),
            @ApiImplicitParam(name = "nodeType", value = "节点类型（org,way,fac）", dataType = "string", paramType = "query", example = ""),
            @ApiImplicitParam(name = "nodeId", value = "节点ID", dataType = "string", paramType = "query", example = ""),
            @ApiImplicitParam(name = "deviceType", value = "设备类型", dataType = "string", paramType = "query", example = ""),
            @ApiImplicitParam(name = "systemId", value = "系统ID", dataType = "string", paramType = "query", example = ""),
            @ApiImplicitParam(name = "deviceName", value = "设备名称", dataType = "string", paramType = "query", example = ""),
            @ApiImplicitParam(name = "deviceCode", value = "设备代码", dataType = "string", paramType = "query", example = "")
    })
    @GetMapping(value = "/queryPage")
    public RespResult queryPage( @RequestParam(required = false)Integer currentPage,
                                 @RequestParam(required = false)Integer size,
                                 @RequestParam(required = false) Boolean isUseUserPermissions,
                                 @RequestParam(required = false) String nodeType,
                                 @RequestParam(required = false) String nodeId,
                                 @RequestParam(required = false) String deviceType,
                                 @RequestParam(required = false) String systemId,
                                 @RequestParam(required = false) String deviceName,
                                 @RequestParam(required = false) String deviceCode) {
        isUseUserPermissions = null == isUseUserPermissions ? true : isUseUserPermissions;
        Page<DeviceGridVo> page = deviceService.queryPage(currentPage, size, isUseUserPermissions, nodeType, nodeId, deviceType, systemId, deviceName,deviceCode);
        return RespResult.ok(new PageUtils(page));
    }

    @ApiOperation(value = "查询设备(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<Device>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<Device> pageUtil =  deviceService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation("查询没有设施Id的设备")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "当前页", dataType = "int", paramType = "query", example = "1"),
            @ApiImplicitParam(name = "size", value = "页大小", dataType = "int", paramType = "query", example = "20"),
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否使用用户权限过滤", dataType = "boolean", paramType = "query", example = "true"),
    })
    @GetMapping(value = "/getDeviceNoFacId")
    public RespResult getDeviceNoFacId( @RequestParam(required = false)Integer currentPage,  @RequestParam(required = false)Integer size, @RequestParam(required = false) Boolean isUseUserPermissions) {
        isUseUserPermissions = null == isUseUserPermissions ? true : isUseUserPermissions;
        Page<DeviceGridVo> page = deviceService.getDeviceNoFacId(currentPage, size, isUseUserPermissions);
        return RespResult.ok(new PageUtils(page));
    }

    @ApiOperation(value = "查看设备(多表根据ID)")
    @GetMapping(value = "/{deviceId}")
    public RespResult detail(@PathVariable("deviceId") String deviceId) throws Exception {
        DeviceVo deviceVo = deviceService.detail(deviceId);
        return RespResult.ok(deviceVo);
    }

    @ApiOperation("新增设备(多表)")
    @PostMapping(value = "/save")
    public RespResult create(@Validated @RequestBody DeviceVo deviceVo) throws Exception {
        deviceService.create(deviceVo);
        log.info(LogUtil.buildUpParams("新增设备", LogTypeEnum.OPERATE.getCode(),deviceVo.getDeviceId()));
        return RespResult.ok();
    }

    @ApiOperation("修改设备(多表)")
    @PutMapping(value = "/update")
    public RespResult update(@Validated @RequestBody DeviceVo deviceVo) throws Exception {
        deviceService.update(deviceVo);
        log.info(LogUtil.buildUpParams("修改设备", LogTypeEnum.OPERATE.getCode(),deviceVo.getDeviceId()));
        return RespResult.ok();
    }

    @ApiOperation("删除设备(多表)")
    @DeleteMapping(value = "/delete")
    public RespResult delete(@RequestBody Set<String> deviceIds) {
        deviceService.delete(deviceIds);
        log.info(LogUtil.buildUpParams("删除设备", LogTypeEnum.OPERATE.getCode(),deviceIds));
        return RespResult.ok();
    }

    @ApiOperation(value = "根据设施id查找设备管理组织树")
    @GetMapping(value = "/deviceManageTree/{facilitiesId}")
    public RespResult deviceManageTree(@PathVariable("facilitiesId") String facilitiesId){
        List<NodeVo> result = deviceService.findManageDepartment(facilitiesId);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "根据模板导入终端箱")
    @PostMapping(value = "/uploadBoxExcel")
    public RespResult uploadDevice(NodeVo nodeVo,@RequestParam(value = "file", required = true) MultipartFile file){
        String res = "";
        try {
            List<UploadBoxEcelVo> uploadBoxEcelVolist = EasyExcel.read(file.getInputStream(), UploadBoxEcelVo.class, new ReadBoxExcelHandler()).sheet().doReadSync();
            uploadBoxEcelVolist.remove(0);
            res = deviceService.saveBatchBox(nodeVo, uploadBoxEcelVolist);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return RespResult.ok(res);
    }

    @ApiOperation(value = "终端箱模板下载")
    @GetMapping(value = "/downloadBoxExcel")
    public void downloadBoxExcel(HttpServletResponse response){
        // 模板初始化
        ExcelBoxVo excelBoxVo = new ExcelBoxVo();
        List<ExcelBoxVo> excelBoxVoList = new ArrayList<ExcelBoxVo>();
        excelBoxVoList.add(excelBoxVo);

        //设置下载文件时显示的名字
        String fileName = "终端箱模板";
        String excelName = "终端箱模板";
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        try {
            //设置表格名字
            fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new BadRequestException("中文乱码异常");
        }
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        try {
            log.info(LogUtil.buildUpParams("下载终端箱模板",LogTypeEnum.GET_RESOURCE.getCode(), null));
            EasyExcel.write(response.getOutputStream(), ExcelBoxVo.class)
                    .registerWriteHandler(new DownloadExcelBoxHandler())
                    //从第九行开始，第0行为提示
                    .useDefaultStyle(true).relativeHeadRowIndex(4)
                    .sheet(excelName).doWrite(excelBoxVoList);
        } catch (IOException e) {
            throw new BadRequestException("导出excelIO异常");
        }
    }
}
