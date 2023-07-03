package com.rhy.bdmp.system.modules.assets.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rhy.bdmp.base.modules.assets.domain.po.Waysection;
import com.rhy.bdmp.system.modules.assets.domain.po.User;
import com.rhy.bdmp.system.modules.assets.domain.vo.WaysectionVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @auther jiangzhimin
 * @Description
 * @date 2021/4/14
 */
@Mapper
public interface WaySectionDao extends BaseMapper<Waysection> {

    /**
     * 查询路段（分页）
     * @param page 分页
     * @param isUseUserPermissions 是否使用用户权限
     * @param userId 用户ID
     * @param orgId 组织ID
     * @param waySectionName 路段名称
     * @return
     */
    <E extends IPage<WaysectionVo>> E queryPage(IPage<WaysectionVo> page,
                                                @Param("isUseUserPermissions")Boolean isUseUserPermissions,
                                                @Param("userId")String userId,
                                                @Param("orgId")String orgId,
                                                @Param("waySectionName")String waySectionName,
                                                @Param("dataPermissionsLevel") Integer dataPermissionsLevel,
                                                @Param("waynetId") String waynetId);

    /*
     * 根据用户id获取用户对象
     * */
    User getUserById(String userId);
}
