package com.gyj.common.enums;

/**
 * Created by Gao on 2017/12/16.
 */
public enum ResultEnum {

    UNKNOW_ERROR(-1, "未知错误"),
    SUCCESS(0, "success"),
    PRIMARY_SCHOOL(100, "上小学"),
    MIDDLE_SCHOOL(101, "上初中"),
    ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
