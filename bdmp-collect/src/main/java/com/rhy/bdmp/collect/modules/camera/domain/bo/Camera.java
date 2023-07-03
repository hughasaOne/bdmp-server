package com.rhy.bdmp.collect.modules.camera.domain.bo;

import lombok.Data;

import java.util.List;

/**
 * @author jiangzhimin
 * @version V1.0
 * @description 摄像机信息
 * @date 2021-11-30 16:25
 **/
@Data
public class Camera {

    private String id;//节点id
    private String name;//节点名
    private String parentId;//节点层级
    private int type;//资源类型 10表示目录，20表示资源
    private int carsum;//资源数
    private List<Camera> carmTreeTwos;//孩子节点数据
    private int status;//int整形，状态 10表示在线，20表示离线
    private int hasPtz; //0表示无云台，1表示有云台
    private double coordX; //经度
    private double coordY; //纬度

}