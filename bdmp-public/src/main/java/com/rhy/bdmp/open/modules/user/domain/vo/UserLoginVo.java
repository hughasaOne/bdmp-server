package com.rhy.bdmp.open.modules.user.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserLoginVo {
    @ApiModelProperty("token")
    private String accessToken;

    @ApiModelProperty("token类型")
    private String tokenType;

    @ApiModelProperty("刷新token")
    private String refreshToken;

    @ApiModelProperty("过期时间")
    private Integer expiresIn;

    @ApiModelProperty("授权范围")
    private String scope;

    @ApiModelProperty("所属运营公司id")
    private String operationCompanyId;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("所属应用id")
    private String appId;

    @ApiModelProperty("客户端id")
    private String clientId;

    @ApiModelProperty("所属组织id")
    private String orgId;

    @ApiModelProperty("所属集团id")
    private String operationGroupId;

    @ApiModelProperty("jwt标识")
    private String jti;
}
