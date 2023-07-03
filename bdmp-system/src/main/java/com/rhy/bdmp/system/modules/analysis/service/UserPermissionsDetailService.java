package com.rhy.bdmp.system.modules.analysis.service;

import com.rhy.bdmp.system.modules.analysis.domain.vo.AuthorizationVO;
import com.rhy.bdmp.system.modules.analysis.domain.vo.ExcelUserPermissionsVO;
import com.rhy.bdmp.system.modules.analysis.domain.vo.OrgUserVO;
import com.rhy.bdmp.system.modules.analysis.domain.vo.UserPermissionsVO;

import java.util.List;

/**
 * 用户权限明细业务层
 *
 * @author PSQ
 */
public interface UserPermissionsDetailService {
    /**
     * 根据运营公司查询用户权限明细
     *
     * @param orgId 机构id
     * @return
     */
    List<ExcelUserPermissionsVO> getCompanyUserPermissions(String orgId);

    /**
     * 根据用户id查询用户权限明细
     *
     * @param userId  用户id
     * @param isExcel 数据是否作用于excel
     * @return
     */
    UserPermissionsVO getUserPermissionsDetail(String userId, Boolean isExcel);

    /**
     * 一键同步授权
     *
     * @param authorizationVo 同步权限vo
     * @return 执行结果
     */
    Boolean oneClickAuthorization(AuthorizationVO authorizationVo);

    /**
     * 查询组织公司机构用户
     *
     * @return 返回运营公司，组织机构和用户呈现树形
     */
    Object selectOrgUser();

    /**
     * 查询组织机构
     *
     * @return 返回组织机构 用于表格
     */
    List<OrgUserVO> selectOrg();

}
