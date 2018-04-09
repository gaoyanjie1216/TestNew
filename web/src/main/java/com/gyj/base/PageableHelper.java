package com.gyj.base;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Created by Gao on 2017/11/23.
 */
public class PageableHelper {

    public static Pageable instance(Integer page, Integer size) {
        page = page - 1;
        return new PageRequest(page, size);
    }

    public static Pageable instance(String page, Integer size) {
        return instance(Integer.parseInt(page), size);
    }

    public static Pageable instance(BasePage page) {
        return instance(page.getPageNo(), page.getPageSize());
    }

    public static Pageable instance(Result result) {
        return instance(result.getPage());
    }

    public static Pageable instance(Result result, Sort.Order... orders) {
        Pageable pageable = instance(result);
        if (null != pageable.getSort()) {
            pageable.getSort().and(new Sort(orders));
        } else {
            pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(orders));
        }
        return pageable;
    }
}
