package com.gyj.base;

import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Gao on 2017/11/23.
 */

public class Result<T> implements Serializable {
    /**
     * 是否成功
     */
    private Boolean success = true;
    /**
     * 消息编码
     */
    private String code = null;
    /**
     * 消息内容
     */
    private String message = null;
    /**
     * 错误信息
     */
    private String errorMessage = null;
    /**
     * 列表数据
     */
    private List<T> list = null;
    /**
     * 列表数据
     */
    private Object data = null;
    /**
     * 翻页数据
     */
    private BasePage<T> page = null;

    // -- 构造函数 --//
    public Result() {
    }

    public Result(int pageNo, int pageSize) {
        this.page = new BasePage<>(pageNo, pageSize);
    }


    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public BasePage<T> getPage() {
        return page;
    }

    public void setPage(BasePage<T> page) {
        this.page = page;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}