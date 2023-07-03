package com.rhy.bdmp.base.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.Waysection;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;

import java.util.List;
import java.util.Set;

/**
 * @description 运营路段 服务接口
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
public interface IBaseWaysectionService extends IService<Waysection> {

    /**
     * 运营路段列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<Waysection> list(QueryVO queryVO);

    /**
     * 运营路段列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<Waysection> page(QueryVO queryVO);

    /**
     * 查看运营路段(根据ID)
     * @param waysectionId
     * @return
     */
    Waysection detail(String waysectionId);

    /**
     * 新增运营路段
     * @param waysection
     * @return
     */
    int create(Waysection waysection);

    /**
     * 修改运营路段
     * @param waysection
     * @return
     */
    int update(Waysection waysection);

    /**
     * 删除运营路段
     * @param waysectionIds
     * @return
     */
    int delete(Set<String> waysectionIds);


}
