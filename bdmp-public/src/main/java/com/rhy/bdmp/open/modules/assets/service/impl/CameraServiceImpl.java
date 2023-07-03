package com.rhy.bdmp.open.modules.assets.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bdmp.open.modules.assets.dao.CameraDao;
import com.rhy.bdmp.open.modules.assets.domain.bo.UserPermissions;
import com.rhy.bdmp.open.modules.assets.domain.po.CameraResourceTl;
import com.rhy.bdmp.open.modules.assets.domain.po.CameraResourceYt;
import com.rhy.bdmp.open.modules.assets.domain.vo.CameraDirVo;
import com.rhy.bdmp.open.modules.assets.domain.vo.RequestCameraDirVo;
import com.rhy.bdmp.open.modules.assets.service.IAssetsPermissionsTreeService;
import com.rhy.bdmp.open.modules.assets.service.ICameraService;
import com.rhy.bdmp.open.modules.system.dao.MicroUserDao;
import org.springframework.stereotype.Service;
import com.rhy.bdmp.open.modules.assets.domain.po.CameraResourceSy;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Author: yanggj
 * @Description: 摄像头业务处理类
 * @Date: 2021/9/28 8:56
 * @Version: 1.0.0
 */
@Service
public class CameraServiceImpl implements ICameraService {

    public final JSONConfig jsonConfig = new JSONConfig().setIgnoreNullValue(false);

    @Resource
    private CameraDao cameraDao;
    @Resource
    private MicroUserDao microUserDao;
    @Resource
    private IAssetsPermissionsTreeService assetsPermissionsTreeService;
    @Resource
    private IAssetsPermissionsTreeService permissionsService;

    @Override
    public List<CameraResourceYt> listCameraResourceYt(Boolean isUseUserPermissions, String orgId, String nodeType) {
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();
        Integer userType = null;
        if (StrUtil.isNotBlank(userId)){
            userType = microUserDao.getMicroUserInfo(userId).getUserType();
        }
        return cameraDao.listCameraResourceYt(isUseUserPermissions, userId, userType, dataPermissionsLevel, orgId, nodeType);
    }

    @Override
    public List<Map<String, Object>> listCameraIdYt(Boolean isUseUserPermissions, String orgId, String nodeType) {
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();
        Integer userType = null;
        if (StrUtil.isNotBlank(userId)){
            userType = microUserDao.getMicroUserInfo(userId).getUserType();
        }
        return cameraDao.listCameraIdYt(isUseUserPermissions, userId, userType, dataPermissionsLevel, orgId, nodeType);
    }

    @Override
    public List<CameraResourceSy> listCameraResourceSy(Boolean isUseUserPermissions, String orgId, String nodeType) {
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();
        Integer userType = null;
        if (StrUtil.isNotBlank(userId)){
            userType = microUserDao.getMicroUserInfo(userId).getUserType();
        }
        return cameraDao.listCameraResourceSy(isUseUserPermissions, userId, userType, dataPermissionsLevel, orgId, nodeType);
    }

    @Override
    public CameraResourceYt selectCameraYtById(String id) {
        return cameraDao.selectCameraYtById(id);
    }

    @Override
    public CameraResourceSy selectCameraSyById(String id) {
        return cameraDao.selectCameraSyById(id);
    }

    @Override
    public List<CameraResourceTl> listCameraResourceTl(Boolean isUseUserPermissions, String orgId, String nodeType) {
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();

        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();
        Integer userType = null;
        if (StrUtil.isNotBlank(userId)){
            userType = microUserDao.getMicroUserInfo(userId).getUserType();
        }
        return cameraDao.listCameraResourceTl(isUseUserPermissions, userId, userType, dataPermissionsLevel, orgId, nodeType);
    }

    @Override
    public List<Map<String, Object>> listCameraIdTl(Boolean isUseUserPermissions, String orgId, String nodeType) {
        UserPermissions userPermissions = assetsPermissionsTreeService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();
        Integer userType = null;
        if (StrUtil.isNotBlank(userId)){
            userType = microUserDao.getMicroUserInfo(userId).getUserType();
        }
        return cameraDao.listCameraIdTl(isUseUserPermissions, userId, userType, dataPermissionsLevel, orgId, nodeType);
    }

    @Override
    public CameraResourceTl getCameraResourceTlById(String id) {
        return cameraDao.getCameraResourceTlById(id);
    }

    @Override
    public List<Tree<String>> getCameraDirYt(RequestCameraDirVo requestCameraDirVo) {
        List<TreeNode<String>> nodeList = new ArrayList<>();
        List<CameraDirVo> cameraDirVoList = new ArrayList<>();
        String parentId = "";
        Boolean isUseUserPermissions = true;
        String nodeId = null;
        Integer nodeType = null;
        if (null != requestCameraDirVo){
            isUseUserPermissions = requestCameraDirVo.getIsUseUserPermissions();
            nodeId = requestCameraDirVo.getOrgId();
            nodeType = requestCameraDirVo.getNodeType();
        }
        // 校验参数
        if ((StrUtil.isBlank(nodeId) != (null == nodeType))) {
            throw new BadRequestException("orgId 和 nodeType 必须同时为空或同时非空");
        }
        UserPermissions userPermissions = permissionsService.getUserPermissions(isUseUserPermissions);
        String userId = userPermissions.getUserId();
        isUseUserPermissions = userPermissions.getIsUseUserPermissions();
        Integer dataPermissionsLevel = userPermissions.getDataPermissionsLevel();
        if (null == nodeType){
            // 默认查询：集团及运营公司
            cameraDirVoList = cameraDao.getOrgList(userId,dataPermissionsLevel,isUseUserPermissions);
            if (CollUtil.isNotEmpty(cameraDirVoList)){
                // 运营集团
                CameraDirVo group = cameraDao.getOrgGroup(cameraDirVoList.get(0).getParentId());
                cameraDirVoList.add(group);
                parentId = group.getParentId();
            }

        } else {
            // 根据节点查询
            if (nodeType > 3 || nodeType < 1){
                return Collections.emptyList();
            }
            cameraDirVoList = cameraDao.getCameraResource(userId,dataPermissionsLevel,isUseUserPermissions,nodeId,nodeType);
            parentId = nodeId;
        }
        cameraDirVoList.forEach(cameraDirVo ->
                nodeList.add(new TreeNode<String>()
                        .setId(cameraDirVo.getId())
                        .setName(cameraDirVo.getName())
                        .setParentId(StrUtil.isBlank(cameraDirVo.getParentId()) ? "0" : cameraDirVo.getParentId()) //parentId为null或空字符时,无法统一根节点标识
                        .setExtra(JSONUtil.createObj(jsonConfig)
                                .putOnce("nodeType", cameraDirVo.getNodeType())
                                .putOnce("hasChild", cameraDirVo.getHasChild())
                        )));
        return TreeUtil.build(nodeList, parentId);
    }
}
