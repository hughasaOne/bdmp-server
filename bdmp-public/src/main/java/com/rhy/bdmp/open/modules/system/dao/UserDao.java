package com.rhy.bdmp.open.modules.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rhy.bdmp.open.modules.assets.domain.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户dao层
 * @author PSQ
 */
@Mapper
public interface UserDao extends BaseMapper<User> {
}
