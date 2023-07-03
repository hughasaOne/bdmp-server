package com.rhy.bdmp.system.modules.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtils;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bcp.logging.annotation.Log;
import com.rhy.bdmp.base.modules.sys.domain.po.Dict;
import com.rhy.bdmp.system.modules.sys.service.DictService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @ClassName:SystemController
 * @Description:
 * @Author:魏财富
 * @Date:2021/4/15 17:25
 */
@RestController
@RequestMapping("/bdmp/system/sys/dict")
public class DictController {
    @Resource
    private DictService sysDictService;
    @ApiOperation("查询字典(分页)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "当前页", dataType = "int", paramType = "query", required = false, example = "1"),
            @ApiImplicitParam(name = "size", value = "页大小", dataType = "int", paramType = "query", required = false, example = "20"),
            @ApiImplicitParam(name = "parentCode", value = "父节点code", dataType = "String", paramType = "query", required = false, example = ""),
            @ApiImplicitParam(name = "dictName", value = "字典名称", dataType = "string", paramType = "query", required = false, example = "")
    })
    @GetMapping("/queryPage")
    public RespResult queryPage(@RequestParam(required = false)Integer currentPage,  @RequestParam(required = false)Integer size, @RequestParam(required = true) String parentCode, @RequestParam(required = false) String dictName){
        Page<Dict> page = sysDictService.queryPage(currentPage, size, parentCode, dictName);
        return RespResult.ok(new PageUtils(page));
    }

    @ApiOperation("查询字典根据字典类型CODE")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parentCode", value = "父节点code", dataType = "String", paramType = "query", required = false, example = ""),
            @ApiImplicitParam(name = "dictName", value = "字典名称", dataType = "string", paramType = "query", required = false, example = "")
    })
    @GetMapping("/queryByCode")
    public RespResult queryByCode(@RequestParam(required = true) String parentCode, @RequestParam(required = false) String dictName){
        List<Dict> nodeVoList = sysDictService.queryByCode(parentCode, dictName);
        return RespResult.ok(nodeVoList);
    }

    /**
     * @date: 2021/4/19 11:25
     */
    @ApiOperation("查询系统字典目录")
    @GetMapping("/findDirectory")
    public RespResult findDirectory(){
        List<Dict> dictList = sysDictService.findDirectory();
        return RespResult.ok(dictList);
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
        return RespResult.ok(sysDictService.findChild(parentId,includeId,queryVO));
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
        return RespResult.ok(sysDictService.findChildDict(parentId,queryVO));
    }

    /**
     * @description: 添加系统字典
     * @date: 2021/4/16 11:55
     * @param: Dict
     */
    @ApiOperation("新增系统字典")
    @Log("字典：系统字典新增")
    @PostMapping
    public RespResult create(@Validated @RequestBody Dict dict) {
        boolean result = sysDictService.createDict(dict);
        return RespResult.ok();
    }

    /**
     * @description: 修改系统字典
     * @date: 2021/4/16 11:55
     * @param: Dict
     */
    @ApiOperation("修改系统字典")
    @Log("字典：系统字典修改")
    @PutMapping
    public RespResult update(@Validated @RequestBody Dict sysDict) {
        boolean result = sysDictService.updateDict(sysDict);
        return RespResult.ok();

    }

    /**
     * @description: 删除系统字典
     * @date: 2021/4/16 11:55
     * @param: [dictIds]
     */
    @ApiOperation("删除系统字典")
    @Log("字典：系统字典删除")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> dictIds) {
        int result = sysDictService.deleteDict(dictIds);
        return RespResult.ok();
    }

    @ApiOperation(value = "查看系统字典(根据ID)")
    @GetMapping(value = "/{dictId}")
    public RespResult detail(@PathVariable("dictId") String dictId) {
        Dict dict = sysDictService.detail(dictId);
        return RespResult.ok(dict);
    }
}
