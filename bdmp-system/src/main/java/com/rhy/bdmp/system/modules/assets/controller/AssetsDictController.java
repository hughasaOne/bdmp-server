package com.rhy.bdmp.system.modules.assets.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtils;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.domain.po.Dict;
import com.rhy.bdmp.system.modules.assets.domain.bo.DictBO;
import com.rhy.bdmp.system.modules.assets.service.AssetsDictService;
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
 * @ClassName: DictController
 * @Description: 台账字典前端控制器
 * @Author: 魏财富
 * @Date: 2021/4/15 11:12
 */
@Api(tags = {"台账字典管理"})
@Slf4j
@RestController
@RequestMapping("/bdmp/system/assets/dict")
public class AssetsDictController {
    @Resource
    private AssetsDictService assetsDictService;

    @ApiOperation("获取台账字典")
    @PostMapping("/getDictAssets")
    public RespResult getDictAssets(@Validated @RequestBody DictBO dictBO){
        return RespResult.ok(assetsDictService.getDictAssets(dictBO));
    }

    @ApiOperation("查询字典(分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "当前页", dataType = "int", paramType = "query", required = false, example = "1"),
            @ApiImplicitParam(name = "size", value = "页大小", dataType = "int", paramType = "query", required = false, example = "20"),
            @ApiImplicitParam(name = "parentCode", value = "父节点code", dataType = "String", paramType = "query", required = false, example = ""),
            @ApiImplicitParam(name = "dictName", value = "字典名称", dataType = "string", paramType = "query", required = false, example = "")
    })
    @GetMapping("/queryPage")
    public RespResult queryPage(@RequestParam(required = false)Integer currentPage,  @RequestParam(required = false)Integer size, @RequestParam(required = true) String parentCode, @RequestParam(required = false) String dictName){
        Page<Dict> page = assetsDictService.queryPage(currentPage, size, parentCode, dictName);
        return RespResult.ok(new PageUtils(page));
    }

    @ApiOperation("查询字典根据字典类型CODE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentCode", value = "父节点code", dataType = "String", paramType = "query", required = false, example = ""),
            @ApiImplicitParam(name = "dictName", value = "字典名称", dataType = "string", paramType = "query", required = false, example = "")
    })
    @GetMapping("/queryByCode")
    public RespResult queryByCode(@RequestParam(required = true) String parentCode,
                                  @RequestParam(required = false) String dictName,
                                  @RequestParam(required = false) Boolean useInnerParentId){
        return RespResult.ok(assetsDictService.queryByCode(parentCode, dictName,useInnerParentId));
    }

    /**
     * @description: 查询台账目录，parentId = null
     * @date: 2021/4/20 10:04
     * @return: com.rhy.bcp.common.resutl.RespResult
     */
    @ApiOperation("查找台账字典目录")
    @GetMapping("/findDirectory")
    public RespResult findDirectory(@RequestParam(value = "param",required = false) String param){
        List<Dict> nodeVos = assetsDictService.findDirectory(param);
        return RespResult.ok(nodeVos);
    }

    /**
     * @description: 查找子节点
     * @param parentId 父节点id
     * @date: 2021/4/20 10:04
     * @return: com.rhy.bcp.common.resutl.RespResult
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentId", value = "父节点id", dataType = "String", paramType = "query", required = false, example = "1390209455804186625"),
    })
    @ApiOperation("查找子节点")
    @PostMapping("/findChildren")
    public RespResult findChild(@RequestParam(required = false) String parentId, @RequestParam(required = false) String includeId, @RequestBody(required = false)QueryVO queryVO){
        return RespResult.ok(assetsDictService.findChild(parentId,includeId,queryVO));
    }

    /**
     * @description: 查找子节点
     * @param parentId 父节点id
     * @date: 2021/4/20 10:04
     * @return: com.rhy.bcp.common.resutl.RespResult
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentId", value = "父节点id", dataType = "String", paramType = "query", required = false, example = "1390209455804186625"),
    })
    @ApiOperation("查找子节点-只找字典")
    @PostMapping("/findChildrenDict")
    public RespResult findChildDict(@RequestParam(required = false) String parentId, @RequestBody(required = false)QueryVO queryVO){
        return RespResult.ok(assetsDictService.findChildDict(parentId,queryVO));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "dictId", value = "字典id", dataType = "String", paramType = "query", required = true),
    })
    @ApiOperation("字典详情")
    @GetMapping("/{dictId}")
    public RespResult detail(@PathVariable("dictId") String dictId){
        return RespResult.ok(assetsDictService.detail(dictId));
    }


    /**
     * @description: 添加台账字典记录
     * @date: 2021/4/16 11:54
     * @param: [dict]
     * @return: com.rhy.bcp.common.resutl.RespResult
     */
    @PostMapping
    public RespResult create(@Validated @RequestBody Dict dict) {
        boolean result = assetsDictService.saveDict(dict);
        log.info(LogUtil.buildUpParams("新增台账字典", LogTypeEnum.OPERATE.getCode(), dict.getDictId()));
        return RespResult.ok();
    }

    /**
     * @description: 修改台账字典记录
     * @date: 2021/4/16 11:55
     * @param: [dict]
     * @return: com.rhy.bcp.common.resutl.RespResult
     */
    @PutMapping
    public RespResult update(@Validated @RequestBody Dict dict) {
        boolean result = assetsDictService.updateDict(dict);
        log.info(LogUtil.buildUpParams("修改台账字典", LogTypeEnum.OPERATE.getCode(), dict.getDictId()));
        if (result){
            return RespResult.ok();
        }
        return RespResult.error("修改失败");
    }

    /**
     * @description: 删除台账字典记录
     * @date: 2021/4/16 11:55
     * @param: [dictIds]
     * @return: com.rhy.bcp.common.resutl.RespResult
     */
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> dictIds) {
        int result = assetsDictService.deleteDict(dictIds);
        log.info(LogUtil.buildUpParams("删除台账字典", LogTypeEnum.OPERATE.getCode(), dictIds));
        return RespResult.ok();
    }
}
