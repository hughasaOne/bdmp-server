package com.rhy.bdmp.base.modules.assets.service;

import com.rhy.bdmp.base.modules.assets.domain.po.File;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;

import java.util.List;
import java.util.Set;

/**
 * @description 文件 服务接口
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
public interface IBaseFileService extends IService<File> {

    /**
     * 文件列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<File> list(QueryVO queryVO);

    /**
     * 文件列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<File> page(QueryVO queryVO);

    /**
     * 查看文件(根据ID)
     * @param fileId
     * @return
     */
    File detail(String fileId);

    /**
     * 新增文件
     * @param file
     * @return
     */
    int create(File file);

    /**
     * 修改文件
     * @param file
     * @return
     */
    int update(File file);

    /**
     * 删除文件
     * @param fileIds
     * @return
     */
    int delete(Set<String> fileIds);


}
