package com.rhy.bdmp.system.modules.assets.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.PageUtils;
import com.rhy.bcp.common.util.Query;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.base.modules.assets.domain.po.Dict;
import com.rhy.bdmp.system.modules.assets.dao.AssetsDictDao;
import com.rhy.bdmp.system.modules.assets.domain.bo.DictBO;
import com.rhy.bdmp.system.modules.assets.domain.vo.DictVO;
import com.rhy.bdmp.system.modules.assets.service.AssetsDictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


/**
 * @ClassName:DictServiceImpl
 * @Description:台账字典服务实现
 * @Author:魏财富
 * @Date:2021/4/15 11:19
 */
@Service
public class AssetsDictServiceImpl extends ServiceImpl<AssetsDictDao, Dict> implements AssetsDictService {
    @Resource
    private AssetsDictDao assetsDictDao;

    @Override
    public Object getDictAssets(DictBO dictBO) {
        Integer currentPage = dictBO.getCurrentPage();
        Integer limit = dictBO.getLimit();
        if (currentPage == null){
            // 不分页
            if (null != dictBO.getIsDeviceDict() && true == dictBO.getIsDeviceDict()){
                return assetsDictDao.getDeviceDictByDictId(dictBO);
            }
            else {
                return assetsDictDao.getDictAssets(dictBO);
            }
        }
        // 分页
        Page<Map<String,Object>> page = new Page<>(currentPage, limit);
        page.setOptimizeCountSql(true);
        return new PageUtils(assetsDictDao.getDictAssets(page, dictBO));
    }

    /**
     * 查询字典（分页）
     * @param currentPage 当前页
     * @param size 页大小
     * @param parentCode 父节点code
     * @param dictName 字典名称
     * @return
     */
    @Override
    public Page<Dict> queryPage(Integer currentPage, Integer size, String parentCode, String dictName) {
        if (null == currentPage || 0 >= currentPage){
            currentPage = 1;
        }
        if (null == size || 0 >= size){
            size = 20;
        }
        Page<Dict> page = new Page<Dict>(currentPage, size);
        page.setOptimizeCountSql(false);
        return assetsDictDao.queryPage(page, parentCode, dictName);
    }

    /**
     * 查询字典根据字典类型CODE
     * @param parentCode 父节点code
     * @param dictName 字典名称
     * @return
     */
    @Override
    public List<DictVO> queryByCode(String parentCode, String dictName,Boolean useInnerParentId) {
        List<DictVO> dictList = null;
        dictList = assetsDictDao.queryByCode(parentCode, dictName,useInnerParentId);
        if ("32330".equals(parentCode)){
            dictList = dictList.stream().filter(dict -> ("32330711".equals(dict.getCode()))
                    || ("32330712".equals(dict.getCode()))
                    || Pattern.matches("^3233.*[0-9]00$", dict.getCode())).collect(Collectors.toList());
            return dictList;
        }else{
            return dictList;
        }
    }

    /**
     * @description: 查询字典目录
     * @date: 2021/4/20 10:08
     * @return: java.util.List<com.rhy.bcp.common.domain.vo.NodeVo>
     */
    @Override
    public List<Dict> findDirectory(String param) {
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<Dict>().nested(t -> t.isNull("parent_id").or().eq("parent_id", "")).orderByAsc("sort").orderByDesc("create_time");
        if (StrUtil.isNotBlank(param)){
            dictQueryWrapper.and(wrapper -> wrapper.eq("dict_id", param)
                    .or().like("name", param)
                    .or().eq("code", param));
        }
        return list(dictQueryWrapper);
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
        List<DictVO> dictVOList = assetsDictDao.findChild(parentId, includeId, queryWrapper);
        return dictVOList;
    }

    @Override
    public Map<String, Object> detail(String dictId) {
        return assetsDictDao.detail(dictId);
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
        List<DictVO> dictVOList = assetsDictDao.findChildDict(parentId, queryWrapper);
        return dictVOList;
    }

    /**
     * @description: 保存台账字典，
     * @date: 2021/4/15 11:23
     * @param:[dict] 台账字典实体
     * @return: boolean
     */
    @Override
    public boolean saveDict(Dict dict) {
        if (StrUtil.isBlank(dict.getCode())){
            throw new BadRequestException("code不为null");
        }
        if (StrUtil.isBlank(dict.getName())){
            throw new BadRequestException("name不为null");
        }
        List<Dict> alikeDict = list(new QueryWrapper<Dict>().eq("code",dict.getCode()));
        if (null != alikeDict && alikeDict.size() > 0){
            throw new BadRequestException("存在相同CODE");
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
        }else{
            Dict parentDict = getOne(new QueryWrapper<Dict>().eq("dict_id", dict.getParentId()));
            if (null  == parentDict){
                throw new BadRequestException("该父节点不存在");
            }else {
                Integer parentLevel =  parentDict.getLevel();
                if (null == parentLevel || 0 == parentLevel){
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
     * @description: 修改台账字典
     * @date: 2021/4/15 14:34
     * @param:[dict] 台账字典实体
     * @return: boolean
     */
    @Override
    public boolean updateDict(Dict dict) {
        if (StrUtil.isBlank(dict.getDictId())){
            throw new BadRequestException("数据ID不存在");
        }else {
            if (StrUtil.isNotBlank(dict.getParentId())){
                Dict dictParent = getOne(new QueryWrapper<Dict>().eq("dict_id", dict.getParentId()));
                if (null == dictParent){
                    throw new BadRequestException("数据父节点已经不存在");
                }else{
                    if (null != dict.getNodeType() && 1 == dict.getNodeType()){
                        throw new BadRequestException("目录只能为根节点");
                    }
                }
            }else {
                if (null != dict.getNodeType() && 1 != dict.getNodeType()){
                    throw new BadRequestException("根节点只能为目录");
                }
            }
            List<Dict> dicts =  list(new QueryWrapper<Dict>().eq("code", dict.getCode()));
            if (null != dicts && dicts.size() > 0 && dict.getDictId().equals(dicts.get(0).getDictId())){
                //修改本身
                dict.setUpdateBy(WebUtils.getUserId());
                dict.setUpdateTime(new Date());
                return updateById(dict);
            }else {
                if (null == dicts || 0 == dicts.size()){
                    //没有相同数据
                    dict.setUpdateBy(WebUtils.getUserId());
                    dict.setUpdateTime(new Date());
                    return updateById(dict);
                }else {
                    throw new BadRequestException("已存在相同数据");
                }
            }
        }
    }

    /**
     * @description: 删除台账字典
     * @date: 2021/4/15 14:50
     * @param: [dictIds]
     * @return: boolean
     */
    @Override
    public int deleteDict(Set<String> dictIds) {
        List<Dict> dicts = list(new QueryWrapper<Dict>().in("parent_id", dictIds));
        if (null != dicts && dicts.size() > 0){
            throw new BadRequestException("请先删除子节点");
        }else {
            //认为没有子结点，直接删除
            return getBaseMapper().deleteBatchIds(dictIds);
        }
    }
}
