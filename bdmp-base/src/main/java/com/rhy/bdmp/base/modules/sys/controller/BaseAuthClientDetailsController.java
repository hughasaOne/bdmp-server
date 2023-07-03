package com.rhy.bdmp.base.modules.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.sys.service.IBaseAuthClientDetailsService;
import com.rhy.bdmp.base.modules.sys.domain.po.AuthClientDetails;
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
 * @date 2022-03-23 17:22
 * @version V1.0
 **/
@Api(tags = {"oauth2认证配置管理"})
@Slf4j
//@RestController
//@RequestMapping("/bdmp/auth/auth-client-details/auth-client-details")
public class BaseAuthClientDetailsController {

	@Resource
	private IBaseAuthClientDetailsService baseAuthClientDetailsService;

    @ApiOperation(value = "查询oauth2认证配置", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<AuthClientDetails>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<AuthClientDetails> result = baseAuthClientDetailsService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询oauth2认证配置(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<AuthClientDetails>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<AuthClientDetails> pageUtil =  baseAuthClientDetailsService.page(queryVO);
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
        baseAuthClientDetailsService.create(authClientDetails);
        return RespResult.ok();
    }

    @ApiOperation("修改oauth2认证配置")
    @PutMapping
    public RespResult update(@Validated @RequestBody AuthClientDetails authClientDetails){
        baseAuthClientDetailsService.update(authClientDetails);
        return RespResult.ok();
    }

    @ApiOperation("删除oauth2认证配置")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> ids){
        baseAuthClientDetailsService.delete(ids);
        return RespResult.ok();
    }
}
