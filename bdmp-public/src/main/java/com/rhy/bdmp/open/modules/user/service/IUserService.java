package com.rhy.bdmp.open.modules.user.service;

import com.rhy.bdmp.open.modules.user.domain.bo.GetUserByOrgBo;
import com.rhy.bdmp.open.modules.user.domain.vo.UserLoginVo;
import com.rhy.bdmp.open.modules.user.domain.vo.UserOrgRelationVo;
import com.rhy.bdmp.open.modules.user.domain.vo.UserVo;

import java.util.List;
import java.util.Set;

public interface IUserService {
    UserVo getUserInfo(String userId);

    UserLoginVo login(String account);

    List<UserOrgRelationVo> getUserOrgRelation(Set<String> userIds);

    List<UserVo> getUserByOrg(GetUserByOrgBo getUserByOrgBo);
}
