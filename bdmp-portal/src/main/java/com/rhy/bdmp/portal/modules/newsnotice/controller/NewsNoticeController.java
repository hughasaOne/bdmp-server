package com.rhy.bdmp.portal.modules.newsnotice.controller;

import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.sys.domain.po.NewsNotice;
import com.rhy.bdmp.base.modules.sys.service.IBaseNewsNoticeService;
import com.rhy.bdmp.portal.modules.newsnotice.service.NewsNoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * @description  新闻公告 前端控制器
 * @author shuaichao
 * @date 2022-03-07 13:39
 * @version V1.0
 **/
@Api(tags = {"新闻公告管理"})
@Slf4j
@RestController
@RequestMapping("/bdmp/portal/news-notice")
public class NewsNoticeController {

	@Resource
	private NewsNoticeService newsNoticeService;

    @ApiOperation(value = "查询", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<NewsNotice>> list() {
        List<NewsNotice> result = newsNoticeService.list();
        return RespResult.ok(result);
    }


    @ApiOperation(value = "查看(根据ID)")
    @GetMapping(value = "/{newsNoticeId}")
    public RespResult<NewsNotice> detail(@PathVariable("newsNoticeId") String newsNoticeId) {
        NewsNotice newsNotice = newsNoticeService.findById(newsNoticeId);
        return RespResult.ok(newsNotice);
    }

    @ApiOperation(value = "爬取交投新闻")
    @GetMapping(value = "/insertByJsoup")
    public RespResult<Integer> insertByJsoup() {
        Integer count = newsNoticeService.insertByJsoup();
        return RespResult.ok(count);
    }





}
