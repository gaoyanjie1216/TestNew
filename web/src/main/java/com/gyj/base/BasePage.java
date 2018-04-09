package com.gyj.base;

/**
 * Created by Gao on 2017/11/23.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BasePage<T> implements Serializable {

    /**
     * 分页页码,默认页码为1
     */
    protected int pageNo = 1;

    /**
     * 分页每页数量,默认10条
     */
    protected int pageSize = 10;

    /**
     * 排序列名称,默认为id
     */
    protected String sidx = "id";

    /**
     * 排序正序
     */
    protected String sord = "asc";

    // -- 返回结果 --//
    /**
     * 结果集
     */
    private List<T> result = new ArrayList<T>();

    /**
     * 记录总数
     */
    private long totalCount = 0;

    // -- 构造函数 --//
    public BasePage() {
    }

    public BasePage(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    /**
     * 获得当前页的页号,序号从1开始,默认为1.
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * 设置当前页的页号,序号从1开始,低于1时自动调整为1.
     */
    public void setPageNo(final int pageNo) {
        this.pageNo = pageNo;

        if (pageNo < 1) {
            this.pageNo = 1;
        }
    }

    /**
     * 获得每页的记录数量
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页的记录数量.
     */
    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 获得页内的记录列表.
     */
    public List<T> getResult() {
        return result;
    }

    /**
     * 设置页内的记录列表.
     */
    //这里有一个冲突，两个set方法
    /*public void setResult(final List<T> result) {
        this.result = result;
    }*/

    public void setResult(org.springframework.data.domain.Page<T> page) {
        this.result = page.getContent();
        this.totalCount = page.getTotalElements();
    }


    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }
}