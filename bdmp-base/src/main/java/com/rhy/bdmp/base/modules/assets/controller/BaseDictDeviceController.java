package com.rhy.bdmp.base.modules.assets.controller;

import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.domain.po.DictDevice;
import com.rhy.bdmp.base.modules.assets.service.IBaseDictDeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * @description 设备字典 前端控制器
 * @author yanggj
 * @date 2021-10-26 09:05
 * @version V1.0
 **/
@Api(tags = {"设备字典管理"})
@Slf4j
//@RestController
//@RequestMapping("//bdmp/system/assets/device-dict")
public class BaseDictDeviceController {

	@Resource
	private IBaseDictDeviceService baseDeviceDictService;

    @ApiOperation(value = "查询设备字典", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<DictDevice>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<DictDevice> result = baseDeviceDictService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询设备字典(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<DictDevice>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<DictDevice> pageUtil =  baseDeviceDictService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看设备字典(根据ID)")
    @GetMapping(value = "/{deviceDictId}")
    public RespResult<DictDevice> detail(@PathVariable("deviceDictId") String deviceDictId) {
        DictDevice deviceDict = baseDeviceDictService.detail(deviceDictId);
        return RespResult.ok(deviceDict);
    }

    @ApiOperation("新增设备字典")
    @PostMapping
    public RespResult create(@Validated @RequestBody DictDevice deviceDict){
        baseDeviceDictService.create(deviceDict);
        return RespResult.ok();
    }

    @ApiOperation("修改设备字典")
    @PutMapping
    public RespResult update(@Validated @RequestBody DictDevice deviceDict){
        baseDeviceDictService.update(deviceDict);
        return RespResult.ok();
    }

    @ApiOperation("删除设备字典")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> deviceDictIds){
        baseDeviceDictService.delete(deviceDictIds);
        return RespResult.ok();
    }
}
