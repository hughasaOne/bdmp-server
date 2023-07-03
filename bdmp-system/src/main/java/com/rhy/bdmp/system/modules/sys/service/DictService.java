package com.rhy.bdmp.system.modules.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bdmp.base.modules.sys.domain.po.Dict;
import com.rhy.bdmp.system.modules.sys.domain.vo.DictVO;

import java.util.List;
import java.util.Set;

/**
 * @ClassName: SystemService
 * @Description:
 * @Author: 魏财富
 * @Date: 2021/4/15 17:30
 */
public interface DictService extends IService<Dict> {

    /**
     * @description: 查询系统字典目录
     * @date: 2021/4/20 10:24
     * @return: List<Dict>
     */
    List<Dict> findDirectory();

    /**
     * @description: 查询字典（分页）
     * @date: 2021/4/22 16:48
     * @param currentPage 当前页
     * @param size 页大小
     * @param parentCode 父节点code
     * @param dictName 字典名称
     * @return: Page<Dict>
     */
    Page<Dict> queryPage(Integer currentPage, Integer size, String parentCode, String dictName);

    /**
     * @description: 查询字典根据字典类型CODE
     * @date: 2021/4/22 16:50
     * @param parentCode 父节点code
     * @param dictName 字典名称
     * @return: List<Dict>
     */
    List<Dict> queryByCode(String parentCode, String dictName);

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

    /**
     * @description: 创建系统字典
     * @date: 2021/4/20 10:23
     * @param: [dict]
     * @return: boolean
     */
    boolean createDict(Dict dict);

    /**
     * @description: 更新系统字典
     * @date: 2021/4/20 10:23
     * @param: [dict]
     * @return: boolean
     */
    boolean updateDict(Dict sysDict);

    /**
     * @description: 删除系统字典
     * @date: 2021/4/20 10:23
     * @param: [dict]
     * @return: int
     */
    int deleteDict(Set<String> dictIds);

    Dict detail(String dictId);
}
