package com.rhy.bdmp.system.modules.assets.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created on 2021/10/26.
 * 设施拓展表枚举API
 * @author duke
 */
@Getter
@AllArgsConstructor
public enum EnumFacilitiesType {

    BRIDGE("32330600", "桥梁", "t_bdmp_assets_facilities_bridge","com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesBridge","com.rhy.bdmp.base.modules.assets.dao.BaseFacilitiesBridgeDao"),
    TUNNEL("32330400", "隧道", "t_bdmp_assets_facilities_tunnel","com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesTunnel","com.rhy.bdmp.base.modules.assets.dao.BaseFacilitiesTunnelDao"),
    SERVICE_AREA("32330800", "服务区", "t_bdmp_assets_facilities_service_area","com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesServiceArea","com.rhy.bdmp.base.modules.assets.dao.BaseFacilitiesServiceAreaDao"),
    TOLL_STATION("32330200", "收费站", "t_bdmp_assets_facilities_toll_station","com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesTollStation","com.rhy.bdmp.base.modules.assets.dao.BaseFacilitiesTollStationDao"),
    DOOR_FRAME("32330700", "门架", "t_bdmp_assets_facilities_gantry","com.rhy.bdmp.base.modules.assets.domain.po.FacilitiesGantry","com.rhy.bdmp.base.modules.assets.dao.BaseFacilitiesGantryDao");

    private final String facilitiesTypeCode;
    private final String facilitiesTypeName;
    private final String facilitiesExtTableName;
    private final String facilitiesExtPo;
    private final String facilitiesExtDao;


    public static EnumFacilitiesType find(String val) {
        for (EnumFacilitiesType dataScopeEnum : EnumFacilitiesType.values()) {
            if (val.equals(dataScopeEnum.getFacilitiesTypeCode())) {
                return dataScopeEnum;
            }
        }
        return null;
    }


}
