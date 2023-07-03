package com.rhy.bdmp.portal.modules.commonbusiness.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rhy.bcp.common.datasource.annotation.DataSource;
import com.rhy.bcp.common.domain.vo.LoginUserVo;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.base.modules.sys.dao.BaseCommonBusinessDao;
import com.rhy.bdmp.base.modules.sys.domain.po.CommonBusiness;
import com.rhy.bdmp.portal.modules.commonbusiness.service.CommonBusinessService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @description  服务实现
 * @author shuaichao
 * @date 2022-03-03 09:54
 * @version V1.0
 **/
@Service
public class CommonBusinessServiceImpl  implements CommonBusinessService {

    @Resource
    BaseCommonBusinessDao baseCommonBusinessDao;

    /**
     * 列表查询
     * @return
     */
    @Override
    public List<CommonBusiness> list() {
        String userId = WebUtils.getUserId();
        QueryWrapper<CommonBusiness> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);

        queryWrapper.orderByAsc("create_time");
        return baseCommonBusinessDao.selectList(queryWrapper);
    }



    /**
     * 查看(根据ID)
     * @param commonBusinessId
     * @return
     */
    @Override
    public CommonBusiness detail(String commonBusinessId) {
        if (!StrUtil.isNotBlank(commonBusinessId)) {
            throw new BadRequestException("not exist commonBusinessId");
        }
        CommonBusiness commonBusiness = baseCommonBusinessDao.selectById(commonBusinessId);
        return commonBusiness;
    }

    /**
     * 新增
     * @param commonBusiness
     * @return
     */
    @Override
    public int create(CommonBusiness commonBusiness) {
        if (StrUtil.isNotBlank(commonBusiness.getCommonBusinessId())) {
            throw new BadRequestException("A new CommonBusiness cannot already have an commonBusinessId");
        }
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        commonBusiness.setUserId(currentUser);
        commonBusiness.setCreateBy(currentUser);
        commonBusiness.setCreateTime(currentDateTime);
        commonBusiness.setUpdateBy(currentUser);
        commonBusiness.setUpdateTime(currentDateTime);
        int result = baseCommonBusinessDao.insert(commonBusiness);
        return result;
    }

    /**
     * 修改
     * @param commonBusiness
     * @return
     */
    @Override
    public int update(CommonBusiness commonBusiness) {
       if (!StrUtil.isNotBlank(commonBusiness.getCommonBusinessId())) {
           throw new BadRequestException("A new CommonBusiness not exist commonBusinessId");
       }
       String currentUser = WebUtils.getUserId();
       Date currentDateTime = DateUtil.date();
        commonBusiness.setUserId(currentUser);
       commonBusiness.setUpdateBy(currentUser);
       commonBusiness.setUpdateTime(currentDateTime);
       int result = baseCommonBusinessDao.updateById(commonBusiness);
       return result;
    }

    /**
     * 删除
     * @param commonBusinessIds
     * @return
     */
    @Override
    public int delete(Set<String> commonBusinessIds) {
        int result = baseCommonBusinessDao.deleteBatchIds(commonBusinessIds);
        return result;
    }

}
