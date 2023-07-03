package com.rhy.bdmp.open.modules.app.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bdmp.open.modules.app.dao.NoticeConfigDao;
import com.rhy.bdmp.open.modules.app.domain.bo.NoticeConfigListBo;
import com.rhy.bdmp.open.modules.app.domain.po.NoticeConfig;
import com.rhy.bdmp.open.modules.app.service.INoticeConfigService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NoticeConfigServiceImpl extends ServiceImpl<NoticeConfigDao, NoticeConfig> implements INoticeConfigService {

    @Override
    public List<String> getNoticeConfigList(List<NoticeConfigListBo> noticeConfigListBo) {
        String appIdent = "";
        String userId = "";
        LambdaQueryWrapper<NoticeConfig> wrapper;
        List<NoticeConfig> noticeConfigList = new ArrayList<>();
        for (NoticeConfigListBo noticeConfigBo1 : noticeConfigListBo) {
            if (StrUtil.isNotBlank(noticeConfigBo1.getAppIdent())){
                appIdent = noticeConfigBo1.getAppIdent();
            }
            if (StrUtil.isNotBlank(noticeConfigBo1.getUserId())){
                userId = noticeConfigBo1.getUserId();
            }
            wrapper = new QueryWrapper<NoticeConfig>().lambda();
            if (StrUtil.isNotBlank(appIdent)){
                wrapper.eq(NoticeConfig::getAppIdent,appIdent);
            }
            if (StrUtil.isNotBlank(userId)){
                wrapper.eq(NoticeConfig::getUserId,userId);
            }
            if (StrUtil.isNotBlank(noticeConfigBo1.getAppIdent()) || StrUtil.isNotBlank(noticeConfigBo1.getUserId())){
                wrapper.select(NoticeConfig::getDeviceId,NoticeConfig::getLastLiveTime);
                noticeConfigList.addAll(list(wrapper));
            }
        }
        Map<String, List<NoticeConfig>> listMap = noticeConfigList.stream().collect(Collectors.groupingBy(NoticeConfig::getDeviceId));
        List<String> resList = new ArrayList<>();
        for (String key : listMap.keySet()) {
            List<NoticeConfig> sortedNoticeConfig = listMap.get(key).stream()
                    .sorted(Comparator.comparing(NoticeConfig::getLastLiveTime).reversed())
                    .collect(Collectors.toList());
            resList.add(sortedNoticeConfig.get(0).getDeviceId());
        }
        return resList;
    }

    @Override
    public void saveOrUpdateBatchNoticeConfig(List<NoticeConfig> noticeConfigList) {
        Date date = new Date();
        LambdaQueryWrapper<NoticeConfig> wrapper;
        List<NoticeConfig> saveList = new ArrayList<>();
        List<NoticeConfig> updateList = new ArrayList<>();
        for (NoticeConfig noticeConfig : noticeConfigList) {
            // 参数处理与校验
            this.paramHandler(noticeConfig,date);
            wrapper = new QueryWrapper<NoticeConfig>().lambda()
                    .eq(NoticeConfig::getUserId, noticeConfig.getUserId())
                    .eq(NoticeConfig::getAppIdent, noticeConfig.getAppIdent());
            NoticeConfig noticeConfig1 = getOne(wrapper);
            if (null == noticeConfig1){
                // 新增
                saveList.add(noticeConfig);
            }
            else {
                // 修改
                noticeConfig.setId(noticeConfig1.getId());
                noticeConfig.setCreateTime(noticeConfig1.getCreateTime());
                updateList.add(noticeConfig);
            }

        }
        super.saveBatch(saveList);
        super.updateBatchById(updateList);
    }

    private void paramHandler(NoticeConfig noticeConfig,Date date) {
        if (StrUtil.isBlank(noticeConfig.getUserId())){
            throw new BadRequestException("userId不为空");
        }
        if (StrUtil.isBlank(noticeConfig.getAppIdent())){
            throw new BadRequestException("appIdent不为空");
        }

        noticeConfig.setCreateBy(noticeConfig.getUserId());
        noticeConfig.setUpdateBy(noticeConfig.getUserId());
        noticeConfig.setCreateTime(date);
        noticeConfig.setLastLiveTime(date);
    }
}
