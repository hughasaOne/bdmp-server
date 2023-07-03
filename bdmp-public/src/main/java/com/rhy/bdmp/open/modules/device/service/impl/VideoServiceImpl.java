package com.rhy.bdmp.open.modules.device.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.rhy.bdmp.base.modules.assets.domain.po.Video;
import com.rhy.bdmp.open.modules.device.dao.VideoDao;
import com.rhy.bdmp.open.modules.device.domain.bo.DeviceDetailBo;
import com.rhy.bdmp.open.modules.device.domain.bo.DeviceListBo;
import com.rhy.bdmp.open.modules.device.domain.vo.DeviceVo;
import com.rhy.bdmp.open.modules.device.domain.vo.VideoVo;
import com.rhy.bdmp.open.modules.device.service.IDeviceService;
import com.rhy.bdmp.open.modules.device.service.IVideoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("videoServiceImpl")
public class VideoServiceImpl implements IVideoService {
    @Resource
    private VideoDao videoDao;

    @Resource(name = "deviceServiceImplV1")
    private IDeviceService deviceService;

    @Override
    public VideoVo detail(DeviceDetailBo deviceDetailBo) {
        DeviceVo deviceDetail = deviceService.detail(deviceDetailBo);
        Video detail = videoDao.selectById(deviceDetail.getDeviceIdOld());

        VideoVo videoVo = new VideoVo();
        BeanUtil.copyProperties(detail,videoVo);
        BeanUtil.copyProperties(deviceDetail,videoVo);
        return videoVo;
    }

    @Override
    public List<VideoVo> list(DeviceListBo deviceListBo) {
        List<VideoVo> list = (List<VideoVo>)deviceService.list(deviceListBo);
        return list;
    }
}
