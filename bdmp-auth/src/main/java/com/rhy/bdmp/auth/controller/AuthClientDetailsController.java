package com.rhy.bdmp.auth.controller;

import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bdmp.auth.service.IAuthClientDetailsService;
import com.rhy.bdmp.base.modules.sys.domain.po.AuthClientDetails;
import com.rhy.bdmp.base.modules.sys.service.IBaseAuthClientDetailsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * @description oauth2认证配置 前端控制器
 * @author shuaichao
 * @date 2022-03-23 11:18
 * @version V1.0
 **/
@Api(tags = {"oauth2认证配置管理"})
@Slf4j
@RestController
@RequestMapping("/oauth/auth-client-details")
public class AuthClientDetailsController {

	@Resource
	private IBaseAuthClientDetailsService baseAuthClientDetailsService;

	@Resource
    private IAuthClientDetailsService authClientDetailsService;

    @ApiOperation(value = "查询oauth2认证配置", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<AuthClientDetails>> list(@RequestBody(required = false) AuthClientDetails authClientDetails) {
        List<AuthClientDetails> result = authClientDetailsService.list(authClientDetails);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询oauth2认证配置(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page/{currentPage}/{size}")
    public RespResult<PageUtil<AuthClientDetails>> page(@RequestBody(required = false) AuthClientDetails authClientDetails,@PathVariable Integer currentPage,@PathVariable Integer size) {
        PageUtil<AuthClientDetails> pageUtil =  authClientDetailsService.page(authClientDetails,currentPage,size);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看oauth2认证配置(根据ID)")
    @GetMapping(value = "/{id}")
    public RespResult<AuthClientDetails> detail(@PathVariable("id") String id) {
        AuthClientDetails authClientDetails = baseAuthClientDetailsService.detail(id);
        return RespResult.ok(authClientDetails);
    }

    @ApiOperation("新增oauth2认证配置")
    @PostMapping
    public RespResult create(@Validated @RequestBody AuthClientDetails authClientDetails){
        authClientDetailsService.create(authClientDetails);
        return RespResult.ok();
    }

    @ApiOperation("修改oauth2认证配置")
    @PutMapping
    public RespResult update(@Validated @RequestBody AuthClientDetails authClientDetails){
        authClientDetailsService.update(authClientDetails);
        return RespResult.ok();
    }

    @ApiOperation("删除oauth2认证配置")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> ids){
        baseAuthClientDetailsService.delete(ids);
        return RespResult.ok();
    }
}
