package com.rhy.bdmp.open.modules.org.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.lang.tree.parser.DefaultNodeParser;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bcp.common.domain.vo.LoginUserVo;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.open.modules.assets.domain.bo.UserPermissions;
import com.rhy.bdmp.open.modules.assets.domain.po.User;
import com.rhy.bdmp.open.modules.common.domain.bo.CommonBo;
import com.rhy.bdmp.open.modules.common.domain.vo.TreeNode;
import com.rhy.bdmp.open.modules.common.service.CommonService;
import com.rhy.bdmp.open.modules.org.dao.OrgDao;
import com.rhy.bdmp.open.modules.org.domain.bo.OrgDetailBo;
import com.rhy.bdmp.open.modules.org.domain.bo.OrgListBo;
import com.rhy.bdmp.open.modules.org.domain.bo.OrgTreeBo;
import com.rhy.bdmp.open.modules.org.domain.dto.IPTelDto;
import com.rhy.bdmp.open.modules.org.domain.vo.BaseOrgVo;
import com.rhy.bdmp.open.modules.org.service.IOrgService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author weicaifu
 */
@Service(value = "OrgServiceV1")
public class OrgServiceImpl implements IOrgService {
    public final JSONConfig jsonConfig = new JSONConfig().setIgnoreNullValue(false);

    @Resource(name = "orgDaoV1")
    private OrgDao orgDao;

    @Resource
    private CommonService commonService;

    /**
     * 组织路段设施树
     */
    @Override
    public Object getOrgWayFacTree(CommonBo commonBo) {
        UserPermissions userPermissions = commonService.getUserPermissions(commonBo.getIsUseUserPermissions());
        List<TreeNode> groupNodeList = orgDao.getGroup(userPermissions);
        List<TreeNode> orgNodeList = orgDao.getOrg(userPermissions);

        List<TreeNode> wayNodeList = null;
        if (CollUtil.isNotEmpty(orgNodeList)){
            wayNodeList = orgDao.getWay(userPermissions,orgNodeList);
        }

        List<TreeNode> facNodeList = null;
        if (CollUtil.isNotEmpty(wayNodeList)){
            facNodeList = orgDao.getFac(userPermissions,wayNodeList);
        }
        List<cn.hutool.core.lang.tree.TreeNode<String>> nodeList = new ArrayList<>();
        if (CollUtil.isEmpty(orgNodeList)){
            return null;
        }

        List<TreeNode> tempNodeList = new ArrayList<>();

        if (CollUtil.isNotEmpty(groupNodeList)){
            tempNodeList.addAll(groupNodeList);
        }

        tempNodeList.addAll(orgNodeList);
        tempNodeList.addAll(wayNodeList);
        tempNodeList.addAll(facNodeList);

        boolean groupNotFind = tempNodeList.stream().noneMatch(node -> StrUtil.equals(node.getNodeType(), "0"));

        if (groupNotFind){
            tempNodeList = tempNodeList.stream().map(node -> {
                if (StrUtil.equals(node.getNodeType(),"1")){
                    node.setParentId("0");
                }
                return node;
            }).collect(Collectors.toList());
        }
        else{
            tempNodeList = tempNodeList.stream().map(node -> {
                if (StrUtil.equals(node.getNodeType(),"0")){
                    node.setParentId("0");
                }
                return node;
            }).collect(Collectors.toList());
        }
        tempNodeList.forEach(node ->
                nodeList.add(new cn.hutool.core.lang.tree.TreeNode<String>()
                .setId(node.getId())
                .setName(node.getName())
                .setParentId(node.getParentId())
                .setWeight(node.getSort() == null ? 0 : node.getSort())
                .setExtra(JSONUtil.createObj(jsonConfig)
                        .putOnce("nodeType", node.getNodeType())
                        .putOnce("shortName", node.getShortName())
                        .putOnce("type", node.getType()))
        ));

        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setWeightKey("sort");
        return TreeUtil.build(nodeList, "0", treeNodeConfig, new DefaultNodeParser<>());
    }

