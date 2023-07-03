package com.rhy.bdmp.system.modules.analysis.service;

import com.rhy.bdmp.system.modules.analysis.domain.vo.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName: AnalysisService
 * @Description: 资源统计服务接口
 * @Author: 魏财富
 * @Date: 2021/4/15 9:58
 */
public interface AnalysisService{
    /**
     * @description: 统计系统资源
     * @date: 2021/4/17 17:41
     * @return: com.rhy.bdmp.system.modules.analysis.domain.vo.SysSumVO
     */
    Map<String,Object> statSysResourcesNum();

    /**
     * @description: 统计机构下的用户总数
     * @date: 2021/4/21 12:31
     * @return: List<UserByOrgVO>
     */
    List<Map<String,Object>> statUserOfApp();

    /**
     * @description: 人员系统应用分布统计
     * @date: 2021/4/19 17:54
     * @return: Map<String, List<UserDistributionAppVO>>
     */
    List<Map<String,Object>> statUserOrgStructure();

    /**
     * @description: 统计台账资源
     * @date: 2021/4/17 17:41
     * @return: com.rhy.bdmp.system.modules.analysis.domain.vo.AssetsSumVo
     */
    Map<String,Object> countAssetsSum();

    /**
     * @description: 查看运营公司排名
     * @date: 2021/4/22 17:26
     * @param: detail：1代表路段里程排名，2代表设施排名，3代表系统排名
     * @return: Map<String, Object>
     */
    Map<String, Map<String, List<RanKOperatingCompanyVO>>> rankOperatingCompany(String rankType, Set<String> typeIds);

    /**
     * 获取系统类型
     */
    List<Map<String,String>> getSysType();
}
