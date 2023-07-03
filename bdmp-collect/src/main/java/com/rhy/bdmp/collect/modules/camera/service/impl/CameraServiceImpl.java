package com.rhy.bdmp.collect.modules.camera.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.db.Db;
import cn.hutool.db.DbUtil;
import cn.hutool.db.Entity;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.rhy.bcp.common.datasource.DynamicRoutingDataSource;
import com.rhy.bcp.common.exception.BadRequestException;
import com.rhy.bcp.common.util.WebUtils;
import com.rhy.bdmp.collect.modules.camera.config.VideoProperties;
import com.rhy.bdmp.collect.modules.camera.domain.bo.Camera;
import com.rhy.bdmp.collect.modules.camera.domain.po.Sy;
import com.rhy.bdmp.collect.modules.camera.domain.vo.ApiCamerResponse;
import com.rhy.bdmp.collect.modules.camera.domain.vo.CameraResponse;
import com.rhy.bdmp.collect.modules.camera.domain.vo.Login;
import com.rhy.bdmp.collect.modules.camera.domain.vo.Tree;
import com.rhy.bdmp.collect.modules.camera.service.IBaseSyService;
import com.rhy.bdmp.collect.modules.camera.service.ICameraService;
import com.rhy.bdmp.collect.modules.camera.util.CallApiUtils;
import com.rhy.bdmp.collect.modules.camera.util.SignUtils;
import com.rhy.bdmp.collect.modules.mq.producer.KafkaProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @description 采集视频资源数据
 * @author jiangzhimin
 * @date 2021-08-02 17:19
 */
@Service
@Slf4j
public class CameraServiceImpl implements ICameraService {

    private final static String TOPIC_CAMERA_STATUS_YT = "camera_status_yt";

    @Resource
    private DynamicRoutingDataSource dynamicRoutingDataSource;

    @Resource(name = "callApiUtils")
    private CallApiUtils callApiUtils;

    @Resource
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private VideoProperties videoProperties;

    @Autowired
    private IBaseSyService baseSyService;


