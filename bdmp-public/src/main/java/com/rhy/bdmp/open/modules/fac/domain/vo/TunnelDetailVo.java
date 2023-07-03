package com.rhy.bdmp.open.modules.fac.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class TunnelDetailVo{
    private BaseFacVo facVo;

    private List<BaseTunnelVo> tunnelVoList;
}
