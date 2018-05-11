package com.gyj.modules.user.service;

import com.gyj.base.ObjectPage;
import com.gyj.base.Result;
import com.gyj.modules.user.model.UserInfo;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by Gao on 2017/11/23.
 */
public interface IUserService {

    Page<UserInfo> getList(Pageable pageable);

    Page<UserInfo> getList(String name, Pageable pageable);

    List<UserInfo> getCutPageList(int pageNo, int pageSize);

    List<UserInfo> getCutPageSortList(int pageNo, int pageSize);

    Result<UserInfo> findResultByPage(Result<UserInfo> result);

    /**
     * 多条件分页模糊查询
     *
     * @param result
     * @param conditions
     * @return
     */
    Result<UserInfo> getConditionsList(Result<UserInfo> result, Map<String, String> conditions);

    Page<UserInfo> findPredicateByPage(Predicate predicate, Pageable pageable);

    /**
     * 使用@query自定义查询方式，根据创建时间返回用户列表
     *
     * @param createTimeStart
     * @param createTimeEnd
     * @return
     */
    List<UserInfo> getUserListByCreateTime(String createTimeStart, String createTimeEnd);

    /**
     * 表中某些字段组成的map
     *
     * @param createTimeStart
     * @param createTimeEnd
     * @return
     */
    List<Map<String, Object>> getConditionsByCreateTime(String createTimeStart, String createTimeEnd);

    /**
     * 使用JDBC方式，根据创建时间返回用户列表
     *
     * @param createTimeStart
     * @param createTimeEnd
     * @return
     */
    List<UserInfo> getUserListByCreateTimeJDBC(String createTimeStart, String createTimeEnd);

    /**
     * 根据id删除用户
     *
     * @param ids
     */
    void deleteUserByIds(List<Integer> ids);

    /**
     * hibernate查询分页，session没有获取到
     *
     * @param name
     * @param page
     * @return
     */
    ObjectPage pageSearch(String name, ObjectPage page);

}
