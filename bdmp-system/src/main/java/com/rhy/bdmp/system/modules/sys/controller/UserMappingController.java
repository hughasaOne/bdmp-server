package com.rhy.bdmp.system.modules.sys.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtils;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bcp.logging.annotation.Log;
import com.rhy.bdmp.base.modules.sys.domain.po.UserMapping;
import com.rhy.bdmp.system.modules.logging.enums.LogTypeEnum;
import com.rhy.bdmp.system.modules.logging.util.LogUtil;
import com.rhy.bdmp.system.modules.sys.service.UserMappingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;

@Api(tags = {"用户映射"})
@Slf4j
@RestController
@RequestMapping("/bdmp/system/sys/userMapping")
public class UserMappingController {

    @Resource
    private UserMappingService userMappingService;

    @ApiOperation(value = "查询用户映射", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult list(@RequestBody(required = false) QueryVO queryVO) {
        Object result = userMappingService.list(queryVO);
        if (result instanceof Page) {
            return RespResult.ok(new PageUtils((Page<UserMapping>) result));
        } else {
            return RespResult.ok(result);
        }
    }

    @ApiOperation("删除用户映射")
    @Log("用户映射：删除")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> userMappingIds) {
        userMappingService.delete(userMappingIds);
        log.info(LogUtil.buildUpParams("删除用户映射", LogTypeEnum.OPERATE.getCode(),userMappingIds));
        return RespResult.ok();
    }

    /**
     * @Description: 新增用户映射
     * @Author: dongyu
     * @Date: 2021/4/20
     */
    @ApiOperation("新增用户映射")
    @Log("用户映射：新增")
    @PostMapping
    public RespResult create(@Validated @RequestBody UserMapping userMapping) {
        userMappingService.create(userMapping);
        log.info(LogUtil.buildUpParams("新增用户映射", LogTypeEnum.OPERATE.getCode(), JSONUtil.createObj().putOnce("userId",userMapping.getUserId())));
        return RespResult.ok();
    }

    /**
     * @Description: 修改用户映射
     * @Author: dongyu
     * @Date: 2021/4/20
     */
    @ApiOperation("修改用户映射")
    @Log("用户映射：修改")
    @PutMapping
    public RespResult update(@Validated @RequestBody UserMapping userMapping) {
        userMappingService.update(userMapping);
        log.info(LogUtil.buildUpParams("修改用户映射", LogTypeEnum.OPERATE.getCode(), JSONUtil.createObj().putOnce("userId",userMapping.getUserId())));
        return RespResult.ok();
    }
}
