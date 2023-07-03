package com.rhy.bdmp.system.modules.assets.domain.vo;

import com.rhy.bdmp.base.modules.assets.domain.po.Route;
import com.rhy.bdmp.base.modules.assets.domain.po.Waysection;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Created on 2021/10/29.
 *
 * @author duke
 */
@ApiModel(value="路段", description="路段信息")
@Data
public class WaysectionRouteVo extends Waysection {
    @ApiModelProperty(value = "路线列表")
    private List<Route> routeList;
}
