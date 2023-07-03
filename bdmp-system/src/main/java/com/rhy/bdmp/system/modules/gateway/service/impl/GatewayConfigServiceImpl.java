package com.rhy.bdmp.system.modules.gateway.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rhy.bdmp.system.modules.gateway.dao.GatewayConfigDao;
import com.rhy.bdmp.system.modules.gateway.domain.vo.GatewayRouteVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.PageUtil;
import com.rhy.bcp.common.util.Query;
import com.rhy.bcp.common.util.QueryVO;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.system.modules.gateway.domain.vo.JsonVO;
import com.rhy.bdmp.system.modules.gateway.domain.vo.ListVO;
import com.rhy.bdmp.system.modules.gateway.service.GatewayConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author author
 * @version V1.0
 * @description 服务实现
 * @date 2022-05-30 17:13
 **/
@Service
public class GatewayConfigServiceImpl extends ServiceImpl<GatewayConfigDao, GatewayRouteVO> implements GatewayConfigService {

    @Autowired
    private Environment property;

    @Autowired
    private GatewayEnvServiceImpl gatewayEnvService;


    public static final String URI = "/nacos/v1/ns/service/list";

    /**
     * 列表查询
     *
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public List<GatewayRouteVO> list(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            Query<GatewayRouteVO> query = new Query<GatewayRouteVO>(queryVO);
            QueryWrapper<GatewayRouteVO> queryWrapper = query.getQueryWrapper();
            queryWrapper.orderByAsc("sort");
            queryWrapper.orderByDesc("create_time");
            List<GatewayRouteVO> entityList = getBaseMapper().selectList(queryWrapper);
            return entityList;
        }
        //2、无查询条件
        QueryWrapper<GatewayRouteVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        queryWrapper.orderByDesc("create_time");
        List<GatewayRouteVO> gatewayRouteVos = getBaseMapper().selectList(queryWrapper);
        gatewayRouteVos.forEach(this::stringToList);
        return gatewayRouteVos;
    }

    /**
     * 列表查询(分页)
     *
     * @param queryVO 查询条件
     * @return
     */
    @Override
    public PageUtil<GatewayRouteVO> page(QueryVO queryVO) {
        //1、有查询条件
        if (null != queryVO) {
            QueryVO.QueryCondition[] array = queryVO.getQueryConditionArray();
            // 查询当前
            for (int i = 0; i < array.length; i++) {
                if ("env_id".equals(array[i].getColumnName())) {
                    String envId = array[i].getValues().get(0).toString();
                    // 由前端传递参数 current 表示需要查询当前环境
                    if ("current".equals(envId)) {
                        array[i].getValues().set(0, gatewayEnvService.getCurrentEnvId().getId());
                    }
                    break;
                }
            }
            queryVO.setQueryConditionArray(array);
            Query<GatewayRouteVO> query = new Query<GatewayRouteVO>(queryVO);
            Page<GatewayRouteVO> page = query.getPage();
            QueryWrapper<GatewayRouteVO> queryWrapper = query.getQueryWrapper();
            if (null != page) {
                queryWrapper.orderByAsc("sort");
                queryWrapper.orderByDesc("create_time");
                getBaseMapper().selectPage(page, queryWrapper);
                List<GatewayRouteVO> records = page.getRecords();
                records.forEach(this::stringToList);
                page.setRecords(records);
                return new PageUtil<GatewayRouteVO>(page);
            } else {
                throw new BadRequestException("没有分页参数，无法进行分页查询");
            }
        } else {
            throw new BadRequestException("没有分页参数，无法进行分页查询");
        }
    }

    /**
     * 查看(根据ID)
     *
     * @param id
     * @return
     */
    @Override
    public GatewayRouteVO detail(String id) {
        if (!StrUtil.isNotBlank(id)) {
            throw new BadRequestException("not exist id");
        }
        GatewayRouteVO gatewayRouteVO = getBaseMapper().selectById(id);
        this.stringToList(gatewayRouteVO);
        return gatewayRouteVO;
    }

    /**
     * 新增
     *
     * @param gatewayRouteVO
     * @return
     */
    @Override
    public int create(GatewayRouteVO gatewayRouteVO) {
        if (StrUtil.isBlank(gatewayRouteVO.getName())) {
            throw new BadRequestException("requires an GatewayConfig's id");
        }
        listToString(gatewayRouteVO);
        checkGatewayConfigVO(gatewayRouteVO);
        Date currentDateTime = DateUtil.date();
        String currentUser = WebUtils.getUserId();
        gatewayRouteVO.setCreateBy(currentUser);
        gatewayRouteVO.setCreateTime(currentDateTime);
        gatewayRouteVO.setUpdateBy(currentUser);
        gatewayRouteVO.setUpdateTime(currentDateTime);
        int result = getBaseMapper().insert(gatewayRouteVO);
        return result;
    }

