package com.rhy.bdmp.system.modules.gateway.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.system.modules.gateway.dao.GatewayEnvDao;
import com.rhy.bdmp.system.modules.gateway.domain.vo.GatewayEnvVO;
import com.rhy.bdmp.system.modules.gateway.domain.vo.GatewayRouteVO;
import com.rhy.bdmp.system.modules.gateway.service.GatewayEnvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.List;

/**
 * @author author
 * @version V1.0
 * @description 服务实现
 * @date 2022-06-22 09:40
 **/
@Service
public class GatewayEnvServiceImpl extends ServiceImpl<GatewayEnvDao, GatewayEnvVO> implements GatewayEnvService {

    @Autowired
    private GatewayConfigServiceImpl gatewayConfigService;


    @Override
    public List<GatewayEnvVO> getServiceEnv() {
        QueryWrapper<GatewayEnvVO> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("active").orderByAsc("sort");
        return getBaseMapper().selectList(wrapper);
    }


    /**
     * 新增
     *
     * @param gatewayEnvVo
     * @return
     */
    @Override
    public int create(GatewayEnvVO gatewayEnvVo) {
        if (StrUtil.isNotBlank(gatewayEnvVo.getId())) {
            throw new BadRequestException("A new TBdmpGatewayEnv cannot already have an id");
        }
        if (StrUtil.isBlank(gatewayEnvVo.getCode()) || StrUtil.isBlank(gatewayEnvVo.getName())) {
            throw new BadRequestException("缺乏code或名称");
        }
        QueryWrapper<GatewayEnvVO> queryWrapper = new QueryWrapper();
        queryWrapper.eq("code", gatewayEnvVo.getCode());
        if (getBaseMapper().selectCount(queryWrapper).intValue() > 0) {
            throw new BadRequestException("该环境code:" + gatewayEnvVo.getCode() + "已经存在");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        gatewayEnvVo.setCreateBy(currentUser);
        gatewayEnvVo.setCreateTime(currentDateTime);
        gatewayEnvVo.setUpdateBy(currentUser);
        gatewayEnvVo.setUpdateTime(currentDateTime);
        return getBaseMapper().insert(gatewayEnvVo);
    }

    /**
     * 修改
     *
     * @param gatewayEnvVo
     * @return
     */
    @Override
    public int update(GatewayEnvVO gatewayEnvVo) {
        if (!StrUtil.isNotBlank(gatewayEnvVo.getId())) {
            throw new BadRequestException("A new TBdmpGatewayEnv not exist id");
        }
        if (StrUtil.isBlank(gatewayEnvVo.getCode()) || StrUtil.isBlank(gatewayEnvVo.getName())) {
            throw new BadRequestException("缺乏code或名称");
        }
        QueryWrapper<GatewayEnvVO> queryWrapper = new QueryWrapper();
        queryWrapper.eq("code", gatewayEnvVo.getCode());
        List<GatewayEnvVO> gatewayEnvVoS = getBaseMapper().selectList(queryWrapper);
        // 如果有多条抛出异常，如果只有一条且与入参id相同则表示修改本身
        if (CollUtil.isNotEmpty(gatewayEnvVoS)) {
            if (gatewayEnvVoS.size() > 1 || !StrUtil.equals(gatewayEnvVoS.get(0).getId(), gatewayEnvVo.getId())) {
                throw new BadRequestException("该环境code:" + gatewayEnvVo.getCode() + "已经存在");
            }
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        gatewayEnvVo.setUpdateBy(currentUser);
        gatewayEnvVo.setUpdateTime(currentDateTime);
        return getBaseMapper().updateById(gatewayEnvVo);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Boolean delete(String id) {
        try {
            QueryWrapper<GatewayRouteVO> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(GatewayRouteVO::getEnvId, id);
            gatewayConfigService.remove(queryWrapper);
            getBaseMapper().deleteById(id);
            return Boolean.TRUE;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Boolean.FALSE;
        }
    }

    /**
     * 获取当前环境
     *
     * @return
     */
    @Override
    public GatewayEnvVO getCurrentEnvId() {
        QueryWrapper<GatewayEnvVO> queryWrapper = new QueryWrapper();
        queryWrapper.eq("active", 1);
        return getBaseMapper().selectOne(queryWrapper);
    }

}
