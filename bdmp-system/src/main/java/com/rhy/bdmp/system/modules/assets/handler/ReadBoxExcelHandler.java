package com.rhy.bdmp.system.modules.assets.handler;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.rhy.bdmp.system.modules.assets.domain.vo.UploadBoxEcelVo;

import java.util.ArrayList;
import java.util.List;

public class ReadBoxExcelHandler extends AnalysisEventListener<UploadBoxEcelVo> {
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 3000;
    List<UploadBoxEcelVo> list = new ArrayList<UploadBoxEcelVo>();

    @Override
    public void invoke(UploadBoxEcelVo data, AnalysisContext context) {
        list.add(data);
        if (list.size() >= BATCH_COUNT) {
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
    }
}