    /**
     * 修改
     *
     * @param gatewayRouteVO
     * @return
     */
    @Override
    public int update(GatewayRouteVO gatewayRouteVO) {
        if (StrUtil.isBlank(gatewayRouteVO.getId())) {
            throw new BadRequestException("A new GatewayConfig not exist id");
        }
        listToString(gatewayRouteVO);
        checkGatewayConfigVO(gatewayRouteVO);
        String currentUser = WebUtils.getUserId();
        Date currentDateTime = DateUtil.date();
        gatewayRouteVO.setUpdateBy(currentUser);
        gatewayRouteVO.setUpdateTime(currentDateTime);
        int result = getBaseMapper().updateById(gatewayRouteVO);
        return result;
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @Override
    public int delete(Set<String> ids) {
        int result = getBaseMapper().deleteBatchIds(ids);
        return result;
    }


    /**
     * 获取nacos中的服务列表
     *
     * @return json
     */
    @Override
    public JSONObject getProperty() {
        String serverAddr = property.getProperty("spring.cloud.nacos.discovery.server-addr");
        Map<String, Object> paramMap = new HashMap<>(2);
        paramMap.put("pageNo", 1);
        paramMap.put("pageSize", 50);
        String content = HttpUtil.get(serverAddr + URI, paramMap);
        return new JSONObject(content);
    }

    /**
     * 校验是否已经存在相同的网关配置
     *
     * @param gatewayRouteVO 网关对象
     */
    private void checkGatewayConfigVO(GatewayRouteVO gatewayRouteVO) {
        String predicates = gatewayRouteVO.getPredicates();
        String uri = gatewayRouteVO.getUri();
        QueryWrapper<GatewayRouteVO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GatewayRouteVO::getPredicates, predicates)
                .eq(GatewayRouteVO::getEnvId, gatewayRouteVO.getEnvId());
        List<GatewayRouteVO> list = getBaseMapper().selectList(queryWrapper);
        if (CollUtil.isNotEmpty(list) && list.size() == 1 && list.get(0).getId().equals(gatewayRouteVO.getId())) {
            return;
        }
        if (isValidIP(uri)) {
            list.forEach(routeVo -> {
                if (routeVo.getUri().equals(uri)) {
                    throw new BadRequestException("已有相同网关IP端口一致:" + uri);
                }
            });
        }
    }

