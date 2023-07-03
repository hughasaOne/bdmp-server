package com.rhy.bdmp.open.modules.fac.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rhy.bdmp.base.modules.assets.domain.po.Facilities;
import com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesTunnel;
import com.rhy.bdmp.open.modules.assets.domain.bo.UserPermissions;
import com.rhy.bdmp.open.modules.common.domain.bo.CommonBo;
import com.rhy.bdmp.open.modules.common.service.CommonService;
import com.rhy.bdmp.open.modules.fac.dao.FacDao;
import com.rhy.bdmp.open.modules.fac.dao.TunnelDao;
import com.rhy.bdmp.open.modules.fac.domain.bo.FacDetailBo;
import com.rhy.bdmp.open.modules.fac.domain.vo.BaseFacVo;
import com.rhy.bdmp.open.modules.fac.domain.vo.BaseTunnelVo;
import com.rhy.bdmp.open.modules.fac.domain.vo.TunnelDetailVo;
import com.rhy.bdmp.open.modules.fac.service.IFacService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service(value = "FacServiceImplV1")
public class FacServiceImpl implements IFacService {
    @Resource
    private FacDao facDao;

    @Resource
    private TunnelDao tunnelDao;

    @Resource
    private CommonService commonService;

    /**
     * 获取设施详情
     */
    @Override
    public Object getFacDetail(FacDetailBo facDetailBo) {
        String facId = facDetailBo.getFacId();
        Facilities baseFac = facDao.selectById(facId);

        if (null == baseFac){
            return null;
        }
        String facType = baseFac.getFacilitiesType();
        if (StrUtil.isBlank(facType)){
            return baseFac;
        }

        TunnelDetailVo tunnelDetailVo = new TunnelDetailVo();
        BaseFacVo baseFacVo = new BaseFacVo();
        BeanUtil.copyProperties(baseFac,baseFacVo);
        tunnelDetailVo.setFacVo(baseFacVo);
        List<BaseTunnelVo> tunnelVoList = new ArrayList<>();
        switch (facType){
            case "32330400":
                // 隧道
                List<FacilitiesTunnel> tunnelList = tunnelDao.selectList(new QueryWrapper<FacilitiesTunnel>().eq("facilities_id",facId));

                if (CollUtil.isNotEmpty(tunnelList)){
                    for (FacilitiesTunnel facilitiesTunnel : tunnelList) {
                        BaseTunnelVo tunnelVo = new BaseTunnelVo();
                        BeanUtil.copyProperties(facilitiesTunnel,tunnelVo);
                        tunnelVoList.add(tunnelVo);
                    }
                    tunnelDetailVo.setTunnelVoList(tunnelVoList);
                }
                break;
            default:
                break;
        }
        return tunnelDetailVo;
    }

    /**
     * 设施列表
     */
    @Override
    public Object getFacList(CommonBo commonBo) {
        commonService.checkParams(commonBo.getNodeId(),commonBo.getNodeType());
        UserPermissions userPermissions = commonService.getUserPermissions(commonBo.getIsUseUserPermissions());
        commonBo.setNodeType(commonService.convertNodeType(commonBo.getNodeType()));
        commonBo.setCodes(CollUtil.isEmpty(commonBo.getCodes()) ? null : commonBo.getCodes());

        List<BaseFacVo> facVoList = facDao.getFacList(commonBo,userPermissions);
        return facVoList;
    }
}
