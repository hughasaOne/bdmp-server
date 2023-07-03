package com.rhy.bdmp.system;

import cn.hutool.json.JSONUtil;
import com.rhy.bdmp.SystemApplication;
import com.rhy.bcp.common.datasource.util.SqlHelperService;
import com.rhy.bcp.common.util.PageUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest(classes = SystemApplication.class)
public class SystemApplicationTest {

    @Autowired(required = false)
    SqlHelperService sqlHelperService;


    @Test
    void batchExeSqlListLocalDB(){
        //bdmp库
        List<String> sqlList = new ArrayList<String>();
        sqlList.add("insert into t_bdmp_app(app_id,app_name) values('app_id_1','app_name_1')");
        sqlList.add("insert into t_bdmp_app(app_id,app_name) values('app_id_2','app_name_2')");
        sqlList.add("insert into t_bdmp_app(app_id,app_name) values('app_id_3','app_name_3')");
        sqlHelperService.exeSqlListLocalDB(sqlList);
    }

    @Test
    void searchSlq(){
//        bdmp库
        String sql = "select * from t_bdmp_app";
        List<Map<String, Object>> mapList = sqlHelperService.searchSqlLocalDB(sql);
        System.out.println("============  " + JSONUtil.parse(mapList).toString());
        System.out.println("******************************************");
        PageUtils page = new PageUtils(sqlHelperService.searchPageSqlLocalDB(1,2, sql));
        System.out.println("============  " + JSONUtil.parse(page).toString());
    }

    @Test
    void searchLocalSlaveSlq(){
//        eladmin库
        String sql = "select * from sys_user";
        List<Map<String, Object>> mapList = sqlHelperService.searchSqlLocalSlaveDB(sql);
        System.out.println("============  " + JSONUtil.parse(mapList).toString());
        System.out.println("******************************************");
        PageUtils page = new PageUtils(sqlHelperService.searchPageSqlLocalSlaveDB(1,1, sql));
        System.out.println("============  " + JSONUtil.parse(page).toString());
    }

}
