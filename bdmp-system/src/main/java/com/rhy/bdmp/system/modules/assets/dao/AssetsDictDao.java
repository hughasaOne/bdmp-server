package com.rhy.bdmp.system.modules.assets.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.assets.domain.po.Dict;
import com.rhy.bdmp.system.modules.assets.domain.bo.DictBO;
import com.rhy.bdmp.system.modules.assets.domain.vo.DictVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @description: 台账字典 数据操作接口
 * @date:2021/4/19 12:42
 */
@Mapper
public interface AssetsDictDao extends BaseMapper<Dict> {

    /**
     * 查询设备（分页）
     * @param page 分页对象
     * @param parentCode 父节点code
     * @param dictName 字典名称
     * @return
     */
    <E extends IPage<Dict>> E queryPage(IPage<Dict> page, @Param("parentCode") String parentCode, @Param("dictName")String dictName);

    /**
     * 查询字典根据字典类型CODE
     * @param parentCode 父节点code
     * @param dictName 字典名称
     */
    List<DictVO> queryByCode( @Param("parentCode") String parentCode,
                              @Param("dictName") String dictName,
                              @Param("useInnerParentId") Boolean useInnerParentId);

    List<DictVO> findChild(@Param("parentId") String parentId, @Param("includeId")String includeId, @Param("ew") QueryWrapper<DictVO> queryWrapper);

    List<DictVO> findChildDict(@Param("parentId") String parentId, @Param("ew") QueryWrapper<DictVO> queryWrapper);

    Map<String, Object> detail(@Param("dictId") String dictId);

    List<Map<String,Object>> getDictAssets(@Param("dictBO") DictBO dictBO);

    Page<Map<String,Object>> getDictAssets(Page page,@Param("dictBO") DictBO dictBO);

    List<Map<String,Object>> getDeviceDictByDictId(@Param("dictBO") DictBO dictBO);
}
