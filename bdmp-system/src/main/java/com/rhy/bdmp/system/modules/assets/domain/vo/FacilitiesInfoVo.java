package com.rhy.bdmp.system.modules.assets.domain.vo;

import com.rhy.bdmp.base.modules.assets.domain.po.Facilities;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created on 2021/10/26.
 *
 * @author duke
 */
@ApiModel(value="路段设施", description="路段设施信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FacilitiesInfoVo extends Facilities {
    @ApiModelProperty(value = "扩展表")
    private List<Object> facilitiesExt;
}
