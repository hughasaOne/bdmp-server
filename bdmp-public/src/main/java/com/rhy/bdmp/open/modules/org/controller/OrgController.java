package com.rhy.bdmp.open.modules.org.controller;

import cn.hutool.http.server.HttpServerResponse;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bdmp.open.modules.common.domain.bo.CommonBo;
import com.rhy.bdmp.open.modules.org.domain.bo.OrgDetailBo;
import com.rhy.bdmp.open.modules.org.domain.bo.OrgListBo;
import com.rhy.bdmp.open.modules.org.domain.bo.OrgTreeBo;
import com.rhy.bdmp.open.modules.org.service.IOrgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @author weicaifu
 */
@Api(tags = "组织机构服务")
@RestController
@RequestMapping("/bdmp/public/org")
public class OrgController {

    @Resource
    private IOrgService orgService;

    @ApiOperation("获取组织路段设施树")
    @PostMapping("/way/fac/tree/v1")
    public RespResult getOrgWayFacTree(@RequestBody CommonBo commonBo){
        return RespResult.ok(orgService.getOrgWayFacTree(commonBo));
    }

    @ApiOperation("获取组织树")
    @PostMapping("/tree/v1")
    public RespResult getOrgTree(@RequestBody OrgTreeBo orgTreeBo){
        return RespResult.ok(orgService.getOrgTree(orgTreeBo));
    }

    @ApiOperation("获取组织列表")
    @PostMapping("/list/v1")
    public RespResult getOrgList(@RequestBody OrgListBo orgListBo){
        return RespResult.ok(orgService.getOrgList(orgListBo));
    }

    @ApiOperation("获取组织详情")
    @PostMapping("/detail/v1")
    public RespResult detail(@RequestBody OrgDetailBo detailBo){
        return RespResult.ok(orgService.detail(detailBo));
    }

    @ApiOperation("组织接入的ip电话统计")
    @PostMapping("/ip-tel/stat/v1")
    public RespResult ipTelStat(){
        return RespResult.ok(orgService.ipTelStat());
    }


    @ApiOperation("组织接入的ip电话统计导出")
    @GetMapping("/ip-tel/stat/v1/export")
    public void exportIpTelStat(HttpServletResponse response) throws IOException {
        SXSSFWorkbook wb =  orgService.exportIpTelStat();
        String fileName = "IP对讲在线率.xlsx";
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        // 这个操作也非常的耗时,暂时不知道和什么有关,应该该和文件的大小有关
        response.setContentType("application/vnd.ms-excel");
        wb.write(response.getOutputStream());



    }





}