    /**
     * 校验字符串是否为IP地址
     *
     * @param b
     * @return
     */
    private boolean isValidIP(String b) {
        //校验“ip:端口号”
        Pattern compile = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+)\\:(\\d+)");
        Matcher m = compile.matcher(b);
        String ip = null;
        String port = null;
        while (m.find()) {
            ip = m.group(1);
            port = m.group(2);
        }
        return StrUtil.isBlank(ip) || StrUtil.isBlank(ip) ? Boolean.FALSE : isboolIp(ip) && isboolPort(port);
    }


    /**
     * 判断是否为合法IP * @return the ip
     */
    private boolean isboolIp(String ipAddress) {
        String ip = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
        Pattern pattern = Pattern.compile(ip);
        Matcher matcher = pattern.matcher(ipAddress);
        return matcher.matches();
    }

    /**
     * 校验合法端口号
     *
     * @param port
     * @return
     */
    private boolean isboolPort(String port) {
        //端口号验证 1 ~ 65535
        String regex = "^([1-9]|[1-9]\\d{1,3}|[1-6][0-5][0-5][0-3][0-5])$";
        return Pattern.matches(regex, port);
    }

    /**
     * 将字符串类型断言过滤转换成集合
     *
     * @param gatewayVo gateway对象
     */
    private void stringToList(GatewayRouteVO gatewayVo) {
        List<ListVO> list = new ArrayList<>();
        String predicates = gatewayVo.getPredicates();
        if (StrUtil.isNotEmpty(predicates) && !StrUtil.equals(predicates, "{}")) {
            JSONArray assertArray = new JSONArray(predicates);
            List<JsonVO> assertList = assertArray.toList(JsonVO.class);
            for (JsonVO vo : assertList) {
                ListVO listVO = new ListVO();
                listVO.setName(vo.getName());
                vo.getArgs().forEach((key, value) -> {
                    listVO.setValue(value);
                });
                list.add(listVO);
            }
            gatewayVo.setAssertList(list);
        } else {
            gatewayVo.setAssertList(new ArrayList<>());
        }
        String filters = gatewayVo.getFilters();
        if (StrUtil.isNotEmpty(filters) && !StrUtil.equals(filters, "{}")) {
            JSONArray filtersArray = new JSONArray(filters);
            List<JsonVO> filterList = filtersArray.toList(JsonVO.class);
            list = new ArrayList<>();
            if (CollUtil.isNotEmpty(filterList)) {
                for (JsonVO vo : filterList) {
                    ListVO listVO = new ListVO();
                    listVO.setName(vo.getName());
                    vo.getArgs().forEach((key, value) -> {
                        listVO.setValue(value);
                    });
                    list.add(listVO);
                }
            } else {
                ListVO listVO = new ListVO();
                list.add(listVO);
            }
            gatewayVo.setFilterList(list);

        } else {
            List<ListVO> list1 = new ArrayList<>();
            list1.add(new ListVO());
            gatewayVo.setFilterList(list1);
        }
        String mateData = gatewayVo.getMetadata();
        if (StrUtil.isNotBlank(mateData) && !StrUtil.equals(mateData, "{}")) {
            JSONArray mateDataArray = new JSONArray(mateData);
            List<JsonVO> mateDataList = mateDataArray.toList(JsonVO.class);
            list = new ArrayList<>();
            if (CollUtil.isNotEmpty(mateDataList)) {
                for (JsonVO vo : mateDataList) {
                    ListVO listVO = new ListVO();
                    listVO.setName(vo.getName());
                    vo.getArgs().forEach((key, value) -> {
                        listVO.setValue(value);
                    });
                    list.add(listVO);
                }
            } else {
                ListVO listVO = new ListVO();
                list.add(listVO);
            }
            gatewayVo.setMetadataList(list);
        } else {
            List<ListVO> list1 = new ArrayList<>();
            list1.add(new ListVO());
            gatewayVo.setMetadataList(list1);
        }
    }


    /**
     * 将集合类型断言过滤转换成字符串
     *
     * @param gatewayVo gateway对象
     */
    private void listToString(GatewayRouteVO gatewayVo) {
        List<ListVO> assertList = gatewayVo.getAssertList();
        if (CollUtil.isNotEmpty(assertList)) {
            List<JsonVO> jsonVOList = new ArrayList<>();
            for (ListVO vo : assertList) {
                JsonVO jsonVO = new JsonVO();
                jsonVO.setName(vo.getName());
                LinkedHashMap<String, String> map = new LinkedHashMap<>();
                map.put(vo.getName(), vo.getValue());
                jsonVO.setArgs(map);
                jsonVOList.add(jsonVO);
            }
            JSONArray array = new JSONArray(jsonVOList);
            gatewayVo.setPredicates(array.toString());
        } else {
            new BadRequestException("断言不能为空");
        }
        List<ListVO> filterList = gatewayVo.getFilterList();
        toFilter(filterList, gatewayVo);
        List<ListVO> mateDataList = gatewayVo.getMetadataList();
        if (CollUtil.isNotEmpty(mateDataList)) {
            List<JsonVO> jsonVOList1 = new ArrayList<>();
            for (ListVO vo : mateDataList) {
                JsonVO jsonVO = new JsonVO();
                if (StrUtil.isBlank(vo.getName())) {
                    gatewayVo.setMetadata("");
                    return;
                }
                jsonVO.setName(vo.getName());
                LinkedHashMap<String, String> map = new LinkedHashMap<>();
                map.put(vo.getName(), vo.getValue());
                jsonVO.setArgs(map);
                jsonVOList1.add(jsonVO);
            }
            JSONArray array1 = new JSONArray(jsonVOList1);
            gatewayVo.setMetadata(array1.toString());
        }
    }

    /**
     * 转换filter
     *
     * @param filterList
     * @param gatewayVo
     */
    private void toFilter(List<ListVO> filterList, GatewayRouteVO gatewayVo) {
        if (CollUtil.isNotEmpty(filterList)) {
            List<JsonVO> jsonVOList1 = new ArrayList<>();
            for (ListVO vo : filterList) {
                String name = vo.getName();
                String value = vo.getValue();
                JsonVO jsonVO = new JsonVO();
                if (StrUtil.isBlank(vo.getName())) {
                    gatewayVo.setFilters("");
                    return;
                }
                if (!(StrUtil.isBlank(name) || StrUtil.isBlank(value))) {
                    jsonVO.setName(name);
                    LinkedHashMap<String, String> map = new LinkedHashMap<>();
                    map.put(name, value);
                    jsonVO.setArgs(map);
                    jsonVOList1.add(jsonVO);
                }
            }
            JSONArray array1 = new JSONArray(jsonVOList1);
            gatewayVo.setFilters(array1.toString());
        }
    }
}