    /**
     * 组织树
     */
    @Override
    public Object getOrgTree(OrgTreeBo orgTreeBo) {
        UserPermissions userPermissions = commonService.getUserPermissions(orgTreeBo.getIsUseUserPermissions());
        orgTreeBo.setSearchType(Optional.ofNullable(orgTreeBo.getSearchType()).orElse(1));
        if (CollUtil.isEmpty(orgTreeBo.getCodes())){
            orgTreeBo.setCodes(null);
        }
        Integer searchType = orgTreeBo.getSearchType();

        if (1 != searchType && 2 != searchType && 3 != searchType){
            throw new BadRequestException("搜索类型（searchType）不合法");
        }

        List<TreeNode> orgTreeNodeList = null;
        List<String> ids = null;
        List<TreeNode> temp = new ArrayList<>();
        switch (searchType){
            case 1:
                // 全部节点
                orgTreeNodeList = orgDao.getAllOrg(userPermissions,orgTreeBo);
                if (CollUtil.isNotEmpty(orgTreeNodeList)){
                    temp.addAll(orgTreeNodeList);
                    ids = temp.stream().map(TreeNode::getId).collect(Collectors.toList());
                    orgTreeNodeList = this.getChildren(ids, orgTreeNodeList);
                    ids = temp.stream().map(TreeNode::getParentId).collect(Collectors.toList());
                    orgTreeNodeList = this.getParent(ids,orgTreeNodeList);
                }
                break;
            case 2:
                // 下级节点及本身
                orgTreeNodeList = new ArrayList<>();
                ids = new ArrayList<>();
                // 将传递过来的节点设置为root
                String[] nodeIds = null;
                if (StrUtil.isNotBlank(orgTreeBo.getNodeId())){
                    nodeIds = orgTreeBo.getNodeId().split(",");
                }
                userPermissions.setIsUseUserPermissions(false);
                List<TreeNode> root = orgDao.getNodeById(orgTreeBo,userPermissions,nodeIds);
                if (CollUtil.isEmpty(root)){
                    throw new BadRequestException("节点不存在");
                }
                List<String> parentIds = new ArrayList<>();
                for (TreeNode node : root) {
                    ids.add(node.getId());
                    parentIds.add(node.getParentId());
                }
                orgTreeNodeList.addAll(root);
                orgTreeNodeList = this.getChildren(ids,orgTreeNodeList);
                // 在所有节点中找到顶点
                List<TreeNode> tempList = new ArrayList<>();
                tempList = this.findRootInAllNode(orgTreeNodeList,tempList);
                if (CollUtil.isNotEmpty(tempList)){
                    // 防止去重时，因为随机去重导致root被去除
                    for (TreeNode treeNode : orgTreeNodeList) {
                        boolean isRepeat = tempList.stream().anyMatch(node -> StrUtil.equals(node.getId(),treeNode.getId()));
                        if (isRepeat){
                            treeNode.setParentId("0");
                        }
                    }
                    orgTreeNodeList.addAll(tempList);
                }

                break;
            case 3:
                // 上级点及本身
                orgTreeNodeList = new ArrayList<>();
                ids = new ArrayList<>();
                nodeIds = null;
                if (StrUtil.isNotBlank(orgTreeBo.getNodeId())){
                    nodeIds = orgTreeBo.getNodeId().split(",");
                }
                userPermissions.setIsUseUserPermissions(false);
                List<TreeNode> node = orgDao.getNodeById(orgTreeBo,userPermissions,nodeIds);
                if (CollUtil.isEmpty(node)){
                    throw new BadRequestException("节点不存在");
                }
                for (TreeNode treeNode : node) {
                    ids.add(treeNode.getParentId());
                }
                orgTreeNodeList.addAll(node);
                orgTreeNodeList = this.getParent(ids,orgTreeNodeList);
                break;
            default:
                break;
        }

        if (CollUtil.isEmpty(orgTreeNodeList)){
            return null;
        }

        List<cn.hutool.core.lang.tree.TreeNode<String>> nodeList = new ArrayList<>();

        orgTreeNodeList = orgTreeNodeList.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(
                        Comparator.comparing(
                                TreeNode::getId))), ArrayList::new));

        orgTreeNodeList.forEach(node ->
                nodeList.add(new cn.hutool.core.lang.tree.TreeNode<String>()
                        .setId(node.getId())
                        .setName(node.getName())
                        .setParentId(node.getParentId())
                        .setWeight(node.getSort())
                        .setExtra(JSONUtil.createObj(jsonConfig)
                                .putOnce("nodeType", node.getNodeType())
                                .putOnce("shortName", node.getShortName())
                                .putOnce("type", node.getType()))
                ));

        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setWeightKey("sort");
        return TreeUtil.build(nodeList, "0", treeNodeConfig, new DefaultNodeParser<>());
    }

    /**
     * 组织列表
     * <p>currentPage == -1时 不分页</p>
     */
    @Override
    public Object getOrgList(OrgListBo orgListBo) {
        UserPermissions userPermissions = commonService.getUserPermissions(orgListBo.getIsUseUserPermissions());
        orgListBo.setCurrentPage(Optional.ofNullable(orgListBo.getCurrentPage()).orElse(-1));

        if (CollUtil.isEmpty(orgListBo.getCodes())){
            orgListBo.setCodes(null);
        }
        if (-1 == orgListBo.getCurrentPage()){
            // list
            return orgDao.getOrgList(orgListBo,userPermissions);
        }
        else{
            // page
            Integer currentPage = orgListBo.getCurrentPage();
            Integer pageSize = orgListBo.getPageSize();
            if ((currentPage < 0 && currentPage != -1) || currentPage == 0){
                throw new BadRequestException("当前页 currentPage 不合法");
            }

            if (null != pageSize && pageSize <= 0){
                throw new BadRequestException("页大小 pageSize 不合法");
            }

            Page<BaseOrgVo> baseOrgVoPage = new Page<>();
            baseOrgVoPage.setCurrent(currentPage);
            baseOrgVoPage.setSize(pageSize);
            Page<BaseOrgVo> orgPage = orgDao.getOrgPage(baseOrgVoPage,orgListBo,userPermissions);
            return new PageUtil<BaseOrgVo>(orgPage);
        }
    }

    /**
     * 组织详情
     */
    @Override
    public Object detail(OrgDetailBo detailBo) {
        return orgDao.getOrgById(detailBo);
    }

    @Override
    public Object ipTelStat() {
        String orgId = this.getUserCompanyId();
        List<IPTelDto> ipTelDtoList = orgDao.getIPTelRelationList(orgId);
        if (CollUtil.isEmpty(ipTelDtoList)){
            return null;
        }
        Map<String, List<IPTelDto>> orgGroup = ipTelDtoList.stream().collect(Collectors.groupingBy(IPTelDto::getOrgId));
        List<Map<String, Object>> resList = new ArrayList<>();
        List<Map<String, Object>> wayTempList = null;
        Map<String,Object> orgTemp = null;
        Map<String,Object> wayTemp = null;
        Map<String,Object> subtotal = null;
        for (Map.Entry<String, List<IPTelDto>> orgEntry : orgGroup.entrySet()) {
            List<IPTelDto> orgIPTelTemp = orgEntry.getValue();
            orgTemp = new HashMap<>();
            IPTelDto orgIpTelDto = orgIPTelTemp.get(0);
            orgTemp.put("orgId",orgIpTelDto.getOrgId());
            orgTemp.put("orgName",orgIpTelDto.getOrgName());
            orgTemp.put("orgShortName",orgIpTelDto.getOrgShortName());
            Map<String, List<IPTelDto>> wayGroup = orgIPTelTemp.stream().collect(Collectors.groupingBy(IPTelDto::getWayId));
            DecimalFormat decimalFormat = new DecimalFormat("##.00%");
            wayTempList = new ArrayList<>();
            for (Map.Entry<String, List<IPTelDto>> wayEntry : wayGroup.entrySet()) {
                List<IPTelDto> wayIPTelTemp = wayEntry.getValue();
                wayTemp = new HashMap<>();
                IPTelDto wayIpTelDto = wayIPTelTemp.get(0);
                wayTemp.put("wayId",wayIpTelDto.getWayId());
                wayTemp.put("wayName",wayIpTelDto.getWayName());
                // 路段ip电话接入数
                wayTemp.put("accessNumber",wayIPTelTemp.size());
                // 路段ip电话在线数
                List<IPTelDto> onlineList = wayIPTelTemp.stream().filter(dto -> dto.getStatus() == 1).collect(Collectors.toList());
                int onlineNumber = onlineList.size();
                wayTemp.put("onlineNumber",onlineNumber);
                // 路段ip电话在线率
                if (onlineNumber == 0){
                    wayTemp.put("onlineRate","0%");
                }
                else {
                    String onlineRate = decimalFormat.format((onlineNumber*1.0) / (wayIPTelTemp.size()*1.0));
                    wayTemp.put("onlineRate",onlineRate);
                }
                wayTempList.add(wayTemp);
            }
            orgTemp.put("wayStatList",wayTempList);
            // 小计
            subtotal = new HashMap<String,Object>();
            // 公司ip电话接入数
            subtotal.put("accessNumber",orgIPTelTemp.size());
            // 公司ip电话在线数
            List<IPTelDto> onlineList = orgIPTelTemp.stream().filter(dto -> dto.getStatus() == 1).collect(Collectors.toList());
            int onlineNumber = onlineList.size();
            subtotal.put("onlineNumber",onlineNumber);
            // 公司ip电话在线率
            if (onlineNumber == 0){
                subtotal.put("onlineRate","0%");
            }
            else {
                String onlineRate = decimalFormat.format((onlineNumber*1.0) / (orgIPTelTemp.size()*1.0));
                subtotal.put("onlineRate",onlineRate);
            }
            orgTemp.put("subtotal",subtotal);
            resList.add(orgTemp);
        }
        return resList;
    }

    @Override
    public SXSSFWorkbook exportIpTelStat() {

        //表头
        String[] header ={"序号","经营单位","路段名称","接入数(个)","在线数(个)","在线率"};

        SXSSFWorkbook wb = new SXSSFWorkbook();

        CellStyle headerStyle = createCellStyle(wb,"13","微软雅黑","header");
        CellStyle otherStyle = createCellStyle(wb,"10","微软雅黑","other");
        // 创建一个sheet
        SXSSFSheet sh = wb.createSheet("IP电话在线率");
        //sh.trackAllColumnsForAutoSizing();

        int rowNo = 0 ;
        Row row = sh.createRow(rowNo++);
        for (int i = 0; i < header.length; i++) {
            row.createCell(i).setCellValue(header[i]);
            row.getCell(i).setCellStyle(headerStyle);
            //sh.autoSizeColumn(i,true);
        }
        sh.setColumnWidth(1,31*255);
        sh.setColumnWidth(2,31*255);
        sh.setColumnWidth(3,14*255);
        sh.setColumnWidth(4,14*255);
        sh.setColumnWidth(5,14*255);


        String orgId = this.getUserCompanyId();
        //表体
        List<IPTelDto> ipTelDtoList = orgDao.getIPTelRelationList(orgId);
        //根据公司分组
        Map<String, List<IPTelDto>> orgByGroup = ipTelDtoList.stream().collect(Collectors.groupingBy(IPTelDto::getOrgId));
        int sort = 1 ;//序号
        for(List<IPTelDto> orgGroup : orgByGroup.values()){
            row = sh.createRow(rowNo);
            //序号
            setCellValueAndStyle(row,0,sort++ +"",otherStyle);
            //经营单位
            setCellValueAndStyle(row,1,orgGroup.get(0).getOrgShortName(),otherStyle);

            //根据路段分组
            Map<String, List<IPTelDto>> wayByGroup = orgGroup.stream().collect(Collectors.groupingBy(IPTelDto::getWayId));

            int wayIndex = 0;
            for( List<IPTelDto> wayGroup : wayByGroup.values()){

                IPTelDto way = wayGroup.get(0);
                if(wayIndex!=0) {
                    row = sh.createRow(rowNo+wayIndex);
                }
                //路段名称
                setCellValueAndStyle(row, 2, way.getWayName(), otherStyle);
                //接入个数
                setCellValueAndStyle(row, 3, wayGroup.size() + "", otherStyle);
                //在线数
                Long onLineNum = wayGroup.stream().filter(dto -> dto.getStatus() == 1).count();
                setCellValueAndStyle(row, 4, onLineNum + "", otherStyle);

                //在线率
                setCellValueAndStyle(row, 5, new BigDecimal(onLineNum).multiply(new BigDecimal("100")).divide(new BigDecimal(wayGroup.size()), 2, RoundingMode.HALF_UP) + "%", otherStyle);

                wayIndex++;
            }
            //合计
            row = sh.createRow(rowNo+wayByGroup.size());
            long onLineNum = orgGroup.stream().filter(dto -> dto.getStatus() == 1).count();
            setCellValueAndStyle(row,1,"合计",otherStyle);
            setCellValueAndStyle(row,3,orgGroup.size()+"",otherStyle);
            setCellValueAndStyle(row,4,onLineNum+"",otherStyle);
            setCellValueAndStyle(row,5,new BigDecimal(onLineNum).multiply(new BigDecimal("100")).divide(new BigDecimal(orgGroup.size()), 2, RoundingMode.HALF_UP) + "%",otherStyle);


            //合并单元格
            CellRangeAddress regionTotal  = new CellRangeAddress(rowNo+wayByGroup.size(), rowNo+wayByGroup.size(), 1, 2);
            sh.addMergedRegion(regionTotal);
            CellRangeAddress regionIndex  = new CellRangeAddress(rowNo, rowNo+wayByGroup.size(), 0, 0);
            sh.addMergedRegion(regionIndex);
            //防止只合并一个单元格报错
            if(wayByGroup.size()>1) {
                CellRangeAddress regionCompany = new CellRangeAddress(rowNo, rowNo + wayByGroup.size() - 1, 1, 1);
                sh.addMergedRegion(regionCompany);
            }
            rowNo += wayByGroup.size()+1;
        }
        return wb;
    }

    private String getUserCompanyId() {
        LoginUserVo currentUser = WebUtils.getCurrentUser();
        User user = commonService.getCurrentUser();
        String orgId = currentUser.getOperationCompanyId();
        if ((null != user.getIsAdmin() && 1 == user.getIsAdmin()) ||
                (StrUtil.isBlank(currentUser.getOperationCompanyId()) && StrUtil.isNotBlank(currentUser.getOperationGroupId()))){
            orgId = "";
        }
        return orgId;
    }


    private static void setCellValueAndStyle(Row row,int cellIndex,String value,CellStyle style){
        Cell cell = row.createCell(cellIndex);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }

    private static CellStyle createCellStyle(SXSSFWorkbook wb, String fontSize, String fontName, String boo) {
        // 创建自定义样式类
        CellStyle style = wb.createCellStyle();
        // 创建自定义字体类
        Font font = wb.createFont();
        // 设置字体样式
        font.setFontName(fontName);
        // 设置字体大小
        font.setFontHeightInPoints(Short.parseShort(fontSize));
        // 我这个版本的POI没找到网上的HSSFCellStyle
        // 设置对齐方式
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        // 数据内容设置边框实在太丑,容易看瞎眼睛,我帮你们去掉了
        if ("header".equals(boo)) {
            // 设置边框
            style.setBorderBottom(BorderStyle.MEDIUM);
            style.setBorderLeft(BorderStyle.MEDIUM);
            style.setBorderRight(BorderStyle.MEDIUM);
            style.setBorderTop(BorderStyle.MEDIUM);
            // 表头字体加粗
            font.setBold(true);
        }
        style.setFont(font);
        return style;
    }



    /**
     * 查找父节点
     * @param ids 父节点id
     */
    private List<TreeNode> getParent(List<String> ids, List<TreeNode> orgTreeNodeList) {
        List<TreeNode> parent = orgDao.getParent(ids);
        if (CollUtil.isNotEmpty(parent)){
            orgTreeNodeList.addAll(parent);
            ids = parent.stream().map(TreeNode::getParentId).collect(Collectors.toList());
            this.getParent(ids,orgTreeNodeList);
        }
        return orgTreeNodeList;
    }

    /**
     * 查找子节点
     * @param ids 子节点id
     */
    private List<TreeNode> getChildren(List<String> ids,List<TreeNode> treeNodeList){
        List<TreeNode> children = orgDao.getChildren(ids);
        if (CollUtil.isNotEmpty(children)){
            treeNodeList.addAll(children);
            ids = children.stream().map(TreeNode::getId).collect(Collectors.toList());
            this.getChildren(ids,treeNodeList);
        }
        return treeNodeList;
    }

    /**
     * 在不知道起点和结束点的数据中找到根节点
     */
    private List<TreeNode> findRootInAllNode(List<TreeNode> orgTreeNodeList, List<TreeNode> tempList) {
        if (CollUtil.isEmpty(orgTreeNodeList)){
            return tempList;
        }
        List<TreeNode> tempList1 = new ArrayList<>();
        tempList1.addAll(orgTreeNodeList);

        for (TreeNode treeNode : orgTreeNodeList) {
            boolean noneParent = tempList1.stream().noneMatch(node -> StrUtil.equals(node.getId(), treeNode.getParentId()));
            if (noneParent){
                treeNode.setParentId("0");
                tempList.add(treeNode);
            }
        }
        return tempList;
    }
}


