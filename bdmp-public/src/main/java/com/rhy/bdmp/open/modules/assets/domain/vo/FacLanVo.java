package com.rhy.bdmp.open.modules.assets.domain.vo;

import lombok.Data;

@Data
public class FacLanVo {
    private String  laneId;
    private String  laneName;
    private String  laneNo;
    private Long  sort;
    private String  laneIp;
    private Integer  laneType;
    private Integer  laneInOut;
    private String  carNum;
    private String  laneStatus;
    private Integer laneNoSort;
}
