package com.rhy.bdmp.gateway.modules.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.gateway.config.GatewayRoutesRefresher;
import com.rhy.bdmp.gateway.modules.dao.BaseGatewayEnvDao;
import com.rhy.bdmp.gateway.modules.dao.BaseGatewayRouteDao;
import com.rhy.bdmp.gateway.modules.domain.bo.GetRoutesBo;
import com.rhy.bdmp.gateway.modules.domain.po.GatewayEnvPo;
import com.rhy.bdmp.gateway.modules.domain.po.GatewayRoutePo;
import com.rhy.bdmp.gateway.modules.domain.to.FilterTO;
import com.rhy.bdmp.gateway.modules.domain.to.PredicatesTO;
import com.rhy.bdmp.gateway.modules.service.IGatewayRouteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 路由管理
 * @author weicaifu
 */
@Service
@Slf4j
public class GatewayRouteServiceImpl extends ServiceImpl<BaseGatewayRouteDao, GatewayRoutePo> implements IGatewayRouteService {
    @Resource
    private BaseGatewayEnvDao baseGatewayEnvDao;

    @Resource
    private GatewayProperties gatewayProperties;

    @Resource
    private GatewayRoutesRefresher routesRefresher;

    @Value("${spring.profiles.active}")
    private String profiles;

    @Override
    public PageUtil<GatewayRoutePo> getRoutes(GetRoutesBo getRoutesBo){
        String envId = getRoutesBo.getEnvId();
        Integer currentPage = getRoutesBo.getCurrentPage();
        Integer limit = getRoutesBo.getLimit();
        String name = getRoutesBo.getName();

        GatewayEnvPo envPo = baseGatewayEnvDao.selectById(envId);
        if (null == envPo){
            return null;
        }
        log.info("查询路由列表，当前环境[{}]，查询id[{}]",envPo.getId(),envId);
        if (envPo.getCode().equals(profiles)){
            List<RouteDefinition> routes = gatewayProperties.getRoutes();
            List<String> ids = new ArrayList<>();
            routes.forEach(route -> {
                ids.add(route.getId());
            });
            List<GatewayRoutePo> routeList = null;
            if (CollUtil.isNotEmpty(ids)){
                routeList = this.list(new QueryWrapper<GatewayRoutePo>()
                        .in("name", ids)
                        .and(wrapper -> wrapper.eq("env_id",envId))
                        .orderByAsc("sort"));
            }

            Page<GatewayRoutePo> page = new Page<>();
            if (CollUtil.isNotEmpty(routeList)){
                for (RouteDefinition route : routes) {
                    boolean exist = routeList.stream().anyMatch(r -> r.getName().equals(route.getId()));
                    if (!exist){
                        GatewayRoutePo gatewayRoutePo = new GatewayRoutePo();
                        gatewayRoutePo.setId(route.getId());
                        gatewayRoutePo.setName(route.getId());
                        gatewayRoutePo.setEnvId(envId);
                        gatewayRoutePo.setPredicates(JSONUtil.parseArray(route.getPredicates()).toString());
                        gatewayRoutePo.setFilters(JSONUtil.parseArray(route.getFilters()).toString());
                        gatewayRoutePo.setUri(JSONUtil.parseObj(route.getUri()).toString());
                        gatewayRoutePo.setOrder(route.getOrder());
                        gatewayRoutePo.setMetadata(JSONUtil.parseObj(route.getMetadata()).toString());
                        gatewayRoutePo.setDatastatusid(1);
                        routeList.add(gatewayRoutePo);
                    }
                }
                limit = currentPage <= 0 ? routeList.size() : limit;
                currentPage = currentPage <= 0 ? 1 : currentPage;
                int total = routeList.size();
                routeList = routeList.stream().filter(r -> {
                            if (StrUtil.isNotBlank(name)) {
                                return r.getName().contains(name);
                            } else {
                                return true;
                            }
                        }).skip((currentPage - 1) * limit).limit(limit).
                        collect(Collectors.toList());
                page.setRecords(routeList);
                page.setTotal(total);
                page.setSize(limit);
                page.setCurrent(currentPage);
            }
            return new PageUtil<GatewayRoutePo>(page);
        }
        else {
            return null;
        }
    }

