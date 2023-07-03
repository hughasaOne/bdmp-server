package com.rhy.bdmp.base.modules.assets.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.base.modules.assets.domain.po.File;
import com.rhy.bdmp.base.modules.assets.dao.BaseAssetsFileDao;
import com.rhy.bdmp.base.modules.assets.service.IBaseFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.Query;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bcp.common.util.WebUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @description 文件 服务实现
 * @author yanggj
 * @date 2021-10-22 11:44
 * @version V1.0
 **/
@Service("baseAssetsFileService")
public class BaseFileServiceImpl extends ServiceImpl<BaseAssetsFileDao, File> implements IBaseFileService {

    /**
     * 文件列表查询
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<File> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<File> query = new Query<File>(queryVO);
            QueryWrapper<File> queryWrapper = query.getQueryWrapper();
                queryWrapper.orderByDesc("create_time");
                List<File> entityList = getBaseMapper().selectList(queryWrapper);
                return entityList;
        }
        //2、无查询条件
        QueryWrapper<File> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        return getBaseMapper().selectList(queryWrapper);
    }

    /**
     * 文件列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<File> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<File> query = new Query<File>(queryVO);
            Page<File> page = query.getPage();
            QueryWrapper<File> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByDesc("create_time");
                page = getBaseMapper().selectPage(page, queryWrapper);
                return new PageUtil<File>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看文件(根据ID)
     * @param fileId
     * @return
     */
    @Override
    public File detail(String fileId) {
        if (!StrUtil.isNotBlank(fileId)) {
            throw new BadRequestException("not exist fileId");
        }
        File file = getBaseMapper().selectById(fileId);
        return file;
    }

    /**
     * 新增文件
     * @param file
     * @return
     */
    @Override
    public int create(File file) {
        if (StrUtil.isNotBlank(file.getFileId())) {
            throw new BadRequestException("A new File cannot already have an fileId");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        file.setCreateBy(currentUser);
        file.setCreateTime(currentDateTime);
        int result = getBaseMapper().insert(file);
        return result;
    }

    /**
     * 修改文件
     * @param file
     * @return
     */
    @Override
    public int update(File file) {
       if (!StrUtil.isNotBlank(file.getFileId())) {
           throw new BadRequestException("A new File not exist fileId");
       }
       int result = getBaseMapper().updateById(file);
       return result;
    }

    /**
     * 删除文件
     * @param fileIds
     * @return
     */
    @Override
    public int delete(Set<String> fileIds) {
        int result = getBaseMapper().deleteBatchIds(fileIds);
        return result;
    }

}
