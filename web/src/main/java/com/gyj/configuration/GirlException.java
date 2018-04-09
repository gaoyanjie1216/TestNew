package com.gyj.configuration;

import com.gyj.enums.ResultEnum;

/**
 * Created by Gao on 2017/12/16.
 */

//spring boot 对抛出的异常是RuntimeException才会进行事务回滚，对抛出Exception不会进行事务回滚
public class GirlException extends RuntimeException {

    private Integer code;

    //构造函数
    public GirlException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    //在一个类中可以定义多个构造函数,以进行不同的初始化,构造函数没有返回值类型
    public GirlException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
