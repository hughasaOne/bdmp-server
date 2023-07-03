package com.rhy.bdmp.open.modules.dict.dao;

import cn.hutool.json.JSONArray;
import com.rhy.bdmp.base.modules.assets.domain.po.DeviceCategory;
import com.rhy.bdmp.open.modules.common.domain.vo.TreeNode;
import com.rhy.bdmp.open.modules.dict.domain.bo.DictBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component(value = "DictDaoV1")
public interface DictDao {
    List<TreeNode> getSystemTree();

    List<TreeNode> getDeviceDictNode(@Param("categoryType") Integer categoryType,
                                     @Param("categoryRules") JSONArray categoryRules);

    List<TreeNode> getDictList(@Param("dictBo") DictBo dictBo);

    Map<String,Object> getDictDetail(@Param("code") String code);

    DeviceCategory getCategoryById(@Param("categoryId") String categoryId);

    List<Map<String,Object>> getDeviceDictList(@Param("deviceType") String deviceType);
}
