package com.rhy.bdmp.system.modules.sys.common.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rhy.bdmp.base.modules.sys.domain.po.Org;
import com.rhy.bdmp.base.modules.sys.domain.po.User;
import com.rhy.bdmp.base.modules.sys.service.IBaseOrgService;
import com.rhy.bdmp.base.modules.sys.service.IBaseUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserPermissions {

    @Resource
    private IBaseOrgService orgService;

    @Resource
    private IBaseUserService userService;

    /**
     * 获取用户能看到的用户
     * todo org后面可能改成组织授权里的org
     */
    public List<User> getUser(Org org,String appId){
        List<Org> orgList = this.getOrg(org);
        List<User> userList = new ArrayList<>();
        if (CollUtil.isNotEmpty(orgList)){
            userList = userService.list(new LambdaQueryWrapper<User>()
                    .in(User::getOrgId,orgList.stream()
                            .map(Org::getOrgId)
                            .collect(Collectors.toList()))
                    .eq(User::getDatastatusid, "1")
                    .eq(StrUtil.isNotBlank(appId),User::getAppId, appId)
                    .orderByAsc(User::getSort)
                    .orderByDesc(User::getCreateTime));
        }

        return userList;
    }

    /**
     * 获取用户能看到的组织
     */
    public List<Org> getOrg(Org org){
        List<Org> temp = new ArrayList<>();
        List<Org> parents = new ArrayList<>();
        parents.add(org);

        this.findChildren(orgService.list(new QueryWrapper<Org>()
                .orderByAsc("sort")
                .orderByDesc("create_time")),temp,parents);
        org.setParentId("0");
        temp.add(org);

        return temp;
    }

    private void findChildren(List<Org> orgVoList, List<Org> temp, List<Org> parents) {
        if (CollUtil.isEmpty(orgVoList)){
            return;
        }

        List<Org> temp1 =  new ArrayList<>();
        List<Org> parents1 =  new ArrayList<>();

        temp1.addAll(orgVoList);

        if (CollUtil.isNotEmpty(parents)){
            for (Org parent : parents) {
                List<Org> children = orgVoList.stream()
                        .filter(orgVo -> StrUtil.equals(orgVo.getParentId(),parent.getOrgId()))
                        .collect(Collectors.toList());
                if (CollUtil.isNotEmpty(children)){
                    temp.addAll(children);
                    parents1.addAll(children);
                }
            }
        }

        if (CollUtil.isNotEmpty(parents1)){
            this.findChildren(temp1,temp,parents1);
        }
    }
}
