package com.rhy.bdmp.collect.modules.camera.domain.vo;
import java.util.Date;

@lombok.Data
public class Data {
    private double coordX;
    private double coordY;
    private Date createTime;
    private int hasPtz;
    private String id;
    private int lockId;
    private String name;
    private String parentId;
    private int status;
    private int type;
}