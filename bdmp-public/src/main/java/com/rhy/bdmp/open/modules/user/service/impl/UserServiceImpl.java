package com.rhy.bdmp.open.modules.user.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.resutl.Result;
import com.rhy.bdmp.base.modules.sys.domain.po.Org;
import com.rhy.bdmp.open.modules.user.config.UserLoginProperties;
import com.rhy.bdmp.open.modules.user.dao.UserDao;
import com.rhy.bdmp.open.modules.user.domain.bo.GetUserByOrgBo;
import com.rhy.bdmp.open.modules.user.domain.vo.OrgVo;
import com.rhy.bdmp.open.modules.user.domain.vo.UserLoginVo;
import com.rhy.bdmp.open.modules.user.domain.vo.UserOrgRelationVo;
import com.rhy.bdmp.open.modules.user.domain.vo.UserVo;
import com.rhy.bdmp.open.modules.user.feignclient.AuthFeignClient;
import com.rhy.bdmp.open.modules.user.service.IUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {

    @Resource(name = "UserDaoV1")
    private UserDao userDao;

    @Resource
    private UserLoginProperties userLoginProperties;

    @Resource
    private AuthFeignClient authFeignClient;

    @Override
    public UserVo getUserInfo(String userId) {
        UserVo userVo = userDao.findUserByUserId(userId);
        if (null == userVo) {
            throw new BadRequestException("未获取到当前用户");
        }
        this.setUserInfo(userVo,userVo.getOrgId());
        return userVo;
    }

    /**
     * 如果用户所属公司/集团，则关系名取公司/集团名，否则则取公司-组织名，深层级（小地名）的用户取用户所属组织的上级[暂定]
     */
    @Override
    public List<UserOrgRelationVo> getUserOrgRelation(Set<String> userIds) {
        List<UserOrgRelationVo> list =  userDao.findAllUser();
        if (CollUtil.isEmpty(list)){
            return null;
        }
        if (CollUtil.isNotEmpty(userIds)){
            list = list.stream()
                    .filter(userOrgRelationVo -> userIds.stream()
                            .anyMatch(userId -> StrUtil.equals(userId, userOrgRelationVo.getUserId()) || StrUtil.equals(userId,userOrgRelationVo.getUserName())))
                    .collect(Collectors.toList());
        }
        List<UserOrgRelationVo> resList = new ArrayList<>();

        List<OrgVo> orgList = userDao.getOrgList();

        Map<String, List<OrgVo>> orgMap = orgList.stream().collect(Collectors.groupingBy(Org::getOrgId));

        for (UserOrgRelationVo info : list){
            List<OrgVo> orgVos = orgMap.get(info.getOrgId());

            if(CollectionUtils.isNotEmpty(orgVos)) {
                OrgVo item = orgVos.get(0);
                if ("000300".equals(item.getOrgType()) || "000200".equals(item.getOrgType())) {
                    info.setCompanyDept(item.getOrgShortName());
                } else {
                    //递归获取当前用户所属运营公司
                    String companyName = getUserCompanyByMap(item.getParentId(),orgMap);
                    if (StrUtil.isNotBlank(companyName)) {
                        if (StrUtil.equals(item.getOrgType(),"000321")){
                            info.setCompanyDept(companyName + "-" + item.getParentShortName());
                        }
                        else {
                            info.setCompanyDept(companyName + "-" + item.getOrgShortName());
                        }
                    }
                }
            }
            resList.add(info);
        }
        return resList;
    }

    /**
     * 根据组织机构查询用户
     */
    @Override
    public List<UserVo> getUserByOrg(GetUserByOrgBo getUserByOrgBo) {
        List<String> orgIds = new ArrayList<>();
        if (BooleanUtil.isTrue(getUserByOrgBo.getIncludeSubOrgUser())){
            // 向下查询子节点用户
            orgIds.add(getUserByOrgBo.getOrgId());
            this.getOrgChildrenIds(CollUtil.toList(getUserByOrgBo.getOrgId()),orgIds);
        }
        else{
            orgIds.add(getUserByOrgBo.getOrgId());
        }
        List<UserVo> userVoList = userDao.getUserByOrg(orgIds);
        if (BooleanUtil.isTrue(getUserByOrgBo.getIncludeOrgInfo())){
            for (UserVo userVo : userVoList) {
                this.setUserInfo(userVo,userVo.getOrgId());
            }
        }
        return userVoList;
    }

    /**
     * 供第三方集成登录
     */
    @Override
    public UserLoginVo login(String account) {
        UserLoginVo userLoginVo = new UserLoginVo();
        UserVo userVo = userDao.getUser(account);
        if (null == userVo){
            throw new BadRequestException("用户 " + account + "不存在");
        }

        Map<String, String> params = userLoginProperties.getParams();
        if (null == params){
            throw new BadRequestException("配置参数不存在");
        }

        params.put("username",userVo.getUsername());
        Result login = authFeignClient.login(params);
        if (null == login){
            throw new BadRequestException("接口调用失败");
        }

        if (!StrUtil.equals(login.getCode(),"200") && !StrUtil.equals(login.getCode(),"0")){
            throw new BadRequestException(login.getMsg());
        }

        JSONObject resJson = JSONUtil.parseObj(login.getData());
        userLoginVo.setAccessToken(resJson.getStr("access_token"));
        userLoginVo.setTokenType(resJson.getStr("token_type"));
        userLoginVo.setRefreshToken(resJson.getStr("refresh_token"));
        userLoginVo.setExpiresIn(resJson.getInt("expires_in"));
        userLoginVo.setScope(resJson.getStr("scope"));
        userLoginVo.setOperationCompanyId(resJson.getStr("operation_company_id"));
        userLoginVo.setUserId(resJson.getStr("user_id"));
        userLoginVo.setAppId(resJson.getStr("app_id"));
        userLoginVo.setClientId(resJson.getStr("client_id"));
        userLoginVo.setOrgId(resJson.getStr("org_id"));
        userLoginVo.setOperationGroupId(resJson.getStr("operation_group_id"));
        userLoginVo.setJti(resJson.getStr("jti"));

        return userLoginVo;
    }

    private void setUserInfo(UserVo userVo, String orgId) {
        Org org = userDao.getOrg(orgId);
        if (null == org){
            return;
        }
        String orgType = org.getOrgType();
        if ("000300".equals(orgType)){
            userVo.setOperationGroupId(org.getParentId());
            Org parentNode = userDao.getOrg(org.getParentId());
            userVo.setOperationGroupName(parentNode.getOrgName());
            userVo.setOperationGroupShortName(parentNode.getOrgShortName());

            userVo.setOperationCompanyId(org.getOrgId());
            userVo.setOperationCompanyName(org.getOrgName());
            userVo.setOperationCompanyShortName(org.getOrgShortName());
        }
        else if("000200".equals(orgType)){
            userVo.setOperationGroupId(org.getOrgId());
            userVo.setOperationGroupName(org.getOrgName());
            userVo.setOperationGroupShortName(org.getOrgShortName());
            userVo.setOperationCompanyId(null);
        }
        else if(StrUtil.isBlank(org.getParentId()) || "000900".equals(orgType)){
            userVo.setOperationCompanyId(null);
            userVo.setOperationGroupId(null);
        }
        else {
            this.setUserInfo(userVo,org.getParentId());
        }
    }

    private String getUserCompanyByMap(String orgId,Map<String, List<OrgVo>> orgList) {
        if(StrUtil.isBlank(orgId)){
            return "";
        }
        OrgVo org = orgList.get(orgId).get(0);
        if("000300".equals(org.getOrgType())){
            return org.getOrgShortName();
        }else{
            return getUserCompanyByMap(org.getParentId(),orgList);
        }
    }

    /**
     * 获取org的子集
     */
    private List<String> getOrgChildrenIds(List<String> orgIds,List<String> resOrgIds){
        List<String> tempIds = userDao.getOrgChildrenIds(orgIds);
        if (CollUtil.isNotEmpty(tempIds)){
            resOrgIds.addAll(tempIds);
            this.getOrgChildrenIds(tempIds,resOrgIds);
        }
        return resOrgIds;
    }
}
