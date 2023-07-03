package com.rhy.bdmp.system.modules.assets.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.system.modules.assets.domain.bo.DictBO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 岗位字典管理
 * @author weicaifu
 **/
@Mapper
public interface DictPostDao {
    List<Map<String, Object>> getDictPost(@Param("dictBO") DictBO dictBO);

    Page<Map<String,Object>> getDictPost(Page page, @Param("dictBO") DictBO dictBO);

    /**
     * 获取组织拥有的岗位
     * @author weicaifu
     * @param orgCodes 组织类型
     **/
    List<Map<String,Object>> getOrgPost(@Param("orgCodes") Set<String> orgCodes);

    List<Map<String, Object>> getDictByCode(String orgType);

    List<Map<String, Object>> getOrgDict(@Param("parentCode") String parentCode);

    List<Map<String, Object>> getDictPostList();

    List<Map<String, Object>> getCompanyList();
}