    /**
     * 同步视频资源(云台)
     * <p>recursiveEntity里的tableName需要指定</p>
     */
    @Override
    public boolean syncCameraByYt() {
        boolean result = true;
        try {
            Db defaultDB = DbUtil.use((DataSource) dynamicRoutingDataSource.getDataSourceMap().get("default"));
            List<Camera> cameraTree = this.cameraTree(true, "");
            String currentDate = DateUtil.now();
            // 1、tree转list
            List<Entity> entityList = new ArrayList<>();
            String tableName = "t_bdmp_assets_camera_resource_yt";
            recursiveEntity(entityList, cameraTree, currentDate,tableName);
            // 2、先查询有哪些资源
            List<Entity> dbList = defaultDB.findAll("t_bdmp_assets_camera_resource_yt");
            // 3、再更新或新增
            for (int i = 0 ; i < entityList.size(); i++){
                Entity entity = entityList.get(i);
                // 判断数据库是否已存在
                Boolean isExist = false;
                for (Entity dbEntity : dbList){
                    if (entity.getStr("id").equals(dbEntity.getStr("id"))){
                        isExist = true;
                        break;
                    }
                }
                // 数据已存在，修改
                if (isExist){
                    defaultDB.update(Entity.create().set("update_time",currentDate).set("parent_id",entity.getStr("parent_id")).set("type",entity.getStr("type")).set("car_sum",entity.getStr("car_sum")).set("status",entity.getStr("status")).set("has_ptz",entity.getStr("has_ptz")),
                            Entity.create("t_bdmp_assets_camera_resource_yt").set("id",entity.getStr("id")));
//                    defaultDB.update(entity, Entity.create("t_bdmp_assets_camera_resource_tl").set("camera_id", jsonObject.getStr("cameraId")));
                }
                // 数据不存在，新增
                else {
                    entity.set("create_time", currentDate);
                    defaultDB.insert(entity);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        }
        return result;
    }

    /**
     * 同步视频资源(腾路)
     * @return
     */
    @Override
    public boolean syncCameraByTl() {
        boolean result = true;
        try {
            Db defaultDB = DbUtil.use((DataSource) dynamicRoutingDataSource.getDataSourceMap().get("default"));
            CameraResponse cameraResponse = this.tlVideo("99999", "1", "");
            List<com.rhy.bdmp.collect.modules.camera.domain.vo.Camera> tlCameraList = cameraResponse.getData();
            String currentDate = DateUtil.now();
            // 1、先查询有哪些资源
            List<Entity> dbList = defaultDB.findAll("t_bdmp_assets_camera_resource_tl");
            // 2、再更新或新增
            for (int i = 0 ; i < tlCameraList.size(); i++){
                com.rhy.bdmp.collect.modules.camera.domain.vo.Camera tlCamera = tlCameraList.get(i);
                Entity entity = Entity.create().setTableName("t_bdmp_assets_camera_resource_tl")
                        .set("camera_id", tlCamera.getCameraId())
                        .set("camera_name", tlCamera.getCameraName())
                        .set("device_type", tlCamera.getDeviceType())
                        .set("camera_ip", tlCamera.getCameraIp())
                        .set("longitude", tlCamera.getLongitude())
                        .set("latitude", tlCamera.getLatitude())
                        .set("online_status", tlCamera.getOnlineStatus())
                        .set("uuid", tlCamera.getUuid())
                        .set("datastatusid", "1")
                        .set("update_time", currentDate);
                // 判断数据库是否已存在
                boolean isExist = false;
                for (Entity dbEntity : dbList){
                    String tlCameraId = String.valueOf(tlCamera.getCameraId());
                    if (tlCameraId.equals(dbEntity.getStr("camera_id"))){
                        isExist = true;
                        break;
                    }
                }
                // 数据已存在，修改
                if (isExist){
//                    defaultDB.update(entity, Entity.create("t_bdmp_assets_camera_resource_tl").set("camera_id", jsonObject.getStr("cameraId")));
                }
                // 数据不存在，新增
                else {
                    entity.set("create_time", currentDate);
                    defaultDB.insert(entity);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new BadRequestException(e.getMessage());
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = BadRequestException.class)
    public boolean syncCameraBySY() {
        // syVideoList=[{cameraName,status}],第三方
        JSONArray syVideoList = this.getSyVideoList();
        // 查询数据库，本库
        List<Sy> syList = baseSyService.list();
        List<Sy> needSaveData = new ArrayList<>();

        // 公共字段
        Date currentDate = new Date();
        String currentUser = WebUtils.getUserId();

        if (null == syVideoList){
            return false;
        }

        // 更新
        for (Sy sy : syList) {
            boolean exists = syVideoList.stream().anyMatch((obj) -> {
                String cameraName = JSONUtil.parseObj(obj).get("cameraName").toString();
                return sy.getName().equals(cameraName);
            });
            if (exists){
                sy.setStatus(10);
                sy.setUpdateBy(currentUser);
                sy.setUpdateTime(currentDate);
            }
        }
        // 新增
        for (Object obj : syVideoList) {
            JSONObject jsonObject = JSONUtil.parseObj(obj);
            String cameraName = jsonObject.get("cameraName").toString();
            boolean exists = syList.stream().anyMatch(sy -> sy.getName().equals(cameraName));
            if (!exists){
                Sy sy = new Sy();
                sy.setName(cameraName);
                sy.setId(cameraName);// todo 数据待定
                Object status = jsonObject.get("status");
                if (null != status){
                    sy.setStatus(Integer.parseInt(status.toString()));
                }
                sy.setCreateBy(currentUser);
                sy.setCreateTime(currentDate);
                sy.setUpdateTime(currentDate);
                sy.setUpdateBy(currentUser);
                needSaveData.add(sy);
            }
        }
        boolean updateBatchById = baseSyService.updateBatchById(syList);
        boolean saveBatch = baseSyService.saveBatch(needSaveData);
        return updateBatchById && saveBatch;
    }

    /**
     * 获取yt视频树
     * @param isAll 是否查询全部
     * @param resId 节点id
     * @author weicaifu
     */
    @Override
    public List<Camera> cameraTree(boolean isAll, String resId) {
        //1.登陆
        Login login = callApiUtils.login();
        //2.获取token
        String token = login.getRows().getToken().getTokenType() + " " + login.getRows().getToken().getAccessToken();
        if (isAll) {
            List<Camera> cameraList = new ArrayList<>();
            recursiveCamera(cameraList, "", token);
            return cameraList;
        } else {
            //获取指定层级的树
            Tree treeRow = callApiUtils.tree(resId, "", 1, 1000, 1, token);
            int length = treeRow.getData().getRows().size();
            List<Camera> cameraList = new ArrayList<>();
            boolean isExistYYJT = false;
            if (length > 0) {
                for (int index = 0; index < length; index++) {
                    Camera camera = new Camera();
                    camera.setId(treeRow.getData().getRows().get(index).getId());
                    camera.setName(treeRow.getData().getRows().get(index).getName());
                    camera.setParentId(treeRow.getData().getRows().get(index).getParentId());
                    camera.setType(treeRow.getData().getRows().get(index).getType());
                    camera.setStatus(treeRow.getData().getRows().get(index).getStatus());
                    camera.setHasPtz(treeRow.getData().getRows().get(index).getHasPtz());
                    camera.setCoordX(treeRow.getData().getRows().get(index).getCoordX());
                    camera.setCoordY(treeRow.getData().getRows().get(index).getCoordY());
                    if (-1 != camera.getName().indexOf("交投运营集团")) {
                        isExistYYJT = true;
                    }
                    cameraList.add(camera);
                }
            }
            if (isExistYYJT) {
                List<Camera> cameraYYJTList = new ArrayList<>();
                for (Camera camera : cameraList) {
                    if (-1 != camera.getName().indexOf("交投运营集团")) {
                        cameraYYJTList.add(camera);
                        Pattern pattern = Pattern.compile("^\\d+."); //去掉空格符合换行符 
                        Matcher matcher = pattern.matcher(camera.getName());
                        String resultName = matcher.replaceAll("");
                        camera.setName(resultName);
                        break;
                    }
                }
                return cameraYYJTList;
            } else {
                return cameraList;
            }
        }
    }

    /**
     * 获取tl视频列表
     * @param pageSize 页大小
     * @param page 当前页
     * @param type 类型
     * @author weicaifu
     */
    @Override
    public CameraResponse tlVideo(String pageSize, String page, String type) {
        String resource = callApiUtils.tlVideoResource(pageSize, page, type);
        ApiCamerResponse apiCamerResponse = JSONUtil.toBean(resource, ApiCamerResponse.class);

        CameraResponse cameraResponse = new CameraResponse();
        try {
            cameraResponse.setCurrentPage(apiCamerResponse.getPagination().getCurrentPage());
            cameraResponse.setTotalRows(apiCamerResponse.getPagination().getTotalRows());
            cameraResponse.setData(apiCamerResponse.getData());
            cameraResponse.setListRows(apiCamerResponse.getPagination().getListRows());
        } catch (Exception e) {
            throw new BadRequestException("请求出现未知异常");
        }
        return cameraResponse;
    }

    private void recursiveCamera(List<Camera> cameraList, String resId, String token) {
        Tree treeRow = callApiUtils.tree(resId, "", 1, 1000, 1, token);
        int length = treeRow.getData().getRows().size();
        if (length > 0) {
            for (int index = 0; index < length; index++) {
                Camera camera = new Camera();
                camera.setId(treeRow.getData().getRows().get(index).getId());
                camera.setName(treeRow.getData().getRows().get(index).getName());
                camera.setParentId(treeRow.getData().getRows().get(index).getParentId());
                camera.setType(treeRow.getData().getRows().get(index).getType());
                camera.setStatus(treeRow.getData().getRows().get(index).getStatus());
                camera.setHasPtz(treeRow.getData().getRows().get(index).getHasPtz());
                camera.setCoordX(treeRow.getData().getRows().get(index).getCoordX());
                camera.setCoordY(treeRow.getData().getRows().get(index).getCoordY());
                camera.setCarsum(0);
                cameraList.add(camera);
                if (20 == camera.getType()){
                    // 发送摄像机状态消息
                    sendCameraStatus(camera);
                }else {
                    List<Camera> nextCameraList = new ArrayList<>();
                    camera.setCarmTreeTwos(nextCameraList);
                    recursiveCamera(nextCameraList, camera.getId(), token);
                }
            }
        }
    }

    /**
     * 发送云台摄像机状态消息
     * @param camera
     */
    private void sendCameraStatus(Camera camera){
        if (20 == camera.getType()) {
            try{
                kafkaProducerService.sendMessage(TOPIC_CAMERA_STATUS_YT, JSONUtil.createObj().set("cameraId", camera.getId()).set("onlineStatus", 10 == camera.getStatus() ? 1 : 0).toString());
            }catch (Exception e){
                log.error("云台摄像机状态消息发送失败:{}", e.getMessage());
            }
            try{
                Db defaultDB = DbUtil.use((DataSource) dynamicRoutingDataSource.getDataSourceMap().get("default"));
                defaultDB.execute("update t_bdmp_assets_camera_resource_yt set status=? where id=?", new Object[]{camera.getStatus(), camera.getId()});
            }catch (Exception e){
                log.error("云台摄像机状态数据库更新失败:{}", e.getMessage());
            }
        }
    }

    // 递归获取云台视频资源
    private void recursiveEntity(List<Entity> entityList, List<Camera> cameraTree, String currentDate,String tableName){
        for (int i = 0 ; i < cameraTree.size(); i++) {
            Camera camera = cameraTree.get(i);
            Entity entity = Entity.create().setTableName(tableName);
            entity.set("id", camera.getId())
                    .set("name", camera.getName())
                    .set("parent_id", camera.getParentId())
                    .set("type", camera.getType())
                    .set("car_sum", camera.getCarsum())
                    .set("status", camera.getStatus())
                    .set("has_ptz", camera.getHasPtz())
                    .set("coord_x", camera.getCoordX())
                    .set("coord_y", camera.getCoordY())
                    .set("datastatusid", "1")
                    .set("create_time", currentDate)
                    .set("update_time", currentDate);
            List<Camera> carmTreeTwos = camera.getCarmTreeTwos();
            if (null != carmTreeTwos) {
                if (0 < carmTreeTwos.size()) {
                    recursiveEntity(entityList, carmTreeTwos, currentDate,tableName);
                }
            }
            entityList.add(entity);
        }
    }

    private JSONArray getSyVideoList(){
        // 获取请求参数
        Map<String, String> sy = videoProperties.getSy();
        // 1、账号id
        String syAccountId = sy.get("accountId");
        // 2、key
        String syKey = sy.get("key");
        // 3、address
        String syAddress = "";
        syAddress = sy.get("address");
        // 4、时间戳
        String timestamp = (new Timestamp(new Date().getTime()).getTime() / 1000)+"";
        // 5、随机字符串
        String nonceString = SignUtils.getRandomString(32);
        // 封装参数
        Map<String, Object> params = new HashMap<>();
        params.put("id",syAccountId);
        params.put("pageIndex",-1);
        params.put("pageCount",-1);
        params.put("timeStamp",timestamp);
        params.put("nonceString",nonceString);
        // 生成签名
        String sign = SignUtils.generateSignatureSY(params, syKey);
        params.put("sign",sign);

        // 调用接口
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity request = new HttpEntity<>(params, headers);
        String url = syAddress+"/api/video/getCameraList";
        ResponseEntity<String> res = restTemplate.postForEntity(url, request, String.class);
        // body{data,status,message}
        String data = JSONUtil.parseObj(res.getBody()).get("data").toString();
        if (JSONUtil.isJson(data)){
            return JSONUtil.parseArray(JSONUtil.parseObj(res.getBody()).get("data"));
        }
        else {
            return null;
        }
    }

}
