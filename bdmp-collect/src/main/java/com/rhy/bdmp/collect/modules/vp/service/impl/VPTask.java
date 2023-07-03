package com.rhy.bdmp.collect.modules.vp.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.collect.modules.vp.domain.po.Resource;
import com.rhy.bdmp.collect.modules.vp.service.IBaseVPService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableScheduling
public class VPTask {
    @Value("${custom.vp.address}")
    private String address;

    @javax.annotation.Resource
    private IBaseVPService baseVPService;

    /**
     * 联系人采集,2小时一次
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    @Transactional(rollbackFor = BadRequestException.class)
    public void collectContact() {
        // 获取联系人
        String resStr = this.getContact();
        List<Map> resData = (List<Map>)JSONUtil.toBean(resStr, Map.class).get("data");
        List<Resource> dirList = new ArrayList<>();
        List<Resource> terminalList = new ArrayList<>();
        // 数据处理
        this.originalDataHandler(resData,dirList,terminalList);
        // 目录去重
        Map<String, List<Resource>> deduplicatedDirMap = dirList.stream().collect(Collectors.groupingBy(Resource::getId));
        List<Resource> deduplicatedList = new ArrayList<>();
        for (String key : deduplicatedDirMap.keySet()) {
            deduplicatedList.add(deduplicatedDirMap.get(key).get(0));
        }
        // 终端去重
        Map<String, List<Resource>> deduplicatedTerminalMap = terminalList.stream().collect(Collectors.groupingBy(Resource::getId));
        for (String key : deduplicatedTerminalMap.keySet()) {
            deduplicatedList.add(deduplicatedTerminalMap.get(key).get(0));
        }
        // 新增或更新
        List<Resource> existedResource = baseVPService.list(null);
        List<Resource> newResource = new ArrayList<>();
        this.needSubmitDataHandler(deduplicatedList,existedResource,newResource);

        baseVPService.saveBatch(newResource);
        baseVPService.updateBatchById(existedResource);
    }

    private void needSubmitDataHandler(List<Resource> deduplicatedList,List<Resource> existedResource,List<Resource> newResource) {
        Date date = new Date();
        String userId = WebUtils.getUserId();
        if (CollUtil.isNotEmpty(existedResource)){
            for (Resource resource1 : deduplicatedList) {
                boolean existed = false;
                for (Resource resource : existedResource) {
                    if (resource.getId().equals(resource1.getId())){
                        existed = true;
                        // 修改
                        resource.setUpdateTime(date);
                        resource.setUpdateBy(userId);
                        resource.setStatus(resource1.getStatus());
                        resource.setNodeType(resource1.getNodeType());
                    }
                }
                if (!existed) {
                    // 新增
                    if (StrUtil.isNotBlank(resource1.getId())){
                        newResource.add(resource1);
                    }
                }
            }
        }
        else {
            // 新增
            newResource.addAll(deduplicatedList);
        }

    }

    private void originalDataHandler(List<Map> resData,List<Resource> dirList,List<Resource> terminalList) {
        Resource dir;
        Resource terminal;
        String domainName = "domainName";
        String userId = WebUtils.getUserId();
        Date date = new Date();
        Long sort = 0L;
        Integer datastatusid = 1;
        for (Map data : resData) {
            // 目录
            dir = new Resource();
            dir.setId(data.get(domainName).toString().trim());
            dir.setName(data.get(domainName).toString());
            dir.setParentId(data.get("parent").toString().trim());
            dir.setStatus(null);
            dir.setNodeType(1);
            dir.setCreateBy(userId);
            dir.setCreateTime(date);
            dir.setUpdateBy(userId);
            dir.setUpdateTime(date);
            dir.setSort(sort);
            dir.setDatastatusid(datastatusid);
            dirList.add(dir);
            // 终端
            terminal = new Resource();
            terminal.setId(data.get("id").toString().trim());
            terminal.setName(data.get("deviceName").toString());
            terminal.setParentId(data.get(domainName).toString().trim());
            Integer status = data.get("status").toString().equals("") ? 0 : Integer.parseInt(data.get("status").toString());
            terminal.setStatus(status);
            terminal.setNodeType(2);
            terminal.setCreateBy(userId);
            terminal.setCreateTime(date);
            terminal.setUpdateBy(userId);
            terminal.setUpdateTime(date);
            terminal.setSort(sort);
            terminal.setDatastatusid(datastatusid);
            terminalList.add(terminal);
        }
    }


    private String getContact(){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        String res = "";
        res = restTemplate.postForEntity(address+"jtkj/api/getDeviceTree",null,String.class).getBody();
        return res;
    }
}
