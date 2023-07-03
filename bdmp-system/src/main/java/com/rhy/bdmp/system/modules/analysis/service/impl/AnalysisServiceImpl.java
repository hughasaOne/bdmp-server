package com.rhy.bdmp.system.modules.analysis.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rhy.bdmp.base.modules.assets.domain.po.DictSystem;
import com.rhy.bdmp.base.modules.assets.service.IBaseDictSystemService;
import com.rhy.bdmp.base.modules.assets.service.impl.BaseDictServiceImpl;
import com.rhy.bdmp.base.modules.sys.domain.po.Org;
import com.rhy.bdmp.system.modules.analysis.dao.AnalysisDao;
import com.rhy.bdmp.system.modules.analysis.domain.vo.RanKOperatingCompanyVO;
import com.rhy.bdmp.system.modules.analysis.service.AnalysisService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @ClassName: AnalysisServiceImpl
 * @Description: 台账资源统计服务实现
 * @Author: 魏财富
 * @Date: 2021/4/15 10:00
 */
@Service
public class AnalysisServiceImpl implements AnalysisService {
    @Resource
    private AnalysisDao analysisdao;
    @Resource
    private BaseDictServiceImpl baseDictService;
    @Resource
    private IBaseDictSystemService baseDictSystemService;

    /**
     * @description: 统计系统资源
     * @date: 2021/4/16 12:52
     * @return: SysSumVO
     */
    @Override
    public Map<String, Object> statSysResourcesNum() {
        return analysisdao.statSysResourcesNum();
    }

    /**
     * @description: 人员系统应用分布统计
     * @date: 2021/4/20 19:01
     * @return: List<HashMap < String, Object>>
     */
    @Override
    public List<Map<String, Object>> statUserOfApp() {
        List<Map<String, Object>> sumUserInApp = analysisdao.statUserOfApp();
        return sumUserInApp;
    }

    /**
     * @description: 组织机构图
     * @date: 2021/4/21 12:32
     * @return: List<UserByOrgVO>
     */
    @Override
    public List<Map<String, Object>> statUserOrgStructure() {
        // 获取机构下的用户
        List<Map<String, Object>> orgSubUserList = analysisdao.getOrgSubUserList();
        if (CollUtil.isEmpty(orgSubUserList)) {
            return Collections.emptyList();
        }
        // 构建node列表
        List<TreeNode<String>> nodeList = CollUtil.newArrayList();
        TreeNode node;
        Map<String, Object> extra;
        for (Map<String, Object> orgSubUser : orgSubUserList) {
            node = new TreeNode<>(orgSubUser.get("orgId").toString(),
                    MapUtil.getStr(orgSubUser,"parentId"),
                    MapUtil.getStr(orgSubUser,"orgName"),
                    Integer.parseInt(orgSubUser.get("sort").toString()));
            extra = new HashMap<>();
            extra.put("totalUser", MapUtil.getLong(orgSubUser,"totalUser"));
            node.setExtra(extra);
            nodeList.add(node);
        }
        List<Tree<String>> treeList = TreeUtil.build(nodeList, "0");

        for (Map<String, Object> map : orgSubUserList) {
            String orgId = MapUtil.getStr(map, "orgId");
            for (Tree<String> treeNode : treeList) {
                Tree<String> selectNode = getNode(treeNode, orgId);
                if (null != selectNode && CollUtil.isNotEmpty(selectNode.getChildren())){
                    setTotalUser(map, selectNode.getChildren());
                }
            }
        }
        return orgSubUserList;
    }

    private void setTotalUser(Map<String, Object> map, List<Tree<String>> childNodes) {
        for (Tree<String> tempNode : childNodes) {
            map.put("totalUser", (Long) map.get("totalUser") + (Long) tempNode.get("totalUser"));
            if (CollUtil.isNotEmpty(tempNode.getChildren())) {
                setTotalUser(map, tempNode.getChildren());
            }
        }
    }

    private Tree<String> getNode(Tree<String> node, String id) {
        if (ObjectUtil.equal(id, node.getId())) {
            return node;
        }

        // 查找子节点
        if (CollUtil.isNotEmpty(node.getChildren())){
            for (Tree<String> child : node.getChildren()) {
                Tree<String> childNode = getNode(child, id);
                if (null != childNode) {
                    return childNode;
                }
            }
        }


        // 未找到节点
        return null;
    }

    /**
     * @return AssetsSumVo
     * @description 统计台账资源
     * @date 2021/4/17 17:40
     */
    @Override
    public Map<String, Object> countAssetsSum() {
        return analysisdao.countAssetsSum();
    }

