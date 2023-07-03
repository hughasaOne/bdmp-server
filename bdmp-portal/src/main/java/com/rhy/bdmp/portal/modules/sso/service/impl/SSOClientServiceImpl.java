package com.rhy.bdmp.portal.modules.sso.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import com.rhy.bcp.common.datasource.annotation.DataSource;
import com.rhy.bcp.common.domain.vo.LoginUserVo;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.resutl.ResultCode;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.base.modules.sys.domain.po.App;
import com.rhy.bdmp.base.modules.sys.service.IBaseAppService;
import com.rhy.bdmp.portal.modules.sso.dao.SSOClientDao;
import com.rhy.bdmp.portal.modules.sso.domain.AppUserVO;
import com.rhy.bdmp.portal.modules.sso.domain.UserAppMappingVO;
import com.rhy.bdmp.portal.modules.sso.enums.AppUrlTypeEnum;
import com.rhy.bdmp.portal.modules.sso.service.ISSOClientService;
import com.rhy.bdmp.portal.modules.sso.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: lipeng
 * @Date: 2021/5/11
 * @description: 业务系统单点登录服务层实现
 * @version: 1.0
 */

@Service
@Slf4j
public class SSOClientServiceImpl implements ISSOClientService {

    @Resource
    IBaseAppService baseAppService;

    @Resource
    private SSOClientDao ssoClientDao;

    @Override
    @DataSource("baseData")
    public String getAppUrl(String appId) {
        /**
         * 总体流程：
         * 1、根据应用ID查询应用信息，判断当前应用是否接入统一认证中心
         * 2、如果接入（新系统），则直接使用请求头中的 token 即可，因为新系统会接入网关，由网关进行统一认证，而请求头中的token本身就是认证中心生成的，所以直接返回即可
         * 3、如果不接入（老系统），则获取当前登录用户，用登录用户获取映射用户，重新生成token
         * 4、将token作为参数调用业务系统的接口，获取业务系统的首页地址
         */

        //根据应用ID查询应用信息
        App app = baseAppService.detail(appId);
        if(app == null){
            throw new BadRequestException("未获取到该应用信息！");
        }
        String appUrl = app.getAppUrl();
        //查询当前用户是否拥有应用的权限
        if(!checkUserPermissions(appId)){
            throw new BadRequestException("当前登录用户没有该应用访问权限，请联系系统管理员。");
        }

        if(StrUtil.isBlank(appUrl)){
            throw new BadRequestException("该应用的“登录接口地址”配置信息为空！");
        }
        // 创建应用token,用于存储应用的用户信息
        String appToken = this.getAppToken(app);
        // 判断前端或后台跳转方式
        if (AppUrlTypeEnum.FRONT_WEB.getCode() == app.getAppUrlType()){
            JSONObject jsonObject = new JSONObject();
            if (-1 != appUrl.indexOf("?")){
                appUrl += "&token={}&appId={}";
            } else {
                appUrl += "?token={}&appId={}";
            }
            String result = StrUtil.format(appUrl, appToken, appId);
            jsonObject.set("code", ResultCode.SUCCESS.getCode());
            jsonObject.set("msg",  ResultCode.SUCCESS.getMsg());
            JSONObject data = new JSONObject();
            data.set("appUrl",result);
            data.set("token",appToken);
            jsonObject.set("data", data);
            return jsonObject.toString();
        }else {
            //调用应用接口获取页面跳转地址
            HashMap<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("appToken", appToken);
            log.info("---appToken---：{}", appToken);
            log.info("---开始调用业务系统接口，接口地址：{}---",appUrl);
            String result = HttpRequest.post(appUrl).form(paramMap).timeout(30000).execute().body();
            log.info("---调用业务系统接口结束，返回结果：{}---",result);
            return result;
        }
    }

    /**
     * 校验用户应用权限
     * @param appId
     */
    @DataSource("baseData")
    private boolean checkUserPermissions(String appId){
        //获取当前登录用户
        LoginUserVo loginUserVo = WebUtils.getCurrentUser();
        if (loginUserVo.getUserId() == null) {
            throw new BadRequestException("未获取到当前用户！");
        }
        boolean isCheck = true;
        int count = ssoClientDao.checkUserPermissions(loginUserVo.getUserId(),appId);
        if(count == 0){
            isCheck = false;
        }
        return isCheck;
    }

    @Override
    public String getAppToken(App app) {
        String appUserToken = "";
        //获取当前登录用户
        LoginUserVo loginUserVo = WebUtils.getCurrentUser();
        if (loginUserVo.getUserId() == null) {
            throw new BadRequestException("未获取到当前用户！");
        }
        //测试数据
//        LoginUserVo loginUserVo = new  LoginUserVo();
//        loginUserVo.setUserId("1");
//        loginUserVo.setUsername("lipeng");
        int isJoinAuthCenter = app.getIsJoinAuthCenter();
        if(isJoinAuthCenter == 1){ //接入
            appUserToken = WebUtils.getTokenStr();
        } else if(isJoinAuthCenter == 0){ //不接入
            //根据当前用户获取当前用户对应应用的映射用户
            AppUserVO appUserVO = ssoClientDao.queryUserMappingByUserId(loginUserVo.getUserId(), app.getAppId());
            if(appUserVO == null){
                throw new BadRequestException("当前用户没有配置该系统的用户映射，请联系系统管理员进行处理！");
            }
            UserAppMappingVO userMappingVO  = new UserAppMappingVO();
            Set<AppUserVO> appUserVOS = new HashSet<AppUserVO>();
            appUserVOS.add(appUserVO);
            userMappingVO.setUserId(loginUserVo.getUserId());
            userMappingVO.setUserName(loginUserVo.getUsername());
            userMappingVO.setUserMappings(appUserVOS);

            //根据用户映射生成token
            appUserToken = JwtTokenUtil.createToken(userMappingVO);
            if(StrUtil.isBlank(appUserToken)){
                throw new BadRequestException("应用单点登录时，创建用户映射TOKEN失败！");
            }
        }else{
            throw new BadRequestException("未获取到该应用的“是否接入认证中心”配置信息！");
        }
        return appUserToken;
    }
}
