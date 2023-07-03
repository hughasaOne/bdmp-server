package com.rhy.bdmp.system.modules.analysis.dao;

import com.rhy.bdmp.base.modules.sys.domain.po.Org;
import com.rhy.bdmp.system.modules.analysis.domain.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: AnalysisDao
 * @Description: 资源统计分析接口
 * @Author: 魏财富
 * @Date: 2021/4/15 9:57
 */
@Mapper
public interface AnalysisDao {
    /**
     * @description: 统计系统资源总数
     * @date: 2021/4/17 17:37
     * @return: com.rhy.bdmp.system.modules.analysis.domain.vo.SysSumVO
     */
    Map<String,Object> statSysResourcesNum();

    /**
     * @description: 人员系统应用分布统计
     * @date: 2021/4/19 17:49
     * @return: com.rhy.bdmp.system.modules.analysis.domain.vo.UserDistributionAppVO
     */
    List<Map<String,Object>> statUserOfApp();

    /**
     * @description: 组织机构图
     * @date: 2021/4/21 12:34
     * @return: List<UserByOrgVO>
     */
    Map<String,Object> sumUserByOrgId(@Param("orgId") String orgId);

    /**
     * @description: 统计台账资源总数
     * @date: 2021/4/17 17:38
     * @return: com.rhy.bdmp.system.modules.analysis.domain.vo.AssetsSumVo
     */
    Map<String,Object> countAssetsSum();

    /**
     * @description: 统计运营公司及其以下机构的路段数和里程数
     * @date:2021/4/20 11:46
     * @return: java.util.List<com.rhy.bdmp.system.modules.analysis.domain.vo.CountWaySctionAndMileageVO>
     */
    List<Map<String, String>> sumWaySectionAndMileage();

    /**
     * @description: 统计运营公司及其以下机构的重点设施数
     * @date:2021/4/20 14:40
     * @param: [orgId:公司id]
     * @return: com.rhy.bdmp.system.modules.analysis.domain.vo.CountKeyFacilitiesVO
     */
    List<RanKOperatingCompanyVO> sumKeyFacilitiesByOrgId(@Param("orgId") String orgId);

    /**
     * @description: 查询运营公司及其以下机构的系统分类设备总数
     * @date: 2021/4/20 18:56
     * @param: [orgId]
     * @return: com.rhy.bdmp.system.modules.analysis.domain.vo.CountOrgAndSysVO
     */
    RanKOperatingCompanyVO countDeviceByOrgId(@Param("orgId") String orgId,@Param("flag") String flag);

    /**
     * @description: 查询所有机构
     */
    List<Org> getAllOrgs();

    /**
     * @description: 查询所有运营公司
     * @date: 2021/4/20 11:45
     * @return: java.util.List<com.rhy.bdmp.base.modules.sys.domain.po.Org>
     */
    List<Org> getOperatingCompanys();

    List<Map<String,String>> getSysType();

    List<RanKOperatingCompanyVO> deviceStatByOrg(@Param("systemIds")  List<String> systemIds);

    /**
     * 获取机构下的用户
     */
    List<Map<String, Object>> getOrgSubUserList();
}
