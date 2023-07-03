package com.rhy.bdmp.base.modules.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.sys.service.IBaseUserMappingService;
import com.rhy.bdmp.base.modules.sys.domain.po.UserMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * @description 用户映射 前端控制器
 * @author yanggj
 * @date 2021-09-24 16:32
 * @version V1.0
 **/
@Api(tags = {"用户映射管理"})
@Slf4j
//@RestController
//@RequestMapping("/bdmp/system/sys/user-mapping")
public class BaseUserMappingController {

	@Resource
	private IBaseUserMappingService baseUserMappingService;

    @ApiOperation(value = "查询用户映射", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<UserMapping>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<UserMapping> result = baseUserMappingService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询用户映射(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<UserMapping>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<UserMapping> pageUtil =  baseUserMappingService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看用户映射(根据ID)")
    @GetMapping(value = "/{userMappingId}")
    public RespResult<UserMapping> detail(@PathVariable("userMappingId") String userMappingId) {
        UserMapping userMapping = baseUserMappingService.detail(userMappingId);
        return RespResult.ok(userMapping);
    }

    @ApiOperation("新增用户映射")
    @PostMapping
    public RespResult create(@Validated @RequestBody UserMapping userMapping){
        baseUserMappingService.create(userMapping);
        return RespResult.ok();
    }

    @ApiOperation("修改用户映射")
    @PutMapping
    public RespResult update(@Validated @RequestBody UserMapping userMapping){
        baseUserMappingService.update(userMapping);
        return RespResult.ok();
    }

    @ApiOperation("删除用户映射")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> userMappingIds){
        baseUserMappingService.delete(userMappingIds);
        return RespResult.ok();
    }
}
