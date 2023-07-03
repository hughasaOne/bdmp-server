package com.rhy.bdmp.base.modules.assets.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.service.IBaseFacilitiesTunnelService;
import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesTunnel;
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
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Api(tags = {"管理"})
@Slf4j
//@RestController
//@RequestMapping("//bdmp/system/assets/facilities-tunnel")
public class BaseFacilitiesTunnelController {

	@Resource
	private IBaseFacilitiesTunnelService baseFacilitiesTunnelService;

    @ApiOperation(value = "查询", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<FacilitiesTunnel>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<FacilitiesTunnel> result = baseFacilitiesTunnelService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<FacilitiesTunnel>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<FacilitiesTunnel> pageUtil =  baseFacilitiesTunnelService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看(根据ID)")
    @GetMapping(value = "/{tunnelId}")
    public RespResult<FacilitiesTunnel> detail(@PathVariable("tunnelId") String tunnelId) {
        FacilitiesTunnel facilitiesTunnel = baseFacilitiesTunnelService.detail(tunnelId);
        return RespResult.ok(facilitiesTunnel);
    }

    @ApiOperation("新增")
    @PostMapping
    public RespResult create(@Validated @RequestBody FacilitiesTunnel facilitiesTunnel){
        baseFacilitiesTunnelService.create(facilitiesTunnel);
        return RespResult.ok();
    }

    @ApiOperation("修改")
    @PutMapping
    public RespResult update(@Validated @RequestBody FacilitiesTunnel facilitiesTunnel){
        baseFacilitiesTunnelService.update(facilitiesTunnel);
        return RespResult.ok();
    }

    @ApiOperation("删除")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> tunnelIds){
        baseFacilitiesTunnelService.delete(tunnelIds);
        return RespResult.ok();
    }
}
