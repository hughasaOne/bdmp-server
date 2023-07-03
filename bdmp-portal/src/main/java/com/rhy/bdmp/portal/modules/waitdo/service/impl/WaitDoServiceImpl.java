package com.rhy.bdmp.portal.modules.waitdo.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.rhy.bcp.common.datasource.annotation.DataSource;
import com.rhy.bdmp.base.modules.sys.domain.po.App;
import com.rhy.bcp.common.domain.vo.LoginUserVo;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.portal.modules.sso.service.ISSOClientService;
import com.rhy.bdmp.portal.modules.waitdo.dao.WaitDoDao;
import com.rhy.bdmp.portal.modules.waitdo.domain.vo.WaitDo;
import com.rhy.bdmp.portal.modules.waitdo.service.IWaitDoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author jiangzhimin
 * @version V1.0
 * @description 门户待办
 * @date 2021-01-27 09:31
 **/
@Service
@Slf4j
public class WaitDoServiceImpl implements IWaitDoService {

    @Resource
    private WaitDoDao waitDoDao;

    @Resource
    private ISSOClientService ssoClientService;

    @Override
    @DataSource("baseData")
    public Map<String, List<WaitDo>> findWaitDoList(Set<String> appIds) {
        Map<String, List<WaitDo>> waitDosMap = new HashMap<String, List<WaitDo>>();
        String userId = WebUtils.getUserId();
        List<App> appList = waitDoDao.findUserAppByUserId(userId, appIds);
        for (App app : appList) {
            try{
                String appName = app.getAppName();
                String waitDoUrl = app.getWaitDoUrl();
                if (StrUtil.isNotBlank(waitDoUrl)) {
                    String userAppToken = ssoClientService.getAppToken(app);
                    List<WaitDo> waitDoList = getWaitDos(userAppToken, waitDoUrl, appName);
                    if (null != waitDosMap.get("dbgz")) {
                        List<WaitDo> dbgz = (List<WaitDo>) waitDosMap.get("dbgz");
                        if (null != waitDoList) {
                            dbgz.addAll(waitDoList);
                        }
                    } else {
                        waitDosMap.put("dbgz", waitDoList);
                    }
                }
            }catch (Exception e){
                log.error("集成待办异常: {}", e.getMessage());
            }
        }
        return waitDosMap;
    }

    //获取待办信息（
    @DataSource("baseData")
    private List<WaitDo> getWaitDos(String userAppToken, String searchUrl, String appName) {
        String result = HttpRequest.get(searchUrl)
                .header("token", "Bearer " + userAppToken)
                .timeout(30000).execute().body();
        JSONObject jsonObject = null;
        try {
            jsonObject = JSONUtil.parseObj(result);
        } catch (Exception e) {
            log.error("待办获取路径错误或返回结果格式不统一：" + searchUrl + "\n" + result);
            return null;
        }
        if ("200".equals(jsonObject.getStr("code")) || "0".equals(jsonObject.getStr("code"))) {
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            addAppName(jsonArray, "appName", appName);
            addAppName(jsonArray, "appToken", userAppToken);
            return JSONUtil.toList(jsonArray, WaitDo.class);
        } else {
            return null;
        }
    }

    //增加待办所属应用
    @DataSource("baseData")
    private void addAppName(JSONArray jsonArray, String key, String appName) {
        for (int i = 0; i < jsonArray.size(); i++) {
            jsonArray.getJSONObject(i).set(key, appName);
        }
    }

    public static void main(String[] args) {
        HashMap<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("eventId", "1");
        paramMap.put("scope", "0");
        paramMap.put("stepIds", null);

        String result = HttpRequest.post("http://127.0.0.1:8002/oms/gis/event")
                .header("token", "Bearer 2f0419a31f9casdfdsf431f6cd297fdd3e28fds4af")
                .form(paramMap).timeout(30000).execute().body();
        System.out.println(result);

    }
}