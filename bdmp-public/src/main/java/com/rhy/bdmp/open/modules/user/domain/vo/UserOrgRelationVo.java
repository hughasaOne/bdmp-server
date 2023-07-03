package com.rhy.bdmp.open.modules.user.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shuai chao
 * @description TODO
 * @date 2022-09-23 9:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOrgRelationVo {

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("用户账号名")
    private String userName;

    @ApiModelProperty("用户昵称")
    private String nickName;

    @ApiModelProperty("用户组织关系名")
    private String companyDept;

    @ApiModelProperty("用户所属组织id")
    private String orgId;
}
