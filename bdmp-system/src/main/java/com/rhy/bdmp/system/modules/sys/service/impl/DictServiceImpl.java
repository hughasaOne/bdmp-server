package com.rhy.bdmp.system.modules.sys.service.impl;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rhy.bdmp.base.modules.sys.domain.po.Dict;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.Query;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.system.modules.sys.domain.vo.DictVO;
import com.rhy.bdmp.system.modules.sys.dao.DictDao;
import com.rhy.bdmp.system.modules.sys.service.DictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @ClassName: SystemServiceImpl
 * @Description:
 * @Author: 魏财富
 * @Date: 2021/4/15 17:31
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictDao, Dict> implements DictService {
    @Resource
    private DictDao sysDictDao;

    /**
     * @description: 查询字典目录
     * @date: 2021/4/20 10:08
     * @return: List<Dict>
     */
    @Override
    public List<Dict> findDirectory() {
        List<Dict> dictList = list(new QueryWrapper<Dict>().nested(t-> t.isNull("parent_id").or().eq("parent_id", "")).orderByAsc("sort").orderByDesc("create_time"));
        return dictList;
    }

    /**
     * @description: 分页查询
     * @date: 2021/5/8 13:13
     * @param: currentPage 当前页
     * @param: size 页面显示的条数
     * @param: parentCode 父节点code
     * @param: dictName 字典名称
     * @return: Page<Dict>
     */
    @Override
    public Page<Dict> queryPage(Integer currentPage, Integer size, String parentCode, String dictName) {
        if (null == currentPage || 0 >= currentPage){
            currentPage = 1;
        }
        if (null == size || 0 >= size){
            size = 20;
        }
        Page<Dict> page = new Page<>(currentPage, size);
        page.setOptimizeCountSql(false);
        return sysDictDao.queryPage(page, parentCode, dictName);
    }

    /**
     * @description: 根据code查询
     * @date: 2021/5/8 13:13
     * @param: parentCode 父节点code
     * @param: dictName 字典名称
     * @return: List<Dict>
     */
    @Override
    public List<Dict> queryByCode(String parentCode, String dictName) {
        return sysDictDao.queryByCode(parentCode, dictName);
    }

    /**
     * @description: //查询子节点
     * @date: 2021/5/8 11:30
     * @param: 父节点id
     * @param: includeId
     * @return: NodeVo
     */
    @Override
    public List<DictVO> findChild(String parentId, String includeId, QueryVO queryVO){
        //查询子节点
        Query<DictVO> query = new Query<DictVO>(queryVO);
        QueryWrapper<DictVO> queryWrapper = query.getQueryWrapper();
        List<DictVO> dictVOList = sysDictDao.findChild(parentId, includeId, queryWrapper);
        return dictVOList;
    }

    /**
     * @description: //查询子节点-只找字典
     * @date: 2021/5/8 11:30
     * @param: 父节点id
     * @param: includeId 包含节点
     * @return: NodeVo
     */
    @Override
    public List<DictVO> findChildDict(String parentId, QueryVO queryVO){
        //查询子节点
        Query<DictVO> query = new Query<DictVO>(queryVO);
        QueryWrapper<DictVO> queryWrapper = query.getQueryWrapper();
        List<DictVO> dictVOList = sysDictDao.findChildDict(parentId, queryWrapper);
        return dictVOList;
    }

    /**
     * @description: 插入系统字典
     * @date: 2021/4/15 19:30
     * @param: [dict]
     * @return: boolean
     */
    @Override
    public boolean createDict(Dict dict) {
        if (StrUtil.isBlank(dict.getCode())){
            throw new BadRequestException("code不为null");
        }
        if (StrUtil.isBlank(dict.getName())){
            throw new BadRequestException("name不为null");
        }
        List<Dict> alikeDict = list(new QueryWrapper<Dict>().eq("code",dict.getCode()));
        if (alikeDict.size() != 0){
            throw new BadRequestException("已存在相同的数据");
        }
        if (null == dict.getDatastatusid()){
            dict.setDatastatusid(1);
        }
        Date date = new Date();
        dict.setCreateTime(date);
        dict.setCreateBy(WebUtils.getUserId());
        dict.setUpdateTime(date);
        dict.setUpdateBy(WebUtils.getUserId());
        if (StrUtil.isBlank(dict.getParentId())){
            //认为其是字典类型,直接插入
            dict.setNodeType(1);
            return save(dict);
        }else {
            //带有父节点,判断该父结点是否存在
            Dict parent = getOne(new QueryWrapper<Dict>().eq("dict_id", dict.getParentId()));
            if (null == parent){
                throw new BadRequestException("该父节点不存在");
            }else {
                Integer parentLevel = parent.getLevel();
                if (null == parentLevel){
                    dict.setLevel(1);
                    dict.setNodeType(2);
                    return save(dict);
                }else {
                    dict.setLevel(parentLevel + 1);
                    dict.setNodeType(2);
                    return save(dict);
                }
            }
        }
    }

    /**
     * @description: 更新系统字典
     * @date: 2021/4/15 20:34
     * @param: [sysDict]
     * @return: boolean
     */
    @Override
    public boolean updateDict(Dict sysDict) {
        if (StrUtil.isBlank(sysDict.getDictId())){
            throw new BadRequestException("数据ID不存在");
        }
        if (StrUtil.isNotBlank(sysDict.getParentId())){
            Dict dictParent = getOne(new QueryWrapper<Dict>().eq("dict_id", sysDict.getParentId()));
            if (null == dictParent){
                throw new BadRequestException("数据父节点已经不存在");
            }else {
                if (null != sysDict.getNodeType() && 1 == sysDict.getNodeType()){
                    throw new BadRequestException("目录只能为根节点");
                }
            }
        }else {
            if (null != sysDict.getNodeType() && 1 != sysDict.getNodeType()){
                throw new BadRequestException("根节点只能为目录");
            }
        }
        List<Dict> dicts =  list(new QueryWrapper<Dict>().eq("code", sysDict.getCode()));
        if (null != dicts && dicts.size() > 0 && sysDict.getDictId().equals(dicts.get(0).getDictId())){
            //修改自身
            sysDict.setUpdateBy(WebUtils.getUserId());
            sysDict.setUpdateTime(new Date());
            return updateById(sysDict);
        }else {
            if (null == dicts || 0 == dicts.size()){
                //没有相同数据
                sysDict.setUpdateBy(WebUtils.getUserId());
                sysDict.setUpdateTime(new Date());
                return updateById(sysDict);
            }else {
                throw new BadRequestException("已存在相同数据");
            }
        }
    }

    /**
     * @description: 删除系统字典
     * @date: 2021/4/19 10:50
     * @param: [dictIds]
     * @return: int
     */
    @Override
    public int deleteDict(Set<String> dictIds) {
        if (dictIds.isEmpty()){
            throw new BadRequestException("要删除的id不为null");
        }
        //判断是否存在子结点
        List<Dict> subDicts = list(new QueryWrapper<Dict>().in("parent_id", dictIds));
        if (subDicts.size() == 0){
            //认为没有子结点，直接删除
            return getBaseMapper().deleteBatchIds(dictIds);
        }
        throw new BadRequestException("请先删除其子节点");
    }

    @Override
    public Dict detail(String dictId) {
        if (!StrUtil.isNotBlank(dictId)) {
            throw new BadRequestException("not exist dictId");
        }
        Dict dict = getBaseMapper().selectById(dictId);
        return dict;
    }
}
