package com.gyj.Utils;

import com.gyj.base.GirlResult;
import com.gyj.enums.ResultEnum;

/**
 * Created by Gao on 2017/12/16.
 */
public class ResultUtils {

    public static GirlResult success(Object object) {
        GirlResult result = new GirlResult();
        result.setCode(0);
        result.setMsg("success");
        result.setData(object);

        return result;
    }

    public static GirlResult success() {
        return success(null);
    }

    public static GirlResult error(Integer code, String msg) {
        GirlResult result = new GirlResult();
        result.setCode(code);
        result.setMsg(msg);

        return result;
    }

    public static GirlResult error(ResultEnum resultEnum) {
        GirlResult result = new GirlResult();
        result.setCode(resultEnum.getCode());
        result.setMsg(resultEnum.getMsg());

        return result;
    }

}
