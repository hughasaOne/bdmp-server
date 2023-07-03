package com.rhy.bdmp.open.modules.pts.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtils;
import com.rhy.bdmp.open.modules.pts.service.IPtsAssetsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: yanggj
 * @Description: 公众出行服务-台账
 * @Date: 2021/12/15 14:38
 * @Version: 1.0.0
 */
@Api(tags = "公众出行服务-台账")
@Slf4j
@RestController
@RequestMapping("/bdmp/public/assets")
public class PtsAssetsController {

    private final IPtsAssetsService pstAssetsService;

    public PtsAssetsController(IPtsAssetsService pstAssetsService) {
        this.pstAssetsService = pstAssetsService;
    }

    @ApiOperation(value = "2.90.4 路段列表", position = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "当前页", dataType = "int", paramType = "query", required = false, example = "1"),
            @ApiImplicitParam(name = "size", value = "页大小", dataType = "int", paramType = "query", required = false, example = "20"),
    })
    @PostMapping("/waysection")
    public RespResult<PageUtils> queryPageWaySection(@RequestParam(required = false) Integer currentPage,
                                                     @RequestParam(required = false) Integer size) {

        Page<?> page = pstAssetsService.queryPageWaySection(currentPage, size);
        return RespResult.ok(new PageUtils(page));
    }

    @ApiOperation(value = "2.90.5 服务区列表", position = 2)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "当前页", dataType = "int", paramType = "query", required = false, example = "1"),
            @ApiImplicitParam(name = "size", value = "页大小", dataType = "int", paramType = "query", required = false, example = "20"),
    })
    @PostMapping("/serviceArea")
    public RespResult<PageUtils> queryPageServiceArea(@RequestParam(required = false) Integer currentPage,
                                                      @RequestParam(required = false) Integer size) {

        Page<?> page = pstAssetsService.queryPageServiceArea(currentPage, size);
        return RespResult.ok(new PageUtils(page));
    }

    @ApiOperation(value = "2.90.6 收费站列表", position = 3)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "当前页", dataType = "int", paramType = "query", required = false, example = "1"),
            @ApiImplicitParam(name = "size", value = "页大小", dataType = "int", paramType = "query", required = false, example = "20"),
    })
    @PostMapping("/tollStation")
    public RespResult<PageUtils> queryPageTollStation(@RequestParam(required = false) Integer currentPage,
                                                      @RequestParam(required = false) Integer size) {

        Page<?> page = pstAssetsService.queryPageTollStation(currentPage, size);
        return RespResult.ok(new PageUtils(page));
    }


}