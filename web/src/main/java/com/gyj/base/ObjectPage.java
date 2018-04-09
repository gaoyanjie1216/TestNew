package com.gyj.base;

/**
 * Created by Gao on 2017/12/25.
 */

import java.util.List;
import java.util.Map;

public class ObjectPage {

    // 每页记录数
    private int eachPageRecordNum;

    // 总记录数
    private int totalRecordNum;

    // 总页数
    private int totalPageNum;

    // 当前页码
    private int currentPageNo;

    // 当前页开始编号
    private int currentPageStartNo;

    // 当前页结束编号
    private int currentPageEndNo;

    // 检索条件
    private Map<String, String> conditions;

    // 每页显示的记录数
    private List<? extends Object> list;


    public int getCurrentPageEndNo() {
        return currentPageEndNo;
    }

    public void setCurrentPageEndNo(int currentPageEndNo) {
        this.currentPageEndNo = currentPageEndNo;
    }

    public int getCurrentPageStartNo() {
        return currentPageStartNo;
    }

    public void setCurrentPageStartNo(int currentPageStartNo) {
        this.currentPageStartNo = currentPageStartNo;
    }

    public int getEachPageRecordNum() {
        return eachPageRecordNum;
    }

    public void setEachPageRecordNum(int eachPageRecordNum) {
        this.eachPageRecordNum = eachPageRecordNum;
    }

    public int getTotalRecordNum() {
        return totalRecordNum;
    }

    public void setTotalRecordNum(int totalRecordNum) {
        this.totalRecordNum = totalRecordNum;
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public int getCurrentPageNo() {
        return currentPageNo;
    }

    public void setCurrentPageNo(int currentPageNo) {
        this.currentPageNo = currentPageNo;
    }

    public List<? extends Object> getList() {
        return list;
    }

    public void setList(List<? extends Object> list) {
        this.list = list;
    }

    public Map<String, String> getConditions() {
        return conditions;
    }

    public void setConditions(Map<String, String> conditions) {
        this.conditions = conditions;
    }

    @Override
    public String toString() {
        return "Page{" +
                "eachPageRecordNum=" + eachPageRecordNum +
                ", totalRecordNum=" + totalRecordNum +
                ", totalPageNum=" + totalPageNum +
                ", currentPageNo=" + currentPageNo +
                ", currentPageStartNo=" + currentPageStartNo +
                ", currentPageEndNo=" + currentPageEndNo +
                ", conditions=" + conditions +
                ", list=" + list +
                '}';
    }
}
