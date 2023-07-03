package com.rhy.bdmp.system.modules.analysis.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 一键授权对象VO
 *
 * @author PSQ
 */
@Data
public class AuthorizationVO {

    @ApiModelProperty("被授权的用户id")
    private List<String> userIds;

    @ApiModelProperty("被复制权限的用户id")
    private String permissionsUserId;

    @ApiModelProperty("复制权限的类型")
    private String permissionsType;
}
