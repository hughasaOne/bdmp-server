package com.rhy.bdmp.system.modules.assets.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.assets.domain.po.Dict;
import com.rhy.bdmp.system.modules.assets.domain.bo.DictBO;
import com.rhy.bdmp.system.modules.assets.domain.vo.DictVO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName: DictService
 * @Description: 台账字典服务接口
 * @Author: 魏财富
 * @Date: 2021/4/15 11:19
 */
public interface AssetsDictService extends IService<Dict> {

    Object getDictAssets(DictBO dictBO);

    /**
     * 查询字典（分页）
     * @param currentPage 当前页
     * @param size 页大小
     * @param parentCode 父节点code
     * @param dictName 字典名称
     * @return
     */
    Page<Dict> queryPage(Integer currentPage, Integer size, String parentCode, String dictName);

    /**
     * 查询字典根据字典类型CODE
     * @param parentCode 父节点code
     * @param dictName 字典名称
     */
    List<DictVO> queryByCode(String parentCode, String dictName,Boolean useInnerParentId);

    /**
     * @description: 查询节点目录
     * @date: 2021/4/20 10:08
     * @return: java.util.List<com.rhy.bcp.common.domain.vo.NodeVo>
     */
    List<Dict> findDirectory(String param);

    /**
     * @description: 查找子节点
     * @date: 2021/5/8 13:10
     * @param parentId 父节点id
     * @param includeId 包含节点
     * @return: List<DictVO>
     */
    List<DictVO> findChild(String parentId, String includeId, QueryVO queryVO);

    /**
     * @description: 查找子节点-只找字典
     * @date: 2021/5/8 13:10
     * @param parentId 父节点id
     * @return: List<DictVO>
     */
    List<DictVO> findChildDict(String parentId, QueryVO queryVO);

    Map<String,Object> detail(String dictId);

    /**
     * @description: 保存台账字典
     * @date: 2021/4/16 8:55
     * @param: [dict]
     * @return: boolean
     */
    boolean saveDict(Dict dict);

    /**
     * @description: 更新台账字典
     * @date: 2021/4/16 8:55
     * @param: [dict]
     * @return: boolean
     */
    boolean updateDict(Dict dict);

    /**
     * @description: 删除台账字典
     * @date: 2021/4/16 8:56
     * @param: [dictIds]
     * @return: boolean
     */
    int deleteDict(Set<String> dictIds);
}
