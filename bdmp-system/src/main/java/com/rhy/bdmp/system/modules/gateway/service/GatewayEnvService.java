package com.rhy.bdmp.system.modules.gateway.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bdmp.system.modules.gateway.domain.vo.GatewayEnvVO;

import java.util.List;

/**
 * @author author
 * @version V1.0
 * @description 服务接口
 * @date 2022-06-22 09:40
 **/
public interface GatewayEnvService extends IService<GatewayEnvVO> {

    /**
     * 列表查询
     *
     * @return
     */
    List<GatewayEnvVO> getServiceEnv();


    /**
     * 新增
     *
     * @param tBdmpGatewayEnv
     * @return
     */
    int create(GatewayEnvVO tBdmpGatewayEnv);

    /**
     * 修改
     *
     * @param tBdmpGatewayEnv
     * @return
     */
    int update(GatewayEnvVO tBdmpGatewayEnv);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    Boolean delete(String id);

    /**
     * 获取当前环境
     *
     * @return
     */
    GatewayEnvVO getCurrentEnvId();
}
