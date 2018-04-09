package com.gyj.Service.Impl;

import com.gyj.Dao.ISearchDao;
import com.gyj.Service.ISearchService;
import com.gyj.Utils.TokenUtil;
import com.gyj.entity.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Gao on 2017/12/14.
 */
@Transactional
@Service
public class SearchServiceImpl implements ISearchService {

    private static Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);

    @Autowired
    private ISearchDao searchDao;

    @Override
    public Page<String> getPageSearch(String token, Pageable p) {
        UserInfo user = this.getLoginUser(token);
        Pageable pageable = new PageRequest(p.getPageNumber(), p.getPageSize(), Sort.Direction.DESC, "sysCreated");
        return searchDao.findConditionsByLoginName(user.getName(), pageable);
    }

    public UserInfo getLoginUser(String token) {
        UserInfo userInfo = TokenUtil.loginUsers.getIfPresent(token);
        if (userInfo == null) {
            logger.info("用户未登录！");
        }
        return userInfo;
    }

}
