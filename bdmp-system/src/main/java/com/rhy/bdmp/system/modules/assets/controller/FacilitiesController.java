package com.rhy.bdmp.system.modules.assets.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.PageUtils;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.domain.po.Facilities;
import com.rhy.bdmp.base.modules.assets.service.IBaseFacilitiesService;
import com.rhy.bdmp.system.modules.assets.domain.vo.FacilitiesInfoVo;
import com.rhy.bdmp.system.modules.assets.domain.vo.FacilitiesVo;
import com.rhy.bdmp.system.modules.assets.service.IFacilitiesService;
import com.rhy.bdmp.system.modules.logging.enums.LogTypeEnum;
import com.rhy.bdmp.system.modules.logging.util.LogUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @auther xiabei
 * @Description 路段设施 前端控制器
 * @date 2021/4/14
 */
@Api(tags = {"路段设施管理"})
@Slf4j
@RestController
@RequestMapping("/bdmp/system/assets/facilities")
public class FacilitiesController {

    @Resource
    private IFacilitiesService facilitiesService;

    @Resource
    private IBaseFacilitiesService baseFacilitiesService;

    @ApiOperation("查询路段设施(分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "当前页", dataType = "int", paramType = "query", example = "1"),
            @ApiImplicitParam(name = "size", value = "页大小", dataType = "int", paramType = "query", example = "20"),
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否使用用户权限过滤", dataType = "boolean", paramType = "query", example = "true"),
            @ApiImplicitParam(name = "nodeType", value = "节点类型（org,way,fac）", dataType = "string", paramType = "query", example = ""),
            @ApiImplicitParam(name = "nodeId", value = "节点ID", dataType = "string", paramType = "query", example = ""),
            @ApiImplicitParam(name = "facilitiesType", value = "设施类型", dataType = "string", paramType = "query", example = ""),
            @ApiImplicitParam(name = "facilitiesName", value = "设施名称", dataType = "string", paramType = "query", example = ""),
            @ApiImplicitParam(name = "isShowChildren", value = "是否获取子设施", dataType = "boolean", paramType = "query", example = "true"),
            @ApiImplicitParam(name = "parentId", value = "父节点id", dataType = "string", paramType = "query", example = "false")
    })
    @GetMapping(value = "/queryPage")
    public RespResult queryPage( @RequestParam(required = false)Integer currentPage,
                                 @RequestParam(required = false)Integer size,
                                 @RequestParam(required = false) Boolean isUseUserPermissions,
                                 @RequestParam(required = false) String nodeType,
                                 @RequestParam(required = false) String nodeId,
                                 @RequestParam(required = false) String facilitiesType,
                                 @RequestParam(required = false) String facilitiesName,
                                 @RequestParam(required = false) Boolean isShowChildren,
                                 @RequestParam(required = false) String parentId) {
        isUseUserPermissions = null == isUseUserPermissions ? true : isUseUserPermissions;
        isShowChildren = null == isShowChildren ? true : isShowChildren;
        Page<FacilitiesVo> page = facilitiesService.queryPage(currentPage, size, isUseUserPermissions, nodeType, nodeId, facilitiesType, facilitiesName, isShowChildren,parentId);
        return RespResult.ok(new PageUtils(page));
    }

    @ApiOperation(value = "查询(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<Facilities>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<Facilities> pageUtil =  baseFacilitiesService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation("查询路段设施(不分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isUseUserPermissions", value = "是否使用用户权限过滤", dataType = "boolean", paramType = "query", example = "true"),
            @ApiImplicitParam(name = "nodeType", value = "节点类型（org,way,fac）", dataType = "string", paramType = "query", example = ""),
            @ApiImplicitParam(name = "nodeId", value = "节点ID", dataType = "string", paramType = "query", example = ""),
            @ApiImplicitParam(name = "facilitiesType", value = "设施类型", dataType = "string", paramType = "query", example = ""),
            @ApiImplicitParam(name = "facilitiesName", value = "设施名称", dataType = "string", paramType = "query", example = ""),
            @ApiImplicitParam(name = "isShowChildren", value = "是否获取子设施", dataType = "boolean", paramType = "query", example = "true"),
            @ApiImplicitParam(name = "parentId", value = "父节点id", dataType = "string", paramType = "query", example = "false")
    })
    @GetMapping(value = "/query")
    public RespResult queryPage( @RequestParam(required = false) Boolean isUseUserPermissions,
                                 @RequestParam(required = false) String nodeType,
                                 @RequestParam(required = false) String nodeId,
                                 @RequestParam(required = false) String facilitiesType,
                                 @RequestParam(required = false) String facilitiesName,
                                 @RequestParam(required = false) Boolean isShowChildren,
                                 @RequestParam(required = false) String parentId) {
        isUseUserPermissions = null == isUseUserPermissions ? true : isUseUserPermissions;
        isShowChildren = null == isShowChildren ? true : isShowChildren;
        List<FacilitiesVo> facilitiesVoList = facilitiesService.query(isUseUserPermissions, nodeType, nodeId, facilitiesType, facilitiesName, isShowChildren,parentId);
        return RespResult.ok(facilitiesVoList);
    }

    @ApiOperation("新增路段设施")
    @PostMapping
    public RespResult create(@Validated @RequestBody FacilitiesInfoVo facilities) throws Exception {
        facilitiesService.create(facilities);
        log.info(LogUtil.buildUpParams("新增设施", LogTypeEnum.OPERATE.getCode(), facilities.getFacilitiesId()));
        return RespResult.ok();
    }

    @ApiOperation("删除路段设施")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> facilitiesIds) {
        facilitiesService.delete(facilitiesIds);
        log.info(LogUtil.buildUpParams("删除设施", LogTypeEnum.OPERATE.getCode(), facilitiesIds));
        return RespResult.ok();
    }

    @ApiOperation("修改路段设施")
    @PutMapping
    public RespResult update(@Validated @RequestBody FacilitiesInfoVo facilities) throws Exception {
        facilitiesService.update(facilities);
        log.info(LogUtil.buildUpParams("删除设施", LogTypeEnum.OPERATE.getCode(), facilities.getFacilitiesId()));
        return RespResult.ok();
    }

    @ApiOperation(value = "根据路段查找收费站集合(根据路段ID)")
    @GetMapping(value = "/tollStation/{waysectionId}")
    public RespResult tollStation(@PathVariable("waysectionId") String waysectionId) {
        List<Facilities> tollStations = facilitiesService.findTollStations(waysectionId);
        return RespResult.ok(tollStations);
    }

    @ApiOperation(value = "查看路段设施(根据ID)")
    @GetMapping(value = "/{facilitiesId}")
    public RespResult<FacilitiesInfoVo> detail(@PathVariable("facilitiesId") String facilitiesId) throws Exception {
        FacilitiesInfoVo facilities = facilitiesService.detail(facilitiesId);
        return RespResult.ok(facilities);
    }

}
