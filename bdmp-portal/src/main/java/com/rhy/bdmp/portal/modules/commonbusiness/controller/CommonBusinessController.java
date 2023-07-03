package com.rhy.bdmp.portal.modules.commonbusiness.controller;

import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.sys.domain.po.CommonBusiness;
import com.rhy.bdmp.portal.modules.commonbusiness.service.CommonBusinessService;
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
@Api(tags = {"常用业务管理"})
@Slf4j
@RestController
@RequestMapping("/bdmp/portal/common-business")
public class CommonBusinessController {

	@Resource
	private CommonBusinessService commonBusinessService;

    @ApiOperation(value = "本人常用业务查询", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<CommonBusiness>> list() {
        List<CommonBusiness> result = commonBusinessService.list();
        return RespResult.ok(result);
    }


    @ApiOperation(value = "查看(根据ID)")
    @GetMapping(value = "/{commonBusinessId}")
    public RespResult<CommonBusiness> detail(@PathVariable("commonBusinessId") String commonBusinessId) {
        CommonBusiness commonBusiness = commonBusinessService.detail(commonBusinessId);
        return RespResult.ok(commonBusiness);
    }

    @ApiOperation("新增")
    @PostMapping
    public RespResult create(@Validated @RequestBody CommonBusiness commonBusiness){
        commonBusinessService.create(commonBusiness);
        return RespResult.ok();
    }

    @ApiOperation("修改")
    @PutMapping
    public RespResult update(@Validated @RequestBody CommonBusiness commonBusiness){
        commonBusinessService.update(commonBusiness);
        return RespResult.ok();
    }

    @ApiOperation("删除")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> commonBusinessIds){
        commonBusinessService.delete(commonBusinessIds);
        return RespResult.ok();
    }
}
