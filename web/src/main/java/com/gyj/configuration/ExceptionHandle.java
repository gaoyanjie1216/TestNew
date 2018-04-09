package com.gyj.configuration;

import com.gyj.Utils.ResultUtils;
import com.gyj.base.GirlResult;
import com.gyj.enums.ResultEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Gao on 2017/12/16.
 */
@ControllerAdvice
public class ExceptionHandle {

    public static final Log log = LogFactory.getLog(ExceptionHandle.class);

    //@ResponseBody不能少，返回对象，json格式
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public GirlResult handle(Exception e) {

        //instanceof 运算符是用来在运行时指出这个对象是否是这个特定类或者是它的子类的一个实例,返回一个布尔值
        if (e instanceof GirlException) {
            GirlException girlException = (GirlException) e;
            return ResultUtils.error(girlException.getCode(), girlException.getMessage());
        } else {
            log.info("[系统异常] {}", e);
            return ResultUtils.error(ResultEnum.UNKNOW_ERROR);
        }
    }
}
