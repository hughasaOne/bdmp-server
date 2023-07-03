package com.rhy.bdmp.system.modules.assets.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.rhy.bcp.common.util.WebUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 新增/修改时参数自动填充
 * @author weicaifu
 **/
@Component
public class MybatisPlusAutoFill implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Date currentTime = new Date();
        String userId = WebUtils.getUserId();
        this.strictInsertFill(metaObject, "createTime", Date.class, currentTime);
        this.strictInsertFill(metaObject, "createBy", String.class, userId);
        this.strictInsertFill(metaObject, "updateTime", Date.class, currentTime);
        this.strictInsertFill(metaObject, "updateBy", String.class, userId);
        this.strictInsertFill(metaObject,"sort",Long.class,0L);
        this.strictInsertFill(metaObject,"datastatusid",Integer.class,1);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Date currentTime = new Date();
        String userId = WebUtils.getUserId();
        this.strictUpdateFill(metaObject, "updateTime", Date.class, currentTime);
        this.strictUpdateFill(metaObject, "updateBy", String.class, userId);
    }
}
