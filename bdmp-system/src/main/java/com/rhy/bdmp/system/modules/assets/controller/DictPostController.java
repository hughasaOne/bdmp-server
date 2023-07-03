package com.rhy.bdmp.system.modules.assets.controller;

import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bdmp.base.modules.assets.domain.po.DictPost;
import com.rhy.bdmp.base.modules.assets.service.IBaseDictPostService;
import com.rhy.bdmp.system.modules.assets.domain.bo.DictBO;
import com.rhy.bdmp.system.modules.assets.service.IDictPostService;
import com.rhy.bdmp.system.modules.logging.enums.LogTypeEnum;
import com.rhy.bdmp.system.modules.logging.util.LogUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;

@Api(tags = {"岗位字典管理"})
@Slf4j
@RestController
@RequestMapping("/bdmp/system/assets/dict/post")
public class DictPostController {

    @Resource
    private IDictPostService dictPostService;

    @Resource
    private IBaseDictPostService baseDictPostService;

    @ApiOperation("运营公司岗位字典树")
    @GetMapping(value = "/getOrgPostTree")
    public RespResult getOrgPostTree(@RequestParam("parentCode") String parentCode) {
        return RespResult.ok(dictPostService.getOrgPostTree(parentCode));
    }

    @ApiOperation("获取组织拥有的的岗位")
    @GetMapping(value = "/getOrgPost")
    public RespResult getOrgPost(@RequestParam String orgType) {
        return RespResult.ok(dictPostService.getOrgPost(orgType));
    }

    @ApiOperation("获取岗位字典")
    @PostMapping("/getDictPost")
    public RespResult getDictPost(@Validated @RequestBody DictBO dictBO){
        return RespResult.ok(dictPostService.getDictPost(dictBO));
    }

    @ApiOperation("新增岗位字典")
    @PostMapping
    public RespResult create(@Validated @RequestBody DictPost dictPost){
        baseDictPostService.create(dictPost);
        log.info(LogUtil.buildUpParams("新增岗位字典", LogTypeEnum.OPERATE.getCode(), dictPost.getPostId()));
        return RespResult.ok();
    }

    @ApiOperation("修改岗位字典")
    @PutMapping
    public RespResult update(@Validated @RequestBody DictPost dictPost){
        baseDictPostService.update(dictPost);
        log.info(LogUtil.buildUpParams("修改岗位字典", LogTypeEnum.OPERATE.getCode(), dictPost.getPostId()));
        return RespResult.ok();
    }

    @ApiOperation("删除岗位字典")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> postIds){
        baseDictPostService.delete(postIds);
        log.info(LogUtil.buildUpParams("删除岗位字典", LogTypeEnum.OPERATE.getCode(), postIds));
        return RespResult.ok();
    }
}
