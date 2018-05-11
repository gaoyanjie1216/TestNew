package com.gyj.modules.user.service.impl;

import com.gyj.base.*;
import com.gyj.modules.user.model.QUserInfo;
import com.gyj.modules.user.dao.IUserDao;
import com.gyj.modules.user.model.UserInfo;
import com.gyj.modules.user.service.IUserService;
import com.querydsl.core.types.Predicate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Gao on 2017/11/23.
 */
//@Service注解，将自动注册到Spring容器，不需要再在定义bean了
@Service
//@Transcational(readOnly=true) 当括号中添加readOnly=true,则会告诉底层数据源，这个是一个只读事务，
// 对于JDBC而言，只读事务会有一定的速度优化，但是加了之后删除不好使，所以还是默认不加了
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private org.springframework.jdbc.core.JdbcTemplate jdbcTemplate;

    private static List<UserInfo> result = new ArrayList<UserInfo>();

    private static UserInfo user = new UserInfo();

    public static final Log log = LogFactory.getLog(UserServiceImpl.class);
    private org.springframework.orm.hibernate3.HibernateTemplate hibernateTemplate;

    @Override
    public Page<UserInfo> getList(Pageable pageable) {
        return userDao.findAll(pageable);
    }

    @Override
    public Page<UserInfo> getList(String name, Pageable pageable) {
        String strName;
        if (null == name || "".equals(name)) {
            strName = "%";
        } else {
            strName = "%" + name + "%";
        }
        Page<UserInfo> page = userDao.findByNameContaining(strName, pageable);
        result = page.getContent();
        return page;
    }

    @Override
    public List<UserInfo> getCutPageList(int pageNo, int pageSize) {

        user.setPageNo(pageNo);
        //pageSize显示几条数据, 假设page默认设置为1,一页显示3条数据，如果设置page为2, 则会从第4条数据开始的3条数据,即4,5,6三条数据
        user.setPageSize(pageSize);
        //创建分页对象
        PageRequest pageRequest = new PageRequest(user.getPageNo() - 1, user.getPageSize());
        List<UserInfo> content = userDao.findAll(pageRequest).getContent();

        return content;
    }

    @Override
    public List<UserInfo> getCutPageSortList(int pageNo, int pageSize) {

        user.setPageSize(pageSize);
        user.setSord("desc");
        user.setPageNo(pageNo);

        //获取排序对象
        Sort.Direction sort_direction = Sort.Direction.ASC.toString().equalsIgnoreCase(user.getSord()) ? Sort.Direction.ASC : Sort.Direction.DESC;
        //设置排序对象参数, 这里设置根据id进行倒序排序
        Sort sort = new Sort(sort_direction, user.getSidx());
        //创建分页对象
        PageRequest pageRequest = new PageRequest(user.getPageNo() - 1, user.getPageSize(), sort);
        List<UserInfo> content = userDao.findAll(pageRequest).getContent();

        return content;
    }

    @Override
    public Result<UserInfo> findResultByPage(Result<UserInfo> result) {

        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "id");
        Pageable pageable = PageableHelper.instance(result, order);
        Page<UserInfo> pageList = userDao.findAll(pageable);
        BasePage<UserInfo> page = result.getPage();
        page.setResult(pageList);
        //result.getPage().setResult(pageList);
        return result;
    }

    @Override
    public Result<UserInfo> getConditionsList(Result<UserInfo> result, Map<String, String> conditions) {

        Pageable pageable = PageableHelper.instance(result, new Sort.Order(Sort.Direction.DESC, "id"));
        PredicateBuilder<QUserInfo> builder = new PredicateBuilder<>(QUserInfo.userInfo);
        builder.add(conditions.get("name"), ((path, val) -> path.name.containsIgnoreCase(val)));
        builder.add(conditions.get("age"), (path, val) -> path.age.eq(Integer.valueOf(val)));
        builder.add(conditions.get("address"), (path, val) -> path.address.containsIgnoreCase(val));
        builder.addDate(conditions.get("createTimeStart"), conditions.get("createTimeEnd"),
                (path, date1, date2) -> path.createTime.between(date1, date2),
                (path, val) -> path.createTime.after(val),
                (path, val) -> path.createTime.before(val));

        result.getPage().setResult(userDao.findAll(builder.build(), pageable));
        return result;
    }

    @Override
    public Page<UserInfo> findPredicateByPage(Predicate predicate, Pageable pageable) {
        return userDao.findAll(predicate, pageable);
    }

    @Override
    public List<UserInfo> getUserListByCreateTime(String createTimeStart, String createTimeEnd) {

        List<UserInfo> list = userDao.findBycreateTimeBetween(createTimeStart, createTimeEnd);
        return list;
    }

    @Override
    public List<Map<String, Object>> getConditionsByCreateTime(String createTimeStart, String createTimeEnd) {

        List<String> userResultList = userDao.findConditionsBycreateTimeBetween(createTimeStart, createTimeEnd);
        List<Map<String, Object>> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
        for (Object row : userResultList) {

            Map<String, Object> map = new HashMap<>();
            Object[] cells = (Object[]) row;
            map.put("name", cells[0]);
            map.put("address", cells[1]);
            map.put("age", cells[2] == null ? "" : cells[2]);
            map.put("create_time", sdf.format(cells[3]));
            list.add(map);
        }

        return list;
    }

    @Override
    public List<UserInfo> getUserListByCreateTimeJDBC(String createTimeStart, String createTimeEnd) {

        StringBuilder sql = new StringBuilder();
        sql.append(" select * from user_info");
        sql.append(" where Date(create_time)>='").append(createTimeStart).append("' and Date(create_time)<='").append(createTimeEnd).append("'");
        log.info(sql);

        List list = jdbcTemplate.queryForList(sql.toString()).stream().map(map -> map.values().toArray()).collect(Collectors.toList());
        return list;
    }

    @Override
    public void deleteUserByIds(List<Integer> ids) {
        //删除多个
        //userDao.deleteByIdIn(ids);
        for (Integer id : ids) {
            userDao.delete(id);
        }
    }

    @Override
    public ObjectPage pageSearch(String name, ObjectPage page) {
        Criteria criteria;
        Session session = null;
        try {
            //此处有问题，还没有获取到session
            session = this.getHibernateTemplate().getSessionFactory().openSession();
            criteria = session.createCriteria(UserInfo.class);
            criteria.add(Restrictions.eq("name", name));
            //查询不等于name的数据
            //criteria.add(Restrictions.or(Restrictions.gt("name", "%" + name + "%"), Restrictions.lt("name", "%" + name + "%")));
            //criteria.add(Restrictions.ne("name", "%" + name + "%"));
            if (!name.equals("")) {
                criteria.add(Restrictions.like("name", "%" + name + "%"));
            }
            criteria.addOrder(Order.desc("createTime"));
            List allList = criteria.list();
            criteria.setFirstResult(page.getEachPageRecordNum() * (page.getCurrentPageNo() - 1)).setMaxResults(page.getEachPageRecordNum());
            List list = criteria.list();
            page.setTotalRecordNum(allList.size());
            int eachPageRecordNum = page.getEachPageRecordNum();
            double totalPageNum;
            if (allList.isEmpty()) {
                totalPageNum = 1;
            } else {
                totalPageNum = Math.ceil((double) allList.size() / (double) eachPageRecordNum);
            }
            page.setTotalPageNum((int) totalPageNum);
            page.setList(list);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SessionFactoryUtils.closeSession(session);
        }

        return page;
    }

    public final org.springframework.orm.hibernate3.HibernateTemplate getHibernateTemplate() {
        return this.hibernateTemplate;
    }
}
