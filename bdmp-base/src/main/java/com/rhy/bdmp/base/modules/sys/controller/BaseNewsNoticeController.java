package com.rhy.bdmp.base.modules.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.sys.service.IBaseNewsNoticeService;
import com.rhy.bdmp.base.modules.sys.domain.po.NewsNotice;
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
@Api(tags = {"管理"})
@Slf4j
//@RestController
//@RequestMapping("/bdmp/protal/newsnotice/news-notice")
public class BaseNewsNoticeController {

	@Resource
	private IBaseNewsNoticeService baseNewsNoticeService;

    @ApiOperation(value = "查询", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<NewsNotice>> list(@RequestBody(required = false) QueryVO queryVO) {
        List<NewsNotice> result = baseNewsNoticeService.list(queryVO);
        return RespResult.ok(result);
    }

    @ApiOperation(value = "查询(分页)", notes = "构造查询条件")
    @PostMapping(value = "/page")
    public RespResult<PageUtil<NewsNotice>> page(@RequestBody(required = true) QueryVO queryVO) {
        PageUtil<NewsNotice> pageUtil =  baseNewsNoticeService.page(queryVO);
        return RespResult.ok(pageUtil);
    }

    @ApiOperation(value = "查看(根据ID)")
    @GetMapping(value = "/{newsNoticeId}")
    public RespResult<NewsNotice> detail(@PathVariable("newsNoticeId") String newsNoticeId) {
        NewsNotice newsNotice = baseNewsNoticeService.detail(newsNoticeId);
        return RespResult.ok(newsNotice);
    }

    @ApiOperation("新增")
    @PostMapping
    public RespResult create(@Validated @RequestBody NewsNotice newsNotice){
        baseNewsNoticeService.create(newsNotice);
        return RespResult.ok();
    }

    @ApiOperation("修改")
    @PutMapping
    public RespResult update(@Validated @RequestBody NewsNotice newsNotice){
        baseNewsNoticeService.update(newsNotice);
        return RespResult.ok();
    }

    @ApiOperation("删除")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> newsNoticeIds){
        baseNewsNoticeService.delete(newsNoticeIds);
        return RespResult.ok();
    }
}
