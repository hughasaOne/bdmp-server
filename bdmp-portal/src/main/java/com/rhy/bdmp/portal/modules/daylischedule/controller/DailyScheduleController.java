package com.rhy.bdmp.portal.modules.daylischedule.controller;

import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.sys.domain.po.DailySchedule;
import com.rhy.bdmp.portal.modules.daylischedule.service.DailyScheduleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;


/**
 * @description  前端控制器
 * @author shuaichao
 * @date 2022-03-03 09:54
 * @version V1.0
 **/
@Api(tags ="日程安排管理")
@Slf4j
@RestController
@RequestMapping("/bdmp/portal/daily-schedule")
public class DailyScheduleController {

	@Resource
	private DailyScheduleService dailyScheduleService;

    @ApiOperation(value = "查询", notes = "构造查询条件")
    @PostMapping(value = "/list")
    public RespResult<List<DailySchedule>> list(@RequestBody(required = false) DailySchedule dailySchedule) {
        List<DailySchedule> result = dailyScheduleService.list(dailySchedule);
        return RespResult.ok(result);
    }




    @ApiOperation(value = "根据日期查询")
    @GetMapping(value = "/findByScheduleDate/{scheduleDate}")
    public RespResult<List<DailySchedule>> detail(
            @PathVariable("scheduleDate") Long scheduleDate) {
        List<DailySchedule> dailySchedule = dailyScheduleService.findByScheduleDate(scheduleDate);
        return RespResult.ok(dailySchedule);
    }

    @ApiOperation(value = "查看(根据ID)")
    @GetMapping(value = "/{dailyScheduleId}")
    public RespResult<DailySchedule> detail(@PathVariable("dailyScheduleId") String dailyScheduleId) {
        DailySchedule dailySchedule = dailyScheduleService.detail(dailyScheduleId);
        return RespResult.ok(dailySchedule);
    }

    @ApiOperation("新增")
    @PostMapping
    public RespResult create(@Validated @RequestBody DailySchedule dailySchedule){
        dailyScheduleService.create(dailySchedule);
        return RespResult.ok();
    }

    @ApiOperation("修改")
    @PutMapping
    public RespResult update(@Validated @RequestBody DailySchedule dailySchedule){
        dailyScheduleService.update(dailySchedule);
        return RespResult.ok();
    }

    @ApiOperation("删除")
    @DeleteMapping
    public RespResult delete(@RequestBody Set<String> dailyScheduleIds){
        dailyScheduleService.delete(dailyScheduleIds);
        return RespResult.ok();
    }
}
