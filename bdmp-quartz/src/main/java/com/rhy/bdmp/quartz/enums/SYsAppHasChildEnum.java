package com.rhy.bdmp.quartz.enums;

import lombok.Data;

/**
 * @author shuaichao
 * @create 2022-03-15 11:49
 */
public enum SYsAppHasChildEnum {
    YES(1,"有子节点"),
    NO(2,"没有子节点");


    private int code;
    private String name;
    SYsAppHasChildEnum(int code, String name){
         this.code = code;
         this.name=name;
    }

    public int getCode(){
        return this.code;
    }
    public String getName(){
        return this.name;
    }



}
