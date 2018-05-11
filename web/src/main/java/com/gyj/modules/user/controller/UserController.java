package com.gyj.modules.user.controller;

/**
 * Created by Gao on 2017/11/22.
 */

import com.google.common.collect.Maps;
import com.gyj.modules.user.dao.IUserDao;
import com.gyj.modules.user.model.UserInfo;
import com.gyj.modules.user.service.IUserService;
import com.gyj.Utils.DateUtils;
import com.gyj.base.ObjectPage;
import com.gyj.base.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserController {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private IUserService userService;

    // 创建线程安全的Map
    static Map<Integer, UserInfo> user = Collections.synchronizedMap(new HashMap<Integer, UserInfo>());

    public static final Log log = LogFactory.getLog(UserController.class);

    /**
     * 获取用户列表
     *
     * @return
     */
    @ApiOperation(value = "获取全部用户列表", notes = "")
    @RequestMapping(value = "/getUserList", method = RequestMethod.GET)
    public List<UserInfo> getUserList() {
        List<UserInfo> uerList = userDao.findAll();
        log.info(333);
        return uerList;
    }

    /**
     * 获取用户列表，按照创建时间排序
     *
     * @return
     */
    @ApiOperation(value = "获取全部用户列表，按照创建时间排序", notes = "")
    @RequestMapping(value = "/getUserListOrder", method = RequestMethod.GET)
    public List<UserInfo> getUserListOrder() {
        //table表字段对应的java对象字段名
        Sort sort = new Sort(Sort.Direction.DESC, "createTime");
        //Sort sort = new Sort(Sort.Direction.DESC, "createTime").and(new Sort(Sort.Direction.DESC, "id"));
        List<UserInfo> uerList = userDao.findAll(sort);
        return uerList;
    }

    /**
     * 创建用户
     *
     * @param address
     * @param name
     * @return
     */
    @ApiOperation(value = "创建用户", notes = "根据user对象创建user")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = false, dataType = "User")
    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    public UserInfo postUser(@ApiParam(value = "用户姓名") @RequestParam("name") String name,
                             @ApiParam(value = "用户地址") @RequestParam("address") String address) {

        UserInfo user = new UserInfo();
        user.setAddress(address);
        user.setName(name);
        user.setCreateTime(new Date());
        user.setLastmodifiedTime(new Date(System.currentTimeMillis()));
        return userDao.save(user);
    }

    /**
     * 根据用户ID获取用户详细信息
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = false, dataType = "Long")
    @RequestMapping(value = "/getUserDetail/{id}", method = RequestMethod.GET)
    public UserInfo getUser(@PathVariable Integer id) {
        return userDao.findOne(id);
    }

    /**
     * 更新用户详细信息
     *
     * @param id
     * @param name
     * @return
     */
    @ApiOperation(value = "更新用户详细信息", notes = "根据url的id来指定更新对象，并根据传过来的user信息来更新用户详细信息")
    @RequestMapping(value = "/updateUser", method = RequestMethod.PUT)
    public Map putUser(@RequestParam("id") Integer id, @RequestParam("name") String name, @RequestParam("address") String address) {
        Map<String, Object> result = Maps.newHashMap();
        UserInfo user = userDao.findOne(id);
        user.setName(name);
        user.setAddress(address);
        user.setLastmodifiedTime(new Date(System.currentTimeMillis()));
        userDao.save(user);
        result.put("success", user);
        return result;
    }

    /**
     * 根据年龄查询
     *
     * @param age
     * @return
     */
    @ApiOperation(value = "根据age查询", notes = "")
    @GetMapping(value = "/findByAgeList")
    public Map<String, Object> findByAgeList(@RequestParam("age") Integer age) {

        Map<String, Object> result = Maps.newHashMap();
        List<UserInfo> userListGreaterThan = userDao.findByAgeGreaterThan(age);
        List<UserInfo> userListNativeQuery = userDao.nativeQuery(age);
        List<UserInfo> userListLessThanEqual = userDao.findByAgeLessThanEqual(age);
        result.put("1", userListGreaterThan);
        result.put("2", userListNativeQuery);
        result.put("3", userListLessThanEqual);
        return result;
    }

    /**
     * 获取全部列表数据,带分页
     *
     * @param pageable
     * @return
     */
    @ApiOperation(value = "分页查询列表", notes = "获取全部列表数据")
    @GetMapping(value = "/getAllPageList")
    public Page<UserInfo> getAllPageList(Pageable pageable) {

        Page<UserInfo> list = this.userService.getList(pageable);
        return list;
    }

    /**
     * 分页查询列表, 通过姓名模糊匹配查找列表
     *
     * @param name
     * @param pageable
     * @return
     */
    @ApiOperation(value = "分页查询列表, 通过姓名模糊匹配查找列表", notes = "通过姓名模糊匹配查找列表")
    @RequestMapping(value = "/getPagelist/{name}", method = RequestMethod.GET)
    public Page<UserInfo> getPagelist(@ApiParam(value = "模糊关键词") @PathVariable("name") String name, Pageable pageable) {

        Page<UserInfo> list = this.userService.getList(name, pageable);
        return list;
    }

    /**
     * 根据页码
     * @param pageNo
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "分页查询列表，返回Map")
    @RequestMapping(value = "/getCutPageList", method = RequestMethod.GET)
    public Map<String, Object> getCutPageList(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize) {

        Map<String, Object> map = Maps.newHashMap();
        List<UserInfo> cutPageList = this.userService.getCutPageList(pageNo, pageSize);
        List<UserInfo> cutPageSortList = this.userService.getCutPageSortList(pageNo, pageSize);
        map.put("1", cutPageList);
        map.put("2", cutPageSortList);
        return map;
    }

    /**
     * 分页查询列表,返回Result对象,全部列表数据
     *
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "分页查询列表,返回Result对象")
    @RequestMapping(value = "/getResultPageList", method = RequestMethod.GET)
    public Result<UserInfo> getResultPageList(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize) throws Exception {

        //@RequestParam Map<String, String> conditions
        /*int pageNo = MapUtils.getInteger(conditions, "pageNumber", null);
        int pageSize = MapUtils.getInteger(conditions, "pageSize", null);*/
        Result<UserInfo> result = new Result<UserInfo>(pageNo, pageSize);
        result = userService.findResultByPage(result);
        return result;
    }

    /**
     * 根据条件分页查询列表,返回Result对象,返回根据条件查询到的数据
     *
     * @param pageNo
     * @param pageSize
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "根据条件分页查询列表,返回Result对象")
    @RequestMapping(value = "/getConditionsList", method = RequestMethod.GET)
    public Result<UserInfo> getConditionsList(@RequestParam("pageNo") Integer pageNo, @RequestParam("pageSize") Integer pageSize,
                                              @RequestParam(value = "name", required = false) String name,
                                              @RequestParam(value = "createTimeStart", required = false) String createTimeStart,
                                              @RequestParam(value = "createTimeEnd", required = false) String createTimeEnd,
                                              @RequestParam(value = "age", required = false) Integer age,
                                              @RequestParam(value = "address", required = false) String address) throws Exception {

        Result<UserInfo> result = new Result<UserInfo>(pageNo, pageSize);
        Map<String, String> conditions = Maps.newHashMap();
        conditions.put("name", name);
        conditions.put("createTimeStart", createTimeStart);
        conditions.put("createTimeEnd", createTimeEnd);
        conditions.put("age", (String.valueOf(age)));
        conditions.put("address", address);
        result = userService.getConditionsList(result, conditions);
        return result;
    }

    /**
     * 查询两个时间查询列表
     * @param createTimeStart
     * @param createTimeEnd
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "根据创建时间，查询两个时间之间的列表")
    @RequestMapping(value = "/getUserListByCreateTime", method = RequestMethod.GET)
    public Map<String, Object> getUserListByCreateTime(@RequestParam(value = "createTimeStart", required = false) String createTimeStart,
                                                  @RequestParam(value = "createTimeEnd", required = false) String createTimeEnd) throws Exception {

        Map<String, Object> result = Maps.newHashMap();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String beginDate = DateUtils.getStatetime(29);
        Date date = new Date();

        //时间为空, 默认最近的30天
        if (StringUtils.isBlank(createTimeStart) || StringUtils.isBlank(createTimeEnd)) {
            createTimeStart = beginDate;
            createTimeEnd = sdf.format(date);
        }

        //使用jdbc查询,返回对象
        List<UserInfo> list1 = userService.getUserListByCreateTimeJDBC(createTimeStart, createTimeEnd);
        //@query自定义查询,返回对象
        List<UserInfo> list2 = userService.getUserListByCreateTime(createTimeStart, createTimeEnd);
        //表中某些字段组成的map
        List<Map<String, Object>> list3 = userService.getConditionsByCreateTime(createTimeStart, createTimeEnd);

        result.put("1", list1);
        result.put("2", list2);
        result.put("3", list3);
        return result;
    }

    /**
     * hibernate查询列表数据,带分页
     * 现在xml配置没有正好，session没有获取到，还需要进一步研究
     *
     * @param name
     * @param pageNo
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "hibernate查询列表数据，带分页")
    @RequestMapping(value = "/pageSearch.do", method = RequestMethod.GET)
    public ObjectPage pageSearch(@RequestParam(value = "name") String name, @RequestParam("pageNo") Integer pageNo,
                                 @RequestParam("pageSize") Integer pageSize) {

        ObjectPage page = new ObjectPage();
        page.setCurrentPageNo(pageNo);
        page.setEachPageRecordNum(pageSize);

        return userService.pageSearch(name, page);
    }

    /**
     * 根据id来指定删除用户
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "删除用户", notes = "根据url的id来指定删除对象")
    @RequestMapping(value = "/deleteUserById", method = RequestMethod.DELETE)
    public String deleteUserById(@RequestParam("id") Integer id) {
        userDao.delete(id);
        return "success";
    }

    /**
     * 根据id来指定删除用户,可以有多个id
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除用户", notes = "根据url的id来指定删除对象")
    @RequestMapping(value = "/deleteUserByIds", method = RequestMethod.DELETE)
    public void deleteUserByIds(@RequestParam("ids") List<Integer> ids) {

      /*  List<Integer> list = new ArrayList<>();
        list.add(ids);*/
        userService.deleteUserByIds(ids);
    }

    /**
     * 根据姓名和年龄来删除用户
     *
     * @param name
     * @param age
     * @return
     */
    @ApiOperation(value = "根据姓名和年龄来删除用户", notes = "根据url的id来指定删除对象")
    @RequestMapping(value = "/deleteUserByNameAndAge", method = RequestMethod.DELETE)
    public String deleteUserByNameAndAge(@RequestParam("name") String name, @RequestParam("age") Integer age) {
        userDao.deleteNativeQueryByNameAndAge(name, age);
        return "success";
    }

    /**
     * 根据年龄来删除用户
     *
     * @param age
     * @return
     */
    @ApiOperation(value = "根据年龄来删除用户", notes = "根据url的id来指定删除对象")
    @RequestMapping(value = "/deleteUserByAge", method = RequestMethod.DELETE)
    public void deleteUserByAge(@RequestParam("age") Integer age) {
        userDao.deleteByAge(age);
    }

}
