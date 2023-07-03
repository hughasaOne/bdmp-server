package com.rhy.bdmp.base.modules.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.sys.service.IBaseOrgService;
import com.rhy.bdmp.base.modules.sys.domain.po.Org;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * @description 组织机构 前端控制器
 * @author yanggj
 * @date 2021-09-24 16:32
 * @version V1.0
 **/
@Api(tags = {"组织机构管理"})
@Slf4j
//@RestController
//@RequestMapping("/bdmp/system/sys/org")
public class BaseOrgController {

	@Resource
	private IBaseOrgService baseOrgService;

    @ApiOperation(value = "查询组织机构", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<Org>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<Org> result = baseOrgService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询组织机构(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<Org>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<Org> pageUtil =  baseOrgService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看组织机构(根据ID)")
    @GetMapping(value = "/{orgId}")
    public RespResult<Org> detail(@PathVariable("orgId") String orgId) {
        Org org = baseOrgService.detail(orgId);
        return RespResult.ok(org);
    }

    @ApiOperation("新增组织机构")
    @PostMapping
    public RespResult create(@Validated @RequestBody Org org){
        baseOrgService.create(org);
        return RespResult.ok();
    }

    @ApiOperation("修改组织机构")
    @PutMapping
    public RespResult update(@Validated @RequestBody Org org){
        baseOrgService.update(org);
        return RespResult.ok();
    }

    @ApiOperation("删除组织机构")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> orgIds){
        baseOrgService.delete(orgIds);
        return RespResult.ok();
    }
}
