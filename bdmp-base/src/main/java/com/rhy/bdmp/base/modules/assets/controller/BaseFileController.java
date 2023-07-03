package com.rhy.bdmp.base.modules.assets.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.service.IBaseFileService;
import com.rhy.bdmp.base.modules.assets.domain.po.File;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * @description 文件 前端控制器
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Api(tags = {"文件管理"})
@Slf4j
//@RestController
//@RequestMapping("//bdmp/system/assets/file")
public class BaseFileController {

	@Resource
	private IBaseFileService baseFileService;

    @ApiOperation(value = "查询文件", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<File>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<File> result = baseFileService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询文件(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<File>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<File> pageUtil =  baseFileService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看文件(根据ID)")
    @GetMapping(value = "/{fileId}")
    public RespResult<File> detail(@PathVariable("fileId") String fileId) {
        File file = baseFileService.detail(fileId);
        return RespResult.ok(file);
    }

    @ApiOperation("新增文件")
    @PostMapping
    public RespResult create(@Validated @RequestBody File file){
        baseFileService.create(file);
        return RespResult.ok();
    }

    @ApiOperation("修改文件")
    @PutMapping
    public RespResult update(@Validated @RequestBody File file){
        baseFileService.update(file);
        return RespResult.ok();
    }

    @ApiOperation("删除文件")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> fileIds){
        baseFileService.delete(fileIds);
        return RespResult.ok();
    }
}
