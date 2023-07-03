package com.rhy.bdmp.base.modules.sys.dao;

import com.rhy.bdmp.base.modules.sys.domain.po.AuthClientDetails;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description oauth2认证配置 数据操作接口
 * @author shuaichao
 * @date 2022-03-23 17:22
 * @version V1.0
 **/
@Mapper
public interface BaseAuthClientDetailsDao extends BaseMapper<AuthClientDetails> {

}
