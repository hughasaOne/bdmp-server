package com.rhy.bdmp.system.modules.assets.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.assets.domain.po.DictBrand;
import com.rhy.bdmp.system.modules.assets.domain.bo.DictBO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created on 2021/11/1.
 *
 * @author duke
 */
public interface IDictBrandService {
    Object getDictBrand(DictBO dictBO);

    /**
     * 创建品牌字典
     * @param dictBrand 品牌字典实体
     * @return int
     */
    boolean create(DictBrand dictBrand);

    /**
     * 修改品牌字典
     * @param dictBrand 品牌字典实体
     * @return int
     */
    boolean update(DictBrand dictBrand);

    /**
     * 删除品牌字典
     * @param brandIds 品牌id
     * @return int
     */
    int delete(Set<String> brandIds);

    /**
     * 品牌字典详情
     * @param brandId 品牌字典实体
     * @return DictBrand
     */
    Map<String,Object> detail(String brandId);

    /**
     * 分页查询 品牌字典
     * @param currentPage 当前页码
     * @param size        每一页显示数量
     * @param param       搜索条件
     * @return    结果集
     */
    Page<DictBrand> queryPage(Integer currentPage, Integer size, String param);

    /**
     * 查询所有品牌字典
     * @param param 搜索条件
     * @return 结果集
     */
    List<DictBrand> list(String param);

    List<Map<String,Object>> getDictListByParentId(String parentId);
}
