package com.rhy.bdmp.open.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bdmp.open.modules.app.domain.bo.NoticeConfigListBo;
import com.rhy.bdmp.open.modules.app.domain.po.NoticeConfig;

import java.util.List;

public interface INoticeConfigService extends IService<NoticeConfig> {
    List<String> getNoticeConfigList(List<NoticeConfigListBo> noticeConfigListBo);

    void saveOrUpdateBatchNoticeConfig(List<NoticeConfig> noticeConfigList);
}
