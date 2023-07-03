package com.rhy.bdmp.open.modules.app.controller;

import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bdmp.open.modules.app.domain.bo.NoticeConfigListBo;
import com.rhy.bdmp.open.modules.app.domain.po.NoticeConfig;
import com.rhy.bdmp.open.modules.app.service.INoticeConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "移动通知配置")
@Slf4j
@RestController
@RequestMapping("/bdmp/public/app")
public class NoticeConfigController {

    @Resource
    private INoticeConfigService noticeConfigService;

    @ApiOperation(value = "保存或更新")
    @PostMapping("saveOrUpdate")
    public RespResult saveOrUpdate(@RequestBody List<NoticeConfig> noticeConfigList){
        noticeConfigService.saveOrUpdateBatchNoticeConfig(noticeConfigList);
        return RespResult.ok();
    }

    @ApiOperation(value = "获取移动通知配置信息")
    @PostMapping("getNoticeConfigList")
    public RespResult<List<String>> getNoticeConfigList(@RequestBody List<NoticeConfigListBo> noticeConfigListBo){
        return RespResult.ok(noticeConfigService.getNoticeConfigList(noticeConfigListBo));
    }
}
