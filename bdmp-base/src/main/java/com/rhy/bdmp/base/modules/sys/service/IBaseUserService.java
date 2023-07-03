package com.rhy.bdmp.base.modules.sys.service;

import com.rhy.bdmp.base.modules.sys.domain.po.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.QueryVO;

import java.util.List;
import java.util.Set;

/**
 * @description 用户 服务接口
 * @author yanggj
 * @date 2021-09-24 16:32
 * @version V1.0
 **/
public interface IBaseUserService extends IService<User> {

    /**
     * 用户列表查询
     * @param queryVO 查询条件
     * @return
     */
    List<User> list(QueryVO queryVO);

    /**
     * 用户列表查询(分页)
     * @param queryVO 查询条件
     * @return
     */
    PageUtil<User> page(QueryVO queryVO);

    /**
     * 查看用户(根据ID)
     * @param userId
     * @return
     */
    User detail(String userId);

    /**
     * 新增用户
     * @param user
     * @return
     */
    int create(User user);

    /**
     * 修改用户
     * @param user
     * @return
     */
    int update(User user);

    /**
     * 删除用户
     * @param userIds
     * @return
     */
    int delete(Set<String> userIds);


}
