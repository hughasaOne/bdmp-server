package com.rhy.bdmp.system.modules.sys.controller;

import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bdmp.system.modules.sys.domain.bo.LogBo;
import com.rhy.bdmp.system.modules.sys.domain.vo.LogVo;
import com.rhy.bdmp.system.modules.sys.service.ILogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = {"日志管理"})
@Slf4j
@RestController
@RequestMapping("/bdmp/system/sys/log")
@Component("logController1")
public class LogController {
    @Resource
    private ILogService logService;

    @ApiOperation(value = "查询日志(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<LogVo>> page(@RequestBody(required = true) LogBo logBo) {
        return RespResult.ok(logService.page(logBo));
    }
}
