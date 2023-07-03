package com.rhy.bdmp.base.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.DictBrand;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;

import java.util.List;
import java.util.Set;

/**
 * @description 品牌字典 服务接口
 * @author duke
 * @date 2021-10-29 16:12
 * @version V1.0
 **/
public interface IBaseDictBrandService extends IService<DictBrand> {

    /**
     * 品牌字典列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<DictBrand> list(QueryVO queryVO);

    /**
     * 品牌字典列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<DictBrand> page(QueryVO queryVO);

    /**
     * 查看品牌字典(根据ID)
     * @param brandId
     * @return
     */
    DictBrand detail(String brandId);

    /**
     * 新增品牌字典
     * @param dictBrand
     * @return
     */
    int create(DictBrand dictBrand);

    /**
     * 修改品牌字典
     * @param dictBrand
     * @return
     */
    int update(DictBrand dictBrand);

    /**
     * 删除品牌字典
     * @param brandIds
     * @return
     */
    int delete(Set<String> brandIds);


}