    /**
     * <p>启动时，加载yaml的配置到数据库，通过服务名匹配，<span style='color: orange;'>如果数据库已存在的配置，以数据库为准</span></p>
     * <p>1、修改之前活跃的环境为0</p>
     * <p>2、查询当前环境，存在则将其置为1，不存在则新增并置为1</p>
     * <p>3、读取当前环境数据库的路由配置</p>
     *  <p>3.1、loadFrom==app时，将内存中的数据初始化到数据库，已存在的不操作，未存在的保存</p>
     * <p>4、将数据库的配置加载到内存。当loadFrom==app时，取数据库的配置与内存中的配置的并集，加载到内存</p>
     * <p>5、如果执行过程中发生异常，回滚到执行之前</p>
     */
    @Override
    public Boolean routeRegister(String loadFrom){
        // 记录修改之前的路由
        List<RouteDefinition> originalRoutes = gatewayProperties.getRoutes();
        try {
            List<RouteDefinition> routes = new ArrayList<>();
            // 修改活跃的环境
            List<GatewayEnvPo> actives = baseGatewayEnvDao.selectList(new QueryWrapper<GatewayEnvPo>().eq("active", 1));
            actives.forEach(active -> {
                active.setActive(0);
                baseGatewayEnvDao.updateById(active);
            });

            // 查询当前环境，不存在时，创建环境
            List<GatewayEnvPo> envPoList = baseGatewayEnvDao.selectList(new QueryWrapper<GatewayEnvPo>().eq("code", profiles));
            GatewayEnvPo gatewayEnvPo = null;
            Date currentDate = new Date();
            String userId = WebUtils.getUserId();
            if (CollUtil.isEmpty(envPoList)){
                gatewayEnvPo = this.insertEnv(currentDate,userId);
                envPoList.add(gatewayEnvPo);
            }
            else {
                envPoList.forEach(env -> {
                    env.setActive(1);
                    baseGatewayEnvDao.updateById(env);
                });
            }
            // 读取数据库的路由
            log.info("刷新路由 当前活跃环境 [{}]",envPoList.get(0).getId());
            List<GatewayRoutePo> gatewayConfigList = this.list(new QueryWrapper<GatewayRoutePo>()
                    .eq("env_id",envPoList.get(0).getId())
                    .eq("datastatusid",1));
            List<GatewayRoutePo> needSaveList = new ArrayList<>();

            // 初始化yaml配置到数据库，已存在的则不处理
            if (StrUtil.isNotBlank(loadFrom) && loadFrom.equals("app")){
                // 启动程序，加载yml
                routes = gatewayProperties.getRoutes();
                if (CollUtil.isNotEmpty(gatewayConfigList)){
                    // 找出需要保存到库的
                    this.findNeedSaveData(routes,needSaveList,envPoList,currentDate,userId,gatewayConfigList);

                    // 找出需要加载到内存的
                    Iterator<RouteDefinition> iterator = routes.iterator();
                    while (iterator.hasNext()){
                        RouteDefinition routeDefinition = iterator.next();
                        boolean exists = gatewayConfigList.stream().anyMatch(gatewayConfigPo -> gatewayConfigPo.getName().equals(routeDefinition.getId()));
                        if (exists){
                            iterator.remove();
                        }
                    }
                }
                else {
                    // 找出需要保存到库的
                    this.findNeedSaveData(routes,needSaveList,envPoList,currentDate,userId,gatewayConfigList);
                }
            }

            // 封装数据库的数据
            if (CollUtil.isNotEmpty(gatewayConfigList)){
                for (GatewayRoutePo gatewayConfig : gatewayConfigList) {
                    RouteDefinition routeDefinition = new RouteDefinition();
                    routeDefinition.setId(gatewayConfig.getName());

                    try {
                        routeDefinition.setUri(new URI(gatewayConfig.getUri()));
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                        throw new BadRequestException(e.getMessage());
                    }

                    if (StrUtil.isNotBlank(gatewayConfig.getFilters())){
                        List<FilterTO> filterTOS = null;
                        try {
                            filterTOS = JSONUtil.parseArray(gatewayConfig.getFilters()).stream().map(filter -> {
                                JSONObject filterObj = (JSONObject) filter;
                                return JSONUtil.toBean(filterObj, FilterTO.class);
                            }).collect(Collectors.toList());
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new BadRequestException("JSON 格式化错误");
                        }

                        List<FilterDefinition> filterDefinitions = new ArrayList<>();
                        for (FilterTO filterTO : filterTOS) {
                            FilterDefinition filterDefinition = new FilterDefinition();
                            filterDefinition.setName(filterTO.getName());
                            filterDefinition.setArgs(filterTO.getArgs());
                            filterDefinitions.add(filterDefinition);
                            routeDefinition.setFilters(filterDefinitions);
                        }
                    }

                    if (StrUtil.isNotBlank(gatewayConfig.getPredicates())){
                        List<PredicatesTO> predicatesTOS = null;
                        try {
                            predicatesTOS = JSONUtil.parseArray(gatewayConfig.getPredicates()).stream().map(predicate -> {
                                JSONObject predicateObj = (JSONObject) predicate;
                                return JSONUtil.toBean(predicateObj, PredicatesTO.class);
                            }).collect(Collectors.toList());
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new BadRequestException("JSON 格式化错误");
                        }

                        List<PredicateDefinition> predicates = new ArrayList<>();
                        for (PredicatesTO predicatesTO : predicatesTOS) {
                            PredicateDefinition predicateDefinition = new PredicateDefinition();
                            predicateDefinition.setName(predicatesTO.getName());
                            predicateDefinition.setArgs(predicatesTO.getArgs());
                            predicates.add(predicateDefinition);
                            routeDefinition.setPredicates(predicates);
                        }
                    }
                    else {
                        throw new BadRequestException("断言不为空");
                    }

                    if (null != gatewayConfig.getOrder()){
                        routeDefinition.setOrder(gatewayConfig.getOrder());
                    }

                    try {
                        if (StrUtil.isNotBlank(gatewayConfig.getMetadata())){
                            routeDefinition.setMetadata(JSONUtil.toBean(gatewayConfig.getMetadata(),Map.class));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new BadRequestException("JSON 格式化错误");
                    }
                    routes.add(routeDefinition);
                }
            }

            // 设置路由
            gatewayProperties.setRoutes(routes);
            routesRefresher.refreshRoutes();

            // 保存路由到数据库
            if (CollUtil.isNotEmpty(needSaveList)){
                this.saveBatch(needSaveList);
            }
        } catch (BadRequestException e) {
            e.printStackTrace();
            log.error("刷新路由 发生异常 [{}]",e.getMessage());
            gatewayProperties.setRoutes(originalRoutes);
            routesRefresher.refreshRoutes();
            throw new BadRequestException(e.getMessage());
        }
        return true;
    }

    private void findNeedSaveData(List<RouteDefinition> routes, List<GatewayRoutePo> needSaveList,
                                  List<GatewayEnvPo> envPoList, Date currentDate, String userId,
                                  List<GatewayRoutePo> gatewayConfigList) {
        for (RouteDefinition route : routes) {
            boolean exists = gatewayConfigList.stream().anyMatch(gatewayConfigPo -> gatewayConfigPo.getName().equals(route.getId()));
            if (!exists){
                GatewayRoutePo gatewayConfigPo = new GatewayRoutePo();
                JSONObject jsonObject = JSONUtil.parseObj(route);
                BeanUtil.copyProperties(jsonObject,gatewayConfigPo);
                gatewayConfigPo.setId(null);
                gatewayConfigPo.setName(route.getId());
                gatewayConfigPo.setOrder(route.getOrder());
                gatewayConfigPo.setEnvId(envPoList.get(0).getId());
                gatewayConfigPo.setDatastatusid(1);
                gatewayConfigPo.setCreateTime(currentDate);
                gatewayConfigPo.setCreateBy(userId);
                gatewayConfigPo.setUpdateTime(currentDate);
                gatewayConfigPo.setUpdateBy(userId);
                needSaveList.add(gatewayConfigPo);
            }
        }
    }

    private GatewayEnvPo insertEnv(Date currentDate, String userId) {
        GatewayEnvPo gatewayEnvPo = new GatewayEnvPo();
        gatewayEnvPo.setCode(profiles);
        gatewayEnvPo.setName(profiles);
        gatewayEnvPo.setActive(1);
        gatewayEnvPo.setCreateTime(currentDate);
        gatewayEnvPo.setCreateBy(userId);
        gatewayEnvPo.setUpdateBy(userId);
        gatewayEnvPo.setUpdateTime(currentDate);
        baseGatewayEnvDao.insert(gatewayEnvPo);
        return gatewayEnvPo;
    }
}
