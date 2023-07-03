package com.rhy.bdmp.system.modules.logging.enums;

public enum LogTypeEnum {
    ACCESS("access","访问"),
    OPERATE("operate","操作"),
    GET_RESOURCE("getResource","获取资源"),
    CRON("cron","定时任务"),
    STATS("stats","统计"),
    TIPS("tips","提示"),
    OTHER("other","其他");

    private String code;
    private String name;

    LogTypeEnum(String code,String name){
        this.code = code;
        this.name = name;
    }

    public String getCode(){
        return this.code;
    }

    public String getName(){
        return this.name;
    }
}
