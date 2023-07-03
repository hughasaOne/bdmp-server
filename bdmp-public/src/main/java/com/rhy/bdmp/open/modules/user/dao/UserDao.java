package com.rhy.bdmp.open.modules.user.dao;

import com.rhy.bdmp.base.modules.sys.domain.po.Org;
import com.rhy.bdmp.open.modules.user.domain.vo.OrgVo;
import com.rhy.bdmp.open.modules.user.domain.vo.UserOrgRelationVo;
import com.rhy.bdmp.open.modules.user.domain.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "UserDaoV1")
public interface UserDao {
    UserVo findUserByUserId(String userId);

    Org getOrg(String orgId);

    UserVo getUser(@Param("account") String account);

    List<UserOrgRelationVo> findAllUser();

    List<OrgVo> getOrgList();

    List<UserVo> getUserByOrg(@Param("orgIds") List<String> orgIds);

    List<String> getOrgChildrenIds(@Param("orgIds") List<String> orgIds);
}
