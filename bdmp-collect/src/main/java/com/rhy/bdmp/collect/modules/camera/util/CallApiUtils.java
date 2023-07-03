package com.rhy.bdmp.collect.modules.camera.util;

import cn.hutool.core.map.MapUtil;
import cn.hutool.json.JSONUtil;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bdmp.collect.modules.camera.config.VideoProperties;
import com.rhy.bdmp.collect.modules.camera.domain.vo.Login;
import com.rhy.bdmp.collect.modules.camera.domain.vo.Tree;
import com.rhy.bdmp.collect.modules.camera.service.VideoComprehensiveManageFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

@Component("callApiUtils")
public class CallApiUtils {
    @Autowired
    private VideoProperties videoProperties;

    @Resource
    private VideoComprehensiveManageFeignClient videoComprehensiveManageFeignClient;

    //1.登陆
    public Login login() {
        Map<String, String> yuntai = videoProperties.getYuntai();
        String username = yuntai.get("username");
        String password = yuntai.get("password");
        String mac = yuntai.get("mac");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("username", username);
        map.add("password", password);
        map.add("mac", mac);
        HttpEntity request = new HttpEntity<>(map, headers);
        RestTemplate restTemplate = new RestTemplate();
        String url = yuntai.get("platformAddress") + yuntai.get("login");
        ResponseEntity<Login> responseEntity = restTemplate.postForEntity(url, request, Login.class);
        return responseEntity.getBody();
    }

    //2.获取树节点资源
    public Tree tree(String id, String name, int pageNumber, int pageSize, int sortType, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", token);
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        map.add("id", id);
        map.add("name", name);
        map.add("pageNumber", pageNumber);
        map.add("pageSize", pageSize);
        map.add("sortType", sortType);
        HttpEntity request = new HttpEntity<>(map, headers);
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> yuntai = videoProperties.getYuntai();
        String url = yuntai.get("platformAddress")+yuntai.get("listPage");
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, request, String.class);
        String data = responseEntity.getBody().replaceFirst("rows", "data");
        return JSONUtil.toBean(data, Tree.class);
    }

    public  String tlVideoResource(String pageSize, String page, String type){
        // 1. 封装参数
        if(Integer.valueOf(page)<0){
            throw new BadRequestException("页码有误");
        }
        Map<String, Object> params = MapUtil.newHashMap();
        Map<String, String> tengluMap = videoProperties.getTenglu();
        String clientId = MapUtil.getStr(tengluMap, "clientId");
        Timestamp timeStamp = new Timestamp(new Date().getTime());
        String nonceString = SignUtils.getRandomString(32);
        String key = MapUtil.getStr(tengluMap, "key");
        params.put("clientId", clientId);
        params.put("type", type);
        params.put("pageIndex", page);
        params.put("pageCount", pageSize);
        params.put("timeStamp", timeStamp.getTime()/1000+"");
        params.put("nonceString", nonceString);

        // 2. 验签
        String sign = SignUtils.generateSignature(params, key);
        params.put("sign", sign);

        // 3. OpenFeign请求
        String deviceAccountList = null;
        try {
            String tlPlatformAddress = videoProperties.getTenglu().get("platformAddress");
            deviceAccountList = videoComprehensiveManageFeignClient.getCameraList(new URI(tlPlatformAddress),params);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return deviceAccountList;
    }

}
