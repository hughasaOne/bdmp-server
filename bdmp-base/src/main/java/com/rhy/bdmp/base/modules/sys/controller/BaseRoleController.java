package com.rhy.bdmp.base.modules.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.sys.service.IBaseRoleService;
import com.rhy.bdmp.base.modules.sys.domain.po.Role;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * @description 角色 前端控制器
 * @author yanggj
 * @date 2021-09-24 16:32
 * @version V1.0
 **/
@Api(tags = {"角色管理"})
@Slf4j
//@RestController
//@RequestMapping("/bdmp/system/sys/role")
public class BaseRoleController {

	@Resource
	private IBaseRoleService baseRoleService;

    @ApiOperation(value = "查询角色", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<Role>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<Role> result = baseRoleService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询角色(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<Role>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<Role> pageUtil =  baseRoleService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看角色(根据ID)")
    @GetMapping(value = "/{roleId}")
    public RespResult<Role> detail(@PathVariable("roleId") String roleId) {
        Role role = baseRoleService.detail(roleId);
        return RespResult.ok(role);
    }

    @ApiOperation("新增角色")
    @PostMapping
    public RespResult create(@Validated @RequestBody Role role){
        baseRoleService.create(role);
        return RespResult.ok();
    }

    @ApiOperation("修改角色")
    @PutMapping
    public RespResult update(@Validated @RequestBody Role role){
        baseRoleService.update(role);
        return RespResult.ok();
    }

    @ApiOperation("删除角色")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> roleIds){
        baseRoleService.delete(roleIds);
        return RespResult.ok();
    }
}
