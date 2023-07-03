package com.rhy.bdmp.system.modules.analysis.controller;


import com.alibaba.excel.EasyExcel;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.resutl.RespResult;
import com.rhy.bdmp.system.modules.analysis.domain.vo.AuthorizationVO;
import com.rhy.bdmp.system.modules.analysis.domain.vo.ExcelUserPermissionsVO;
import com.rhy.bdmp.system.modules.analysis.domain.vo.UserPermissionsVO;
import com.rhy.bdmp.system.modules.analysis.service.UserPermissionsDetailService;
import com.rhy.bdmp.system.modules.assets.handler.GeneralMergeExcelTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 权限明细
 *
 * @author 庞盛权
 */
@Api(tags = "权限明细")
@RestController
@RequestMapping("/bdmp/system/analysis/permissions")
public class UserPermissionsDetailController {

    @Resource
    private UserPermissionsDetailService userPermissionsDetailService;

    /**
     * 查询公司下所有用户的权限明细
     *
     * @param orgId 机构id
     */
    @ApiOperation("导出公司下所有用户的权限")
    @RequestMapping(value = "/getCompanyUserPermissions", method = RequestMethod.GET)
    public void getCompanyUserPermissions(@RequestParam String orgId, HttpServletResponse response) {
        List<ExcelUserPermissionsVO> companyUserPermissions = userPermissionsDetailService.getCompanyUserPermissions(orgId);
        String fileName = "导出权限明细";
        try {
            //设置表格名字
            fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("content-Type", "application/octet-stream");
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), ExcelUserPermissionsVO.class)
                    // 第一个参数表示开始合并的行,从第2行开始,第一行是标题行.
                    // 第二个参数表示需要合并的列，合并第一列
                    .registerWriteHandler(new GeneralMergeExcelTemplate(1, new int[]{0}))
                    .useDefaultStyle(true)
                    .sheet("权限明细")
                    .doWrite(companyUserPermissions);
        } catch (UnsupportedEncodingException e) {
            throw new BadRequestException("中文乱码异常");
        } catch (IOException e) {
            throw new BadRequestException("导出ExcelIO异常");
        }
    }

    /**
     * 查询用户权限明细
     *
     * @param userId 用户id
     * @return
     */
    @ApiOperation("查询用户权限明细")
    @RequestMapping(value = "/getUserPermissionsDetail", method = RequestMethod.GET)
    public RespResult<UserPermissionsVO> getUserPermissionsDetail(@RequestParam String userId) {
        UserPermissionsVO userPermissionsVoS = userPermissionsDetailService.getUserPermissionsDetail(userId, Boolean.FALSE);
        return RespResult.ok(userPermissionsVoS);
    }

    /**
     * 一键同步权限
     *
     * @param authorizationVo
     */
    @ApiOperation("一键同步权限")
    @PostMapping("/oneClickAuthorization")
    public RespResult oneClickAuthorization(@RequestBody AuthorizationVO authorizationVo) {
        if (userPermissionsDetailService.oneClickAuthorization(authorizationVo)) {
            return RespResult.ok();
        } else {
            return RespResult.error("权限同步失败");
        }
    }


    @ApiOperation("查询组织公司机构用户")
    @PostMapping("/selectOrgUser")
    public RespResult selectOrgUser() {
        return RespResult.ok(userPermissionsDetailService.selectOrgUser());
    }

    @ApiOperation("查询组织机构")
    @PostMapping("/selectOrg")
    public RespResult selectOrg() {
        return RespResult.ok(userPermissionsDetailService.selectOrg());
    }
}
