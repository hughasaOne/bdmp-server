package com.rhy.bdmp.open.modules.common.dao;

import com.rhy.bdmp.open.modules.assets.domain.po.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommonDao {
    User getUserById(String userId);
}
