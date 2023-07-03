/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.rhy.bdmp.open.modules.system.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @description 修改密码
 * @author jiangzhimin
 * @date 2020-12-25 00:08
 * @version V1.0
 **/
@ApiModel(value="修改密码", description="修改密码")
@Data
public class UserPassVo implements Serializable {

    @NotBlank
    @ApiModelProperty(value = "userId")
    private String userId;

    @NotBlank
    @ApiModelProperty(value = "旧密码")
    private String oldPass;

    @NotBlank
    @ApiModelProperty(value = "新密码")
    private String newPass;

}