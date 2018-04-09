package com.gyj.Mail.enums;

/**
 * Created by Gao on 2017/12/13.
 */

/**
 * 码云：http://git.oschina.net/jnyqy
 */
public enum MailContentTypeEnum {

    HTML("text/html; charset=UTF-8"), //html格式
    TEXT("text");
    private String value;

    MailContentTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}