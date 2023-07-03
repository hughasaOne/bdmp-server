package com.rhy.bdmp.base.modules.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.sys.service.IBaseUserService;
import com.rhy.bdmp.base.modules.sys.domain.po.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * @description 用户 前端控制器
 * @author yanggj
 * @date 2021-09-24 16:32
 * @version V1.0
 **/
@Api(tags = {"用户管理"})
@Slf4j
//@RestController
//@RequestMapping("/bdmp/system/sys/user")
public class BaseUserController {

	@Resource
	private IBaseUserService baseUserService;

    @ApiOperation(value = "查询用户", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<User>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<User> result = baseUserService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询用户(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<User>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<User> pageUtil =  baseUserService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看用户(根据ID)")
    @GetMapping(value = "/{userId}")
    public RespResult<User> detail(@PathVariable("userId") String userId) {
        User user = baseUserService.detail(userId);
        return RespResult.ok(user);
    }

    @ApiOperation("新增用户")
    @PostMapping
    public RespResult create(@Validated @RequestBody User user){
        baseUserService.create(user);
        return RespResult.ok();
    }

    @ApiOperation("修改用户")
    @PutMapping
    public RespResult update(@Validated @RequestBody User user){
        baseUserService.update(user);
        return RespResult.ok();
    }

    @ApiOperation("删除用户")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> userIds){
        baseUserService.delete(userIds);
        return RespResult.ok();
    }
}
