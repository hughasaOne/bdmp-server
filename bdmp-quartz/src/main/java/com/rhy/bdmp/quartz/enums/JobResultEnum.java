package com.rhy.bdmp.quartz.enums;

/**
 * @author shuaichao
 * @create 2022-03-15 11:49
 */
public enum JobResultEnum {
    FAIL("fail","执行失败"),
    SUCCESS("fail","执行成功");


    private String name;
    private String description;
    private JobResultEnum(String name,String description){
         this.name=name;
         this.description=description;
    }

}