    /**
     * @date: 2021/4/22 17:26
     * @param: rankType：1：路段、里程数排名，2：设施个数排名，3：系统分类设备分类数排名
     * @param: typeIds："1:{1:路段数,2里程数} 2:{120100:桥梁,120200:隧道,120300:收费站,120400:服务区,120500:门架,120600:互通立交} 3:{110100:监控系统,110200:收费系统,110300:通信系统,110400:供配电系统,110500:隧道机电系统110600:隧道消防系统110700:隧道通风系统,110800:隧道照明系统,110900:门架系统}"
     */
    @Override
    public Map<String, Map<String, List<RanKOperatingCompanyVO>>> rankOperatingCompany(String rankType, Set<String> typeIds) {
        Map<String, Map<String, List<RanKOperatingCompanyVO>>> hashMap = new HashMap<String, Map<String, List<RanKOperatingCompanyVO>>>();
        if ("1".equals(rankType)) {
            Map<String, List<RanKOperatingCompanyVO>> WaySectionAndMileage = rankByWaySectionAndMileage(typeIds);
            hashMap.put("rankWaySectionAndMileage", WaySectionAndMileage);
        } else if ("2".equals(rankType)) {
            Map<String, List<RanKOperatingCompanyVO>> facilities = rankByKeyFacilities(typeIds);
            hashMap.put("rankFacilities", facilities);
        } else if ("3".equals(rankType)) {
            Map<String, List<RanKOperatingCompanyVO>> system = rankBySys(typeIds);
            hashMap.put("rankSystem", system);
        }
        return hashMap;
    }

    /**
     * @param typeIds：1:路段数,2里程数
     * @description 运营公司排名(路段数 、 里程数)
     * @date 2021/4/22 17:26
     */
    public Map<String, List<RanKOperatingCompanyVO>> rankByWaySectionAndMileage(Set<String> typeIds) {
        Map<String, List<RanKOperatingCompanyVO>> sorted = new HashMap<>();
        List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
        //查询路段和里程的总数
        List<Map<String, String>> countWaySectionOrMileage = analysisdao.sumWaySectionAndMileage();

        ArrayList<RanKOperatingCompanyVO> rankOperatingCompanyVOSByWaysection = new ArrayList<>();
        ArrayList<RanKOperatingCompanyVO> rankOperatingCompanyVOSByMileage = new ArrayList<>();
        Set<String> flags = typeIds;
        if (null == typeIds || 0 == typeIds.size()) {
            flags = new HashSet<String>();
            flags.add("1");
            flags.add("2");
        }
        for (String flag : flags) {
            //分组
            if ("1".equals(flag)) {
                for (Map<String, String> map : countWaySectionOrMileage) {
                    RanKOperatingCompanyVO rankOperatingCompanyVO = new RanKOperatingCompanyVO();
                    rankOperatingCompanyVO.setOrgId(map.get("orgId"));
                    rankOperatingCompanyVO.setOrgName(map.get("orgName"));
                    rankOperatingCompanyVO.setNum(MapUtil.getDouble(map, "sumWaysection"));
                    rankOperatingCompanyVO.setRankType(flag);
                    rankOperatingCompanyVOSByWaysection.add(rankOperatingCompanyVO);
                }
                //排序
                List<RanKOperatingCompanyVO> sortedByNum = rankOperatingCompanyVOSByWaysection.stream().sorted(Comparator.comparing(RanKOperatingCompanyVO::getNum).reversed()).collect(Collectors.toList());
                sorted.put("sortedBySumWaySection", sortedByNum);
            }
            if ("2".equals(flag)) {
                for (Map<String, String> map : countWaySectionOrMileage) {
                    RanKOperatingCompanyVO rankOperatingCompanyVO = new RanKOperatingCompanyVO();
                    rankOperatingCompanyVO.setOrgId(map.get("orgId"));
                    rankOperatingCompanyVO.setOrgName(map.get("orgName"));
                    rankOperatingCompanyVO.setNum(MapUtil.getDouble(map, "sumMileage"));
                    rankOperatingCompanyVO.setRankType(flag);
                    rankOperatingCompanyVOSByMileage.add(rankOperatingCompanyVO);
                }
                //排序
                List<RanKOperatingCompanyVO> sortedByNum = rankOperatingCompanyVOSByMileage.stream().sorted(Comparator.comparing(RanKOperatingCompanyVO::getNum).reversed()).collect(Collectors.toList());
                sorted.put("sortedBySumMileage", sortedByNum);
            }
        }
        return sorted;
    }

