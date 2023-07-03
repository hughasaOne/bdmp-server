package com.rhy.bdmp.gateway.modules.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rhy.bdmp.gateway.modules.domain.to.FilterTO;
import com.rhy.bdmp.gateway.modules.domain.to.PredicatesTO;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class GatewayRouteVo {
    private String id;

    private String name;

    private String envId;

    private List<PredicatesTO> predicatesList;

    private List<FilterTO> filterList;

    private String uri;

    private Integer order;

    private Map<String,Object> metadataMap;

    private Long sort;

    private Integer datastatusid;

    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    private Date createTime;

    private String updateBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    private Date updateTime;
}
