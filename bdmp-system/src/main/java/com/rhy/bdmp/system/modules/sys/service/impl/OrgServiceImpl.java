package com.rhy.bdmp.system.modules.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.domain.vo.NodeVo;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.Query;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.base.modules.assets.domain.po.Waysection;
import com.rhy.bdmp.base.modules.sys.domain.po.Org;
import com.rhy.bdmp.base.modules.sys.domain.po.User;
import com.rhy.bdmp.base.modules.sys.domain.po.WayMapping;
import com.rhy.bdmp.base.modules.sys.service.IBaseUserService;
import com.rhy.bdmp.base.modules.sys.service.IBaseWayMappingService;
import com.rhy.bdmp.system.modules.assets.dao.WaySectionDao;
import com.rhy.bdmp.system.modules.sys.common.service.UserPermissions;
import com.rhy.bdmp.system.modules.sys.dao.OrgDao;
import com.rhy.bdmp.system.modules.sys.dao.UserDao;
import com.rhy.bdmp.system.modules.sys.domain.vo.CCPWayVo;
import com.rhy.bdmp.system.modules.sys.domain.vo.OrgVo;
import com.rhy.bdmp.system.modules.sys.service.OrgService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class OrgServiceImpl implements OrgService {

    public final JSONConfig jsonConfig = new JSONConfig().setIgnoreNullValue(false);

    @Resource
    private OrgDao orgDao;

    @Resource
    private UserDao userDao;

    @Resource
    private IBaseUserService baseUserService;

    @Resource
    private WaySectionDao waySectionDao;

    @Resource
    private UserPermissions userPermissions;

    @Resource
    private IBaseWayMappingService baseWayMappingService;

    /**
     * @Description: 删除组织机构
     * @Author: dongyu
     * @Date: 2021/4/14
     */
    @Override
    public void delete(Set<String> orgIds) {
        if (CollectionUtils.isNotEmpty(orgIds)) {
            //如果只传一个id，判断id是否存在
            if (orgIds.size() == 1) {
                if (orgDao.selectById(String.valueOf(orgIds.toArray()[0])) == null) {
                    throw new BadRequestException("组织ID不存在");
                }
            }
            for (String orgId : orgIds) {
                //判断组织下面是否有子组织
                //查找组织及其子组织集合
                List<OrgVo> orgVoChildrenIdList = orgDao.findOrgChildren(orgId);
                if (CollectionUtils.isNotEmpty(orgVoChildrenIdList) && orgVoChildrenIdList.size() > 1) {
                    //组织下有子组织
                    throw new BadRequestException("组织存在子组织");
                }
            }
            //判断组织下面是否有用户
            List<User> userVoList = userDao.selectList(new QueryWrapper<User>().eq("org_id", orgIds));
            if (CollectionUtils.isNotEmpty(userVoList)) {
                //组织有关联用户
                throw new BadRequestException("组织存在关联用户");
            }
            //判断组织下是否有路段
            List<Waysection> waysections = waySectionDao.selectList(new QueryWrapper<Waysection>().eq("manage_id", orgIds));
            if (CollectionUtils.isNotEmpty(waysections)) {
                throw new BadRequestException("组织存在关联路段");
            }
            //删除组织（根据组织id集合）
            orgDao.deleteBatchIds(orgIds);
        }
    }

    /**
     * @Description: 查询组织关系
     * @Author: dongyu
     * @Date: 2021/4/15
     */
    @Override
    public List<NodeVo> findOrgTree() {
        // todo 应急改动： 解决不是管理员，能看到所有用户的问题
        List<NodeVo> result = new ArrayList<>();
        //查找所有组织
        String userId = WebUtils.getUserId();
        User user = baseUserService.getById(userId);
        if (null == user){
            throw new BadRequestException("用户不存在");
        }
        Org org = orgDao.selectById(user.getOrgId());
        List<Org> orgVoList = orgDao.selectList(new QueryWrapper<Org>().orderByAsc("sort").orderByDesc("create_time"));
        if (null != user.getIsAdmin() && 1 == user.getIsAdmin()){
            if (CollectionUtils.isNotEmpty(orgVoList)) {
                for (Org orgVo : orgVoList) {
                    NodeVo nodeVo = new NodeVo();
                    nodeVo.setId(orgVo.getOrgId());
                    nodeVo.setLabel(orgVo.getOrgShortName());
                    nodeVo.setValue(orgVo.getOrgId());
                    nodeVo.setParentId(orgVo.getParentId());
                    nodeVo.setNoteType("org");
                    nodeVo.setMoreInfo(orgVo);
                    result.add(nodeVo);
                }
            }
        }
        else {
            List<Org> temp = userPermissions.getOrg(org);
            if (CollUtil.isNotEmpty(temp)){
                for (Org orgVo : temp) {
                    NodeVo nodeVo = new NodeVo();
                    nodeVo.setId(orgVo.getOrgId());
                    nodeVo.setLabel(orgVo.getOrgShortName());
                    nodeVo.setValue(orgVo.getOrgId());
                    nodeVo.setParentId(orgVo.getParentId());
                    nodeVo.setNoteType("org");
                    nodeVo.setMoreInfo(orgVo);
                    result.add(nodeVo);
                }
            }
        }
        return result;
    }

    @Override
    public int create(Org org) {
        if (StrUtil.isNotBlank(org.getOrgId())) {
            throw new BadRequestException("组织ID已存在，不能做新增操作");
        }
        //组织名称不能为空
/*        if (StrUtil.isBlank(org.getOrgName())) {
            throw new BadRequestException("组织名称不能为空");
        }*/
        //parentId是否存在
        if (StrUtil.isNotBlank(org.getParentId())) {
            if (orgDao.selectById(org.getParentId()) == null) {
                throw new BadRequestException("组织parentId不存在");
            }
        }
        //datastatusid默认为1
        if (org.getDatastatusid() == null) {
            org.setDatastatusid(1);
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        org.setCreateBy(currentUser);
        org.setCreateTime(currentDateTime);
        org.setUpdateBy(currentUser);
        org.setUpdateTime(currentDateTime);
        int result = orgDao.insert(org);
        return result;
    }

    /**
     * @Description: 修改组织
     * @Author: dongyu
     * @Date: 2021/4/20
     */
    @Override
    public int update(Org org) {
        if (StrUtil.isBlank(org.getOrgId())) {
            throw new BadRequestException("组织ID不能为空");
        } else {
            //组织ID不存在
            if (orgDao.selectById(org.getOrgId()) == null) {
                throw new BadRequestException("组织ID不存在");
            }
        }
        //组织名称不能为空
/*        if ("".equals(org.getOrgName())) {
            throw new BadRequestException("组织名称不能为空");
        }*/
        //parentId是否存在
        if (StrUtil.isNotBlank(org.getParentId())) {
            if (orgDao.selectById(org.getParentId()) == null) {
                throw new BadRequestException("组织parentId不存在");
            }
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        org.setUpdateBy(currentUser);
        org.setUpdateTime(currentDateTime);
        int result = orgDao.updateById(org);
        return result;
    }

    /*
     * 组织机构列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public Object list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<Org> query = new Query<Org>(queryVO);
            Page<Org> page = query.getPage();
            QueryWrapper<Org> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                page = orgDao.selectPage(page, queryWrapper);
                //获取Org
                List<Org> orgList = page.getRecords();
                //Org转换成OrgVo
                List<OrgVo> orgVoList = convertVo(orgList);
                Page<OrgVo> orgVoPage = new Page<>();
                //Org的分页信息复制到OrgVo的分页信息
                BeanUtils.copyProperties(page, orgVoPage);
                orgVoPage.setRecords(orgVoList);
                return orgVoPage;
            }
            List<Org> entityList = orgDao.selectList(queryWrapper);
            return convertVo(entityList);
        }
        //2、无查询条件
        return convertVo(orgDao.selectList(new QueryWrapper<Org>().orderByAsc("sort").orderByDesc("create_time")));
    }

    /**
     * 查看组织机构(根据ID)
     *
     * @param orgId
     * @return
     */
    @Override
    public OrgVo detail(String orgId) {
        if (!StrUtil.isNotBlank(orgId)) {
            throw new BadRequestException("组织ID不能为空");
        }
        Org org = orgDao.selectById(orgId);
        if (org != null) {
            List<Org> orgs = new ArrayList<>();
            orgs.add(org);
            return convertVo(orgs).get(0);
        }
        return null;
    }


    /**
     * 通过orgId向下查找包含的所有orgId及自身
     *
     * @param orgId  机构ID
     * @param isSelf 是否包含自身
     * @return
     */
    @Override
    public Set<String> getOrgIdsByDown(String orgId, Boolean isSelf) {
        Set<String> result = new HashSet<>();
        if (isSelf) {
            result.add(orgId);
        }
        getOrgIdsByDown(orgId, result);
        return result;
    }

    /**
     * Org转换成OrgVo
     *
     * @param orgList
     * @return
     */
    public List<OrgVo> convertVo(List<Org> orgList) {
        //组织列表
        List<Org> orgs = orgDao.selectList(new QueryWrapper<Org>());
        //组织Vo列表
        List<OrgVo> orgVoList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(orgList)) {
            for (Org org : orgList) {
                OrgVo orgVo = new OrgVo();
                BeanUtils.copyProperties(org, orgVo);
                if (CollectionUtils.isNotEmpty(orgs)) {
                    //上级组织名称
                    orgs.forEach(o -> {
                        if (o.getOrgId().equals(org.getParentId())) {
                            orgVo.setParentName(o.getOrgName());
                        }
                    });
                }
                orgVoList.add(orgVo);
            }
        }
        return orgVoList;
    }

    /**
     * 根据父节点获取机构列表
     *
     * @param queryVO 查询条件
     * @return
     */
    public List<OrgVo> listByParentId(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<Org> query = new Query<Org>(queryVO);
            QueryWrapper<Org> queryWrapper = query.getQueryWrapper();

            Map<String, Object> paramsMap = queryVO.getParamsMap();
            String parentId = MapUtil.getStr(paramsMap, "parentId");
            String hideNodeIds = MapUtil.getStr(paramsMap, "hideNodeIds");

            // todo 应急改动： 解决不是管理员，能看到所有用户的问题
            String userId = WebUtils.getUserId();
            User user = baseUserService.getById(userId);
            if (StrUtil.isBlank(parentId) && (null == user.getIsAdmin() || 1 != user.getIsAdmin())){
                parentId = user.getOrgId();
                queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(Org::getOrgId,parentId);
                List<OrgVo> orgVoList = orgDao.listHasChildByQueryWraper(queryWrapper);
                List<OrgVo> orgVos = new ArrayList<>();
                if (!StrUtil.isBlank(hideNodeIds) && null != orgVoList && 0 < orgVoList.size()){
                    for (OrgVo orgVo : orgVoList){
                        if (-1 == ("," + hideNodeIds + ",").indexOf("," + orgVo.getOrgId() + ",")){
                            orgVos.add(orgVo);
                        }
                    }
                    orgVoList = orgVos;
                }
                return orgVoList;
            }

            if (StrUtil.isNotBlank(parentId)) {
                queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(Org::getParentId, parentId).orderByAsc(Org::getSort).orderByDesc(Org::getCreateTime);
            }
            else {
                if (null == queryWrapper.getExpression() || (0 == queryWrapper.getExpression().getNormal().size() && StrUtil.isBlank(queryWrapper.getExpression().getSqlSegment()))) {
                    queryWrapper = new QueryWrapper<>();
                    queryWrapper.lambda().nested(i -> i.isNull(Org::getParentId).or().eq(Org::getParentId, "")).orderByAsc(Org::getSort).orderByDesc(Org::getCreateTime);
                }
            }
            List<OrgVo> orgVoList = orgDao.listHasChildByQueryWraper(queryWrapper);
            List<OrgVo> orgVos = new ArrayList<>();
            if (!StrUtil.isBlank(hideNodeIds) && null != orgVoList && 0 < orgVoList.size()){
                for (OrgVo orgVo : orgVoList){
                    if (-1 == ("," + hideNodeIds + ",").indexOf("," + orgVo.getOrgId() + ",")){
                        orgVos.add(orgVo);
                    }
                }
                orgVoList = orgVos;
            }
            return orgVoList;
        }
        //2、无查询条件
        QueryWrapper<Org> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().nested(i -> i.isNull(Org::getParentId).or().eq(Org::getParentId, "")).orderByAsc(Org::getSort).orderByDesc(Org::getCreateTime);
        return orgDao.listHasChildByQueryWraper(queryWrapper);
    }

    /**
     * 查询组织:根据ID获取同级与上级数据
     *
     * @param ids       组织ID字符串,多个英文逗号分隔
     * @param hasSelf   是否包含参数ID节点
     * @return
     */
    public List<Tree<String>> getSuperior(String ids, Boolean hasSelf) {
        List<TreeNode<String>> nodeList = new ArrayList<>();
        if (StrUtil.isBlank(ids)){
            throw new BadRequestException("组织ID不能为空");
        }
        String[] idArray = ids.split(",");
        Set<OrgVo> orgVoSet = new LinkedHashSet<>();
        for (String id : idArray) {
            Org org = orgDao.selectById(id);
            List<OrgVo> orgList = getSuperior(org, new ArrayList<>());
            orgVoSet.addAll(orgList);
        }
        if (!hasSelf) {
            Set<OrgVo> orgVoTempSet = new LinkedHashSet<>();
            for (OrgVo orgVo : orgVoSet) {
                if (-1 == ("," + ids + ",").indexOf("," + orgVo.getOrgId() + ",")){
                    orgVoTempSet.add(orgVo);
                }
            }
            orgVoSet = orgVoTempSet;
        }
        orgVoSet.forEach(orgVo ->
                nodeList.add(new TreeNode<String>()
                        .setId(orgVo.getOrgId())
                        .setName(orgVo.getOrgName())
                        .setParentId(StrUtil.isBlank(orgVo.getParentId()) ? "0" : orgVo.getParentId()) //parentId为null或空字符时,无法统一根节点标识
                        .setExtra(JSONUtil.createObj(jsonConfig)
                                .putOnce("nodeType", StrUtil.isBlank(orgVo.getOrgType()) ? "暂无" : orgVo.getOrgType())
                                .putOnce("orgId", orgVo.getOrgId())
                                .putOnce("shortName", orgVo.getOrgShortName())
                                .putOnce("hasChild", orgVo.getHasChild())
                        )));
        return TreeUtil.build(nodeList, "0");
    }

    /**
     * 获取集控点下的所有路段
     */
    @Override
    public List<CCPWayVo> getCCPWay(String orgId) {
        Org org = orgDao.selectById(orgId);
        if (!StrUtil.equals("000320",org.getOrgType())){
            throw new BadRequestException("仅支持集控点映射路段");
        }
        // 查找集控点下的所有收费站
        List<CCPWayVo> ccpWayList = orgDao.getCCPWayList(orgId);
        ccpWayList = ccpWayList.stream().sorted(Comparator.comparing(CCPWayVo::getOrgName)).collect(Collectors.toList());
        return ccpWayList;
    }

    /**
     * 获取集控点与路段的映射关系
     */
    @Override
    public List<CCPWayVo> getCCPWayMapping(String orgId) {
        return orgDao.getCCPWayMapping(orgId);
    }

    /**
     * 保存集控点与路段的映射关系
     */
    @Override
    public void ccpWayMappingSave(List<WayMapping> wayMappingList,String orgId) {
        if (CollUtil.isEmpty(wayMappingList)){
            throw new BadRequestException("映射关系不为空");
        }
        for (WayMapping wayMapping : wayMappingList) {
            if (StrUtil.isBlank(wayMapping.getOrgId())){
                throw new BadRequestException("组织id不为空");
            }
            if (StrUtil.isBlank(wayMapping.getWaysectionId())){
                throw new BadRequestException("路段id不为空");
            }
        }
        // 如果id不为空，checked=false,且deleted为true,则删除
        Set<String> deletedIds = wayMappingList
                .stream()
                .filter(wayMapping -> StrUtil.isNotBlank(wayMapping.getId()) && wayMapping.isDeleted() && !wayMapping.isChecked())
                .map(WayMapping::getId)
                .collect(Collectors.toSet());
        if (CollUtil.isNotEmpty(deletedIds)){
            baseWayMappingService.delete(deletedIds);
        }

        // id不为空,checked=true,且deleted为true的则更新
        List<WayMapping> updateData = wayMappingList
                .stream()
                .filter(wayMapping -> StrUtil.isNotBlank(wayMapping.getId()) && !wayMapping.isDeleted() && wayMapping.isChecked())
                .map(wayMapping -> {
                    wayMapping.setUpdateTime(new Date());
                    return wayMapping;
                })
                .collect(Collectors.toList());

        // id为空，checked=true的为新增
        List<WayMapping> insertData = wayMappingList
                .stream()
                .filter(wayMapping -> StrUtil.isEmpty(wayMapping.getId()) && wayMapping.isChecked())
                .map(wayMapping -> {
                    wayMapping.setCreateTime(new Date());
                    wayMapping.setCreateBy(WebUtils.getUserId());
                    wayMapping.setUpdateTime(new Date());
                    wayMapping.setUpdateBy(WebUtils.getUserId());
                    return wayMapping;
                }).collect(Collectors.toList());
        List<WayMapping> temp = new ArrayList<>();
        if (CollUtil.isNotEmpty(updateData)){
            temp.addAll(updateData);
        }
        if (CollUtil.isNotEmpty(insertData)){
            temp.addAll(insertData);
        }
        baseWayMappingService.saveOrUpdateBatch(temp);
    }

    // 递归获取兄弟节点及父节点（含父节点兄弟节点）
    private List<OrgVo> getSuperior(Org org, List<OrgVo> orgList) {
        if (null == org){
            return orgList;
        }
        if (StrUtil.isBlank(org.getParentId())) {
            QueryWrapper<Org> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().nested(t -> t.isNull(Org::getParentId).or().eq(Org::getParentId, "")).orderByAsc(Org::getSort).orderByDesc(Org::getCreateTime);
            orgList.addAll(orgDao.listHasChildByQueryWraper(queryWrapper));
            return orgList;
        }
        QueryWrapper<Org> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Org::getParentId, org.getParentId()).orderByAsc(Org::getSort).orderByDesc(Org::getCreateTime);
        orgList.addAll(orgDao.listHasChildByQueryWraper(queryWrapper));
        return getSuperior(orgDao.selectById(org.getParentId()), orgList);
    }

    private void getOrgIdsByDown(String orgId, Set<String> result) {
        List<Org> orgList = orgDao.selectList(new QueryWrapper<Org>().lambda().eq(Org::getParentId, orgId));
        for (Org org : orgList) {
            result.add(org.getOrgId());
            getOrgIdsByDown(org.getOrgId(), result);
        }
    }


}