    /**
     * @param typeIds 120100:桥梁,120200:隧道,120300:收费站,120400:服务区,120500:门架,120600:互通立交
     * @description 设施个数排名
     * @date 2021/4/22 17:26
     */
    public Map<String, List<RanKOperatingCompanyVO>> rankByKeyFacilities(Set<String> typeIds) {
        Map<String, List<RanKOperatingCompanyVO>> sorted = new HashMap<String, List<RanKOperatingCompanyVO>>();
        //拿到运营公司
        List<Org> orgs = analysisdao.getOperatingCompanys();
        //默认查询时使用
        Set<String> flags = typeIds;
        if (null == typeIds || 0 == typeIds.size()) {
            flags = new HashSet<String>();
            flags.add("32330100");
            flags.add("32330200");
            flags.add("32330400");
            flags.add("32330500");
            flags.add("32330600");
            flags.add("32330700");
            flags.add("32330711");
            flags.add("32330712");
            flags.add("32330800");
        }
        // 6个MAP<String，List<RanKOperatingCompanyVO>
        Map<String, List<RanKOperatingCompanyVO>> attrMap = new HashMap<String, List<RanKOperatingCompanyVO>>();
        attrMap.put("32330100", new ArrayList<RanKOperatingCompanyVO>());
        attrMap.put("32330200", new ArrayList<RanKOperatingCompanyVO>());
        attrMap.put("32330400", new ArrayList<RanKOperatingCompanyVO>());
        attrMap.put("32330500", new ArrayList<RanKOperatingCompanyVO>());
        attrMap.put("32330600", new ArrayList<RanKOperatingCompanyVO>());
        attrMap.put("32330700", new ArrayList<RanKOperatingCompanyVO>());
        attrMap.put("32330800", new ArrayList<RanKOperatingCompanyVO>());
        List<RanKOperatingCompanyVO> rankOperatingCompanyVOList = new ArrayList<>();

        //List<String> aa = new ArrayList<>();
        //根据运营公司id向下查询(不同设施类型的总个数)
        for (Org org : orgs) {
            List<RanKOperatingCompanyVO> rankOperatingCompanyVOs = analysisdao.sumKeyFacilitiesByOrgId(org.getOrgId());
            if (null != rankOperatingCompanyVOs && 0 < rankOperatingCompanyVOs.size()) {
                rankOperatingCompanyVOList.addAll(rankOperatingCompanyVOs);
            }
        }
        //根据不同设施类型分组=>同一设施类型公司个数情况
        Map<String, List<RanKOperatingCompanyVO>> groupByRankType = rankOperatingCompanyVOList.stream().collect(Collectors.groupingBy(RanKOperatingCompanyVO::getRankType));
        //排序
        for (String flag : flags) {
            boolean isExistRankType = false;
            for (String rankType : groupByRankType.keySet()) {
                if (flag.equals(rankType)) {
                    isExistRankType = true;
                    List<RanKOperatingCompanyVO> rankOperatingCompanyVOs = groupByRankType.get(rankType);
                    List<RanKOperatingCompanyVO> rankTypeSorted = rankOperatingCompanyVOs.stream().sorted(Comparator.comparing(RanKOperatingCompanyVO::getNum).reversed())
                            .collect(Collectors.toList());
                    // 运营公司信息得全，没有的公司统计数补0
                    List<RanKOperatingCompanyVO> appendList = new ArrayList<RanKOperatingCompanyVO>();
                    for (Org org : orgs) {
                        boolean isExist = false;
                        for (RanKOperatingCompanyVO ranKOperatingCompanyVO : rankTypeSorted) {
                            if (org.getOrgId().equals(ranKOperatingCompanyVO.getOrgId())) {
                                isExist = true;
                                break;
                            }
                        }
                        if (!isExist) {
                            RanKOperatingCompanyVO vo = new RanKOperatingCompanyVO();
                            vo.setOrgId(org.getOrgId());
                            vo.setOrgName(org.getOrgName());
                            vo.setNum(0.0);
                            vo.setRankType(flag);
                            appendList.add(vo);
                        }
                    }
                    rankTypeSorted.addAll(appendList);
                    sorted.put(flag, rankTypeSorted);
                }
            }
            // 设施类型统计得全，没有的类型统计数补0
            if (!isExistRankType) {
                List<RanKOperatingCompanyVO> appendVos = new ArrayList<RanKOperatingCompanyVO>();
                for (Org org : orgs) {
                    RanKOperatingCompanyVO vo = new RanKOperatingCompanyVO();
                    vo.setOrgId(org.getOrgId());
                    vo.setOrgName(org.getOrgName());
                    vo.setNum(0.0);
                    vo.setRankType(flag);
                    appendVos.add(vo);
                }
                sorted.put(flag, appendVos);
            }
        }
        List<RanKOperatingCompanyVO> subDict1 = sorted.get("32330711");
        List<RanKOperatingCompanyVO> subDict2 = sorted.get("32330712");
        List<RanKOperatingCompanyVO> subDict3 = sorted.get("32330700");
        if (CollUtil.isNotEmpty(subDict1) && CollUtil.isNotEmpty(subDict3)) {
            for (RanKOperatingCompanyVO orgInfo : subDict1) {
                for (RanKOperatingCompanyVO orgInfo1 : subDict3) {
                    if (orgInfo.getOrgId().equals(orgInfo1.getOrgId())) {
                        orgInfo1.setNum(orgInfo1.getNum() + orgInfo.getNum());
                    }
                }
            }
        }
        if (CollUtil.isNotEmpty(subDict2) && CollUtil.isNotEmpty(subDict3)) {
            for (RanKOperatingCompanyVO orgInfo : subDict2) {
                for (RanKOperatingCompanyVO orgInfo1 : subDict3) {
                    if (orgInfo.getOrgId().equals(orgInfo1.getOrgId())) {
                        orgInfo1.setNum(orgInfo1.getNum() + orgInfo.getNum());
                    }
                }
            }
        }
        sorted.remove("32330711");
        sorted.remove("32330712");
        return sorted;
    }

