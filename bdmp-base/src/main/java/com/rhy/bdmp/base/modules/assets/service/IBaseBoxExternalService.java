package com.rhy.bdmp.base.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.BoxExternal;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;

import java.util.List;
import java.util.Set;

/**
 * @description 终端箱外接设备信息表 服务接口
 * @author yanggj
 * @date 2021-09-24 15:36
 * @version V1.0
 **/
public interface IBaseBoxExternalService extends IService<BoxExternal> {

    /**
     * 终端箱外接设备信息表列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<BoxExternal> list(QueryVO queryVO);

    /**
     * 终端箱外接设备信息表列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<BoxExternal> page(QueryVO queryVO);

    /**
     * 查看终端箱外接设备信息表(根据ID)
     * @param externalId
     * @return
     */
    BoxExternal detail(String externalId);

    /**
     * 新增终端箱外接设备信息表
     * @param boxExternal
     * @return
     */
    int create(BoxExternal boxExternal);

    /**
     * 修改终端箱外接设备信息表
     * @param boxExternal
     * @return
     */
    int update(BoxExternal boxExternal);

    /**
     * 删除终端箱外接设备信息表
     * @param externalIds
     * @return
     */
    int delete(Set<String> externalIds);


}
