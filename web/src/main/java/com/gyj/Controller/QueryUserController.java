package com.gyj.Controller;

import com.gyj.Dao.UserJPA;
import com.gyj.entity.QUserBean;
import com.gyj.entity.UserBean;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by Gao on 2017/11/24.
 */

//@RestController该控制器返回的数据都是Json字符串,这也是RestController所遵循的规则。
@RestController
@RequestMapping(value = "/query/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class QueryUserController {

    @Autowired
    private UserJPA userJPA;

    //实体管理者, 在bean构造函数初始化时通过EntityManager对象实例化JPAQueryFactory查询工厂实体
    //方便我们接下来的查询操作,QueryDsl形式是需要建立在JPAQueryFactory对象基础上构建的
    @Autowired
    private EntityManager entityManager;

    //JPA查询工厂
    private JPAQueryFactory queryFactory;

    //@PostConstruct注解在类初始化的时候完成对JPAQueryFactory对象的实例化
    @PostConstruct
    public void initFactory() {
        queryFactory = new JPAQueryFactory(entityManager);
        System.out.println("init JPAQueryFactory successfully");
    }

    /**
     * 查询全部数据并根据id倒序
     *
     * @return
     */
    @ApiOperation(value = "获取全部用户列表", notes = "")
    @RequestMapping(value = "/getAllUserList", method = RequestMethod.GET)
    public List<UserBean> getAllUserList() {

        //使用querydsl查询
        QUserBean quser = QUserBean.userBean;
        //查询并返回结果集, 使用JPAQueryFactory工厂对象的selectFrom方法来简化查询
        //该方法代替了select&from两个方法, 注意：也是仅限单表操作时可以使用。
        return queryFactory
                .selectFrom(quser)             //查询源
                .orderBy(quser.id.desc())      //根据id倒序
                .fetch();                      //执行查询并获取结果集
    }

    /**
     * 根据QueryDSL查询获取用户
     *
     * @param id 主键编号
     * @return
     */
    @ApiOperation(value = "根据QueryDSL查询获取用户", notes = "")
    @RequestMapping(value = "/getUserDetailByDsl/{id}", method = RequestMethod.GET)
    public UserBean getUserDetailByDsl(@PathVariable("id") Long id) {

        //使用querydsl查询
        QUserBean quser = QUserBean.userBean;
        //查询并返回结果集
        return queryFactory
                .selectFrom(quser)           //查询源
                .where(quser.id.eq(id))      //指定查询具体id的数据
                .fetchOne();                 //执行查询并返回单个结果
    }

    /**
     * SpringDataJPA & QueryDSL实现单数据查询
     * 只是简单的查询整合风格要比纯QueryDSL要简便，但是如果添加排序、模糊查询时还是纯QueryDSL编写更简单一些
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据QueryDSL和JPA联合查询获取用户信息", notes = "")
    @RequestMapping(value = "/getUserDetailByDslAndJpa/{id}", method = RequestMethod.GET)
    public UserBean getUserDetailByDslAndJpa(@PathVariable("id") Long id) {

        //使用querydsl查询
        QUserBean quser = QUserBean.userBean;
        //查询并返回指定id的单条数据
        return userJPA.findOne(quser.id.eq(id));
    }

    /**
     * 根据名称模糊查询
     *
     * @param name
     * @return
     */
    @ApiOperation(value = "根据名称模糊查询", notes = "")
    @RequestMapping(value = "/likeQueryWithName", method = RequestMethod.GET)
    public List<UserBean> likeQueryWithName(@RequestParam("name") String name) {

        //使用querydsl查询
        QUserBean quser = QUserBean.userBean;

        /*return queryFactory
                .selectFrom(quser)
                .where(quser.name.like("%" + name + "%"))        //只是写like也会精确查到了结果
                .fetch();*/

        return queryFactory
                .selectFrom(quser)                               //查询源
                .where(quser.name.containsIgnoreCase(name))      //根据name模糊查询
                .fetch();                                        //执行查询并返回结果集
    }

    /**
     * 使用Jpa更新会员信息
     *
     * @param userBean
     */
    @ApiOperation(value = "使用Jpa更新会员信息", notes = "")
    @RequestMapping(value = "/updateUserWithJpa", method = RequestMethod.GET)
    public String updateUserWithJpa(UserBean userBean) {

        //http://127.0.0.1:8080/query/user/updateUserWithJpa?id=6&name=change&age=23&address=SdJN&pwd=123456
        userJPA.save(userBean);
        return "success!";
    }

    /**
     * 使用QueryDsl更新会员信息
     *
     * @param userBean
     */
    @ApiOperation(value = "使用QueryDsl更新会员信息", notes = "")
    @Transactional  //不加的话会抛出TransactionRequiredException: Executing an update/delete query
    @RequestMapping(value = "/updateWithQueryDsl", method = RequestMethod.GET)
    public String updateWithQueryDsl(UserBean userBean) {

        //querydsl查询实体
        QUserBean quer = QUserBean.userBean;

        //先获取了QUserBean查询对象，并且通过JPAQueryFactory对象构建了update方法处理，
        //而update的参数就是需要更新的查询实体，当然update方法内仅支持更新单个查询实体
        queryFactory
                .update(quer)    //更新对象
                .set(quer.name, userBean.getName())   //更新字段列表
                .set(quer.address, userBean.getAddress())
                .set(quer.age, userBean.getAge())
                .set(quer.pwd, userBean.getPwd())
                .where(quer.id.eq(userBean.getId()))    //更新条件
                .execute();      //执行更新,如果不调用execute方法就不会执行更新操作
        return "success!";
    }

    /**
     * 使用Jpa删除会员信息
     *
     * @param userBean
     */
    @ApiOperation(value = "使用Jpa删除会员信息", notes = "")
    @RequestMapping(value = "/deleteUserWithJpa", method = RequestMethod.GET)
    public String deleteUserWithJpa(UserBean userBean) {

        //执行删除指定主键的值
        userJPA.delete(userBean.getId());
        return "success!";
    }

    /**
     * 使用QueryDsl删除会员信息
     *
     * @param userBean
     */
    @ApiOperation(value = "使用QueryDsl删除会员信息", notes = "")
    @Transactional
    @RequestMapping(value = "/deleteUserWithQueryDsl", method = RequestMethod.GET)
    public String deleteUserWithQueryDsl(UserBean userBean) {

        //querydsl查询实体
        QUserBean quer = QUserBean.userBean;

        queryFactory
                .delete(quer)                            //删除对象
                .where(quer.id.eq(userBean.getId()))     //删除条件
                .execute();                              //执行删除
        return "success!";
    }

    /**
     * 使用QueryDsl删除年龄大于20岁的会员信息
     *
     * @param userBean
     * @return
     */
    @ApiOperation(value = "使用QueryDsl删除年龄大于20岁的会员信息", notes = "")
    @RequestMapping(value = "/deleteUserWithQueryDslByAge", method = RequestMethod.GET)
    @Transactional
    public String deleteUserWithQueryDslByAge(UserBean userBean) {

        //querydsl查询实体
        QUserBean quer = QUserBean.userBean;

        queryFactory
                .delete(quer)        //删除对象
                //删除条件
                .where(quer.name.eq(userBean.getName())
                        .and(quer.age.gt(20)))
                .execute();          //执行删除
        return "success!";
    }

}
