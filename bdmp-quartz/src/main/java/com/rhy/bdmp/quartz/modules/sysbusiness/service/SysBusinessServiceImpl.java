package com.rhy.bdmp.quartz.modules.sysbusiness.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.base.modules.sys.dao.BaseSysBusinessDao;
import com.rhy.bdmp.base.modules.sys.domain.po.SysBusiness;
import com.rhy.bdmp.quartz.modules.sysbusiness.dao.SysBusinessDao;
import com.rhy.bdmp.quartz.modules.sysbusiness.dao.UserDao;
import com.rhy.bdmp.quartz.modules.sysbusiness.domain.bo.SysBusinessExt;
import com.rhy.bdmp.quartz.modules.sysbusiness.domain.vo.SysBusinessExtVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rhy.bdmp.base.modules.sys.domain.po.User;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author shuaichao
 * @create 2022-03-15 14:35
 */
@Service
@Slf4j
public class SysBusinessServiceImpl implements ISysBusinessService {
    public final JSONConfig jsonConfig = new JSONConfig().setIgnoreNullValue(false);

    @Resource
    BaseSysBusinessDao baseSysBusinessDao;

    @Resource
    SysBusinessDao sysBusinessDao;

    @Resource
    private UserDao userDao;

    @Override
    public List<SysBusinessExt> list(SysBusiness sysBusiness) {

        return sysBusinessDao.findByParam(sysBusiness);
    }

    /**
     * 新增
     *
     * @param sysBusiness
     * @return
     */
    @Override
    @Transactional
    public int create(SysBusiness sysBusiness) {
        if (StrUtil.isNotBlank(sysBusiness.getBusinessId())) {
            throw new BadRequestException("A new SysBusiness cannot already have an sysBusinessId");
        }

        Date currentDateTime = DateUtil.date();
        sysBusiness.setCreateTime(currentDateTime);
        sysBusiness.setCreateBy(WebUtils.getUserId());

        return baseSysBusinessDao.insert(sysBusiness);
    }

    /**
     * 修改
     *
     * @param sysBusiness
     * @return
     */
    @Override
    public int update(SysBusiness sysBusiness) {
        if (!StrUtil.isNotBlank(sysBusiness.getBusinessId())) {
            throw new BadRequestException("A new SysBusiness not exist sysBusinessId");
        }
        Date currentDateTime = DateUtil.date();
        sysBusiness.setUpdateTime(currentDateTime);
        sysBusiness.setUpdateBy(WebUtils.getUserId());
        int result = baseSysBusinessDao.updateById(sysBusiness);
        return result;
    }

    /**
     * 删除
     *
     * @param sysBusinessIds
     * @return
     */
    @Override
    public int delete(List<String> sysBusinessIds) {
        List<SysBusiness> sysBusinesss = baseSysBusinessDao.selectBatchIds(sysBusinessIds);
        //获取父节点id(排除父ID为0的)
        Map<String, List<SysBusiness>> map = sysBusinesss.stream().filter(item -> !"0".equals(item.getParentId())).collect(Collectors.groupingBy(SysBusiness::getParentId));
        Set<Map.Entry<String, List<SysBusiness>>> entries = map.entrySet();

        //判断该节点是否有子节点，有子节点不允许删除
        List<SysBusinessExt> list = sysBusinessDao.findByPIds(sysBusinessIds);
        if (CollectionUtil.isNotEmpty(list)) {
            throw new BadRequestException("删除失败！要删除的节点包含子节点，请先删除子节点。");
        }
        return baseSysBusinessDao.deleteBatchIds(sysBusinessIds);
    }

    /**
     * 树形菜单
     *
     * @return 树形list
     */
    @Override
    public List<Tree<String>> treeList() {
        User user = getUserById();
        List<TreeNode<String>> nodeList = new ArrayList<>();
        boolean isAdmin = user.getIsAdmin() != null && user.getIsAdmin().intValue() == 1;
        List<SysBusinessExtVo> voList = sysBusinessDao.findByUserId(user.getUserId(), isAdmin);
        voList.forEach(fac -> {
            Integer sort = fac.getSort() == null ? 0 : fac.getSort();
            nodeList.add(new TreeNode<String>()
                    .setId(fac.getId())
                    .setParentId(fac.getParentId())
                    .setName(fac.getLabel())
                    .setWeight(sort)
                    .setExtra(JSONUtil.createObj(jsonConfig)
                            .putOnce("id", fac.getId())
                            .putOnce("label", fac.getLabel())
                            .putOnce("appId", fac.getAppId())
                            .putOnce("businessId", fac.getBusinessId())
                            .putOnce("sort", sort)
                            .putOnce("nodeType", fac.getNodeType())));
        });
        return TreeUtil.build(nodeList, "0");
    }

    /**
     * 获取当前登录用户
     *
     * @return
     */
    @Override
    public User getUserById() {
        String userId = WebUtils.getUserId();
        if (StrUtil.isBlank(userId)) {
            throw new BadRequestException("未获取到当前用户");
        }
        return userDao.selectById(userId);
    }

}
