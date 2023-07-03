package com.rhy.bdmp.base.modules.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.sys.service.IBaseCommonBusinessService;
import com.rhy.bdmp.base.modules.sys.domain.po.CommonBusiness;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * @description  前端控制器
 * @author shuaichao
 * @date 2022-03-03 09:54
 * @version V1.0
 **/
@Api(tags = {"管理"})
@Slf4j
//@RestController
//@RequestMapping("/bdmp/protal/commonbusiness/common-business")
public class BaseCommonBusinessController {

	@Resource
	private IBaseCommonBusinessService baseCommonBusinessService;

    @ApiOperation(value = "查询", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<CommonBusiness>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<CommonBusiness> result = baseCommonBusinessService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<CommonBusiness>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<CommonBusiness> pageUtil =  baseCommonBusinessService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看(根据ID)")
    @GetMapping(value = "/{commonBusinessId}")
    public RespResult<CommonBusiness> detail(@PathVariable("commonBusinessId") String commonBusinessId) {
        CommonBusiness commonBusiness = baseCommonBusinessService.detail(commonBusinessId);
        return RespResult.ok(commonBusiness);
    }

    @ApiOperation("新增")
    @PostMapping
    public RespResult create(@Validated @RequestBody CommonBusiness commonBusiness){
        baseCommonBusinessService.create(commonBusiness);
        return RespResult.ok();
    }

    @ApiOperation("修改")
    @PutMapping
    public RespResult update(@Validated @RequestBody CommonBusiness commonBusiness){
        baseCommonBusinessService.update(commonBusiness);
        return RespResult.ok();
    }

    @ApiOperation("删除")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> commonBusinessIds){
        baseCommonBusinessService.delete(commonBusinessIds);
        return RespResult.ok();
    }
}