    /**
     * @description 系统设备数排名
     * @date 2021/4/22 17:26
     * @param: typeIds 110100:监控系统,110200:收费系统,110300:通信系统,110400:供配电系统,110500:隧道机电系统110600:隧道消防系统110700:隧道通风系统,110800:隧道照明系统,110900:门架系统
     */
    public Map<String, List<RanKOperatingCompanyVO>> rankBySys(Set<String> typeIds) {
        List<DictSystem> dictSystemList = baseDictSystemService.list(new LambdaQueryWrapper<DictSystem>().in(DictSystem::getSystemSerial, typeIds));
        HashMap<String, List<RanKOperatingCompanyVO>> result = new HashMap<>();
        if (dictSystemList.isEmpty()) {
            return result;
        }
        List<Org> orgs = analysisdao.getOperatingCompanys();
        dictSystemList.forEach(dict -> {
            List<String> systemIds = new ArrayList<>();
            // systemId 非空时,才去查询系统字典表
            systemIds.add(dict.getSystemId());
            List<DictSystem> childSystemIds = baseDictSystemService.list(new LambdaQueryWrapper<DictSystem>().eq(DictSystem::getParentId, dict.getSystemId()));
            while (!childSystemIds.isEmpty()) {
                List<String> tempIds = childSystemIds.stream().map(DictSystem::getSystemId).collect(Collectors.toList());
                systemIds.addAll(tempIds);
                childSystemIds = baseDictSystemService.list(new LambdaQueryWrapper<DictSystem>().in(DictSystem::getParentId, tempIds));
            }
            List<RanKOperatingCompanyVO> rankList = analysisdao.deviceStatByOrg(systemIds);
            // 求差集
            List<Org> differenceOrgSet = orgs.stream().filter(org -> rankList.stream().map(RanKOperatingCompanyVO::getOrgId).noneMatch(id -> org.getOrgId().equals(id))).collect(Collectors.toList());
            differenceOrgSet.forEach(item -> {
                RanKOperatingCompanyVO ranKOperatingCompanyVO = new RanKOperatingCompanyVO();
                ranKOperatingCompanyVO.setOrgId(item.getOrgId());
                ranKOperatingCompanyVO.setNum(0.0);
                ranKOperatingCompanyVO.setOrgName(item.getOrgName());
                rankList.add(ranKOperatingCompanyVO);
            });
            result.put(dict.getSystemSerial(), rankList);
        });
        return result;
    }

    @Override
    public List<Map<String, String>> getSysType() {
        List<DictSystem> list = baseDictSystemService.list(new LambdaQueryWrapper<DictSystem>().isNull(DictSystem::getParentId));
        return list.stream().map(item -> {
            HashMap<String, String> map = new HashMap<>();
            map.put("dictId", item.getSystemId());
            map.put("name", item.getSystemName());
            map.put("code", item.getSystemSerial());
            return map;
        }).collect(Collectors.toList());
    }
}
