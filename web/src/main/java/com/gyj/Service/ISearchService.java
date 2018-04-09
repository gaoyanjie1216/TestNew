package com.gyj.Service;

import com.gyj.entity.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by Gao on 2017/12/14.
 */
public interface ISearchService {

    /**
     * 获取用户搜索记录列表
     *
     * @param token
     * @param pageable
     * @return
     */
    Page<String> getPageSearch(String token, Pageable pageable);
}
