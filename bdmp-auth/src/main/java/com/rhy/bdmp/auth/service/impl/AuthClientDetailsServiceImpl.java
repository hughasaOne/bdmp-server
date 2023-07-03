package com.rhy.bdmp.auth.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.auth.service.IAuthClientDetailsService;
import com.rhy.bdmp.base.modules.sys.dao.BaseAuthClientDetailsDao;
import com.rhy.bdmp.base.modules.sys.domain.po.AuthClientDetails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @description oauth2认证配置 服务实现
 * @author shuaichao
 * @date 2022-03-23 11:18
 * @version V1.0
 **/
@Service
public class AuthClientDetailsServiceImpl extends ServiceImpl<BaseAuthClientDetailsDao, AuthClientDetails> implements IAuthClientDetailsService {


    @Resource
    BaseAuthClientDetailsDao baseAuthClientDetailsDao;



    /**
     * 新增oauth2认证配置
     * @param authClientDetails
     * @return
     */
    @Override
    public int create(AuthClientDetails authClientDetails) {
        if (StrUtil.isNotBlank(authClientDetails.getId())) {
            throw new BadRequestException("A new AuthClientDetails cannot already have an id");
        }
        AuthClientDetails searchClientDetail = selectByClientId(authClientDetails.getClientId());
        if(searchClientDetail!=null){
            throw  new BadRequestException("客户端id"+authClientDetails.getClientId()+"重复,添加失败！");
        }

        authClientDetails.setCreateBy(WebUtils.getUserId());
        authClientDetails.setCreateTime(new Date());
        int result = getBaseMapper().insert(authClientDetails);
        return result;
    }

    public  AuthClientDetails selectByClientId(String clientId){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("client_id",clientId);
        return  baseAuthClientDetailsDao.selectOne(wrapper);
    }

    /**
     * 修改oauth2认证配置
     * @param authClientDetails
     * @return
     */
    @Override
    public int update(AuthClientDetails authClientDetails) {
       if (!StrUtil.isNotBlank(authClientDetails.getId())) {
           throw new BadRequestException("A new AuthClientDetails not exist id");
       }
        AuthClientDetails searchClientDetail = selectByClientId(authClientDetails.getClientId());
        if(searchClientDetail!=null&&!searchClientDetail.getId().equals(authClientDetails.getId())){
            throw  new BadRequestException("客户端id"+authClientDetails.getClientId()+"重复,修改失败！");

        }
        authClientDetails.setUpdateTime(new Date());
       authClientDetails.setUpdateBy(WebUtils.getUserId());
       int result = getBaseMapper().updateById(authClientDetails);
       return result;
    }

    @Override
    public List<AuthClientDetails> list(AuthClientDetails authClientDetails) {
        QueryWrapper<AuthClientDetails>   wrapper =new QueryWrapper<>();
        if(StringUtils.isNoneBlank(authClientDetails.getClientId())) {
            wrapper.eq("client_id", authClientDetails.getClientId());
        }
        return  baseAuthClientDetailsDao.selectList(wrapper);
    }

    @Override
    public PageUtil<AuthClientDetails> page(AuthClientDetails authClientDetails, Integer currentPage, Integer size) {
        Page<AuthClientDetails> detailPage = new Page<>(currentPage , size);
        QueryWrapper<AuthClientDetails>   wrapper =new QueryWrapper<>();
        if(StringUtils.isNoneBlank(authClientDetails.getClientId())) {
            wrapper.eq("client_id", authClientDetails.getClientId());
        }
        Page<AuthClientDetails> authClientDetailsPage = baseAuthClientDetailsDao.selectPage(detailPage, wrapper);
        return new PageUtil<AuthClientDetails>(authClientDetailsPage);
    }


}
