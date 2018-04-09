package com.gyj.Controller;

import com.gyj.entity.GoodDTO;
import com.gyj.entity.GoodInfoBean;
import com.gyj.entity.QGoodInfoBean;
import com.gyj.entity.QGoodTypeBean;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Gao on 2017/11/24.
 */
@RestController
@RequestMapping(value = "/queryDsl/good", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class QueryDSLGoodController {

    @Autowired
    private EntityManager entityManager;

    //查询工厂实体
    private JPAQueryFactory queryFactory;

    //实例化控制器完成后执行该方法实例化JPAQueryFactory
    @PostConstruct
    public void initFactory() {
        System.out.println("开始实例化JPAQueryFactory");
        queryFactory = new JPAQueryFactory(entityManager);
    }

    @ApiOperation(value = "使用QueryDsl查询商品信息", notes = "")
    @RequestMapping(value = "/selectGoodByType", method = RequestMethod.GET)
    //类型编号
    public List<GoodInfoBean> selectGoodByType(@RequestParam(value = "typeId") Long typeId) {
        //商品查询实体
        QGoodInfoBean _Q_good = QGoodInfoBean.goodInfoBean;
        //商品类型查询实体
        QGoodTypeBean _Q_good_type = QGoodTypeBean.goodTypeBean;

        //查询了两张表，仅返回了商品信息内的字段（select(_Q_good)）,在where条件内进行了这两张表的关联，
        //根据传递的类型编号作为关联商品类型主键（相当于left join），最后根据排序字段进行倒序
        //QueryDSL自动生成的SQL采用了Cross Join 获取两张表的《笛卡尔集》然后根据select内配置的实体进行返回字段，
        //使用 where goodinfobe0_.tg_type_id=goodtypebe1_.tgt_id 代替了on goodinfobe0_.tg_type_id=goodtypebe1_.tgt_id实现了相同的效果

        return queryFactory
                .select(_Q_good)
                .from(_Q_good, _Q_good_type)
                .where(
                        //为两个实体关联查询
                        _Q_good.typeId.eq(_Q_good_type.id)
                                .and(
                                        //查询指定typeid的商品
                                        _Q_good_type.id.eq(typeId)
                                )
                )
                //根据排序字段倒序
                .orderBy(_Q_good.order.desc())
                //执行查询
                .fetch();
    }

    /**
     * 根据QueryDSL查询
     *
     * @return
     */
    //http://127.0.0.1:8080/queryDsl/good/selectGoodDTOWithQueryDSL
    @ApiOperation(value = "使用QueryDsl自定义查询商品信息", notes = "")
    @RequestMapping(value = "/selectGoodDTOWithQueryDSL", method = RequestMethod.GET)
    public List<GoodDTO> selectGoodDTOWithQueryDSL() {

        //商品基本信息
        QGoodInfoBean _Q_good = QGoodInfoBean.goodInfoBean;
        //商品类型
        QGoodTypeBean _Q_good_type = QGoodTypeBean.goodTypeBean;

        //JPAQueryFactory工厂select方法可以将Projections方法返回的QBean作为参数，我们通过Projections的bean方法来构建返回的结果集映射到实体内，
        //有点像Mybatis内的ResultMap的形式，不过内部处理机制肯定是有着巨大差别的！bean方法第一个参数需要传递一个实体的泛型类型作为返回集合内的单个对象类型，
        //如果QueryDSL查询实体内的字段与DTO实体的字段名字不一样时，我们就可以采用as方法来处理，为查询的结果集指定的字段添加别名，这样就会自动映射到DTO实体内。
        //生成的SQL是cross join形式关联查询，关联 形式通过where goodinfobe0_.tg_type_id=goodtypebe1_.tgt_id 代替了on goodinfobe0_.tg_type_id=goodtypebe1_.tgt_id，最终查询结果集返回数据这两种方式一致。

        return queryFactory
                .select(
                        Projections.bean(
                                GoodDTO.class,   //返回自定义实体的类型
                                _Q_good.id,
                                _Q_good.price,
                                _Q_good.title,
                                _Q_good.unit,
                                _Q_good_type.name.as("typeName"),   //使用别名对应dto内的typeName
                                _Q_good_type.id.as("typeId")        //使用别名对应dto内的typeId
                        )
                )
                .from(_Q_good, _Q_good_type)                   //构建两表笛卡尔集
                .where(_Q_good.typeId.eq(_Q_good_type.id))     //关联两表
                .orderBy(_Q_good.order.desc())                 //倒序
                .fetch();
    }

    /**
     * 使用java8新特性Collection内stream方法转换dto
     *
     * @return
     */
    @ApiOperation(value = "使用QueryDsl自定义查询商品信息", notes = "")
    @RequestMapping(value = "/selectGoodDTOWithStream", method = RequestMethod.GET)
    public List<GoodDTO> selectGoodDTOWithStream() {

        //商品基本信息
        QGoodInfoBean _Q_good = QGoodInfoBean.goodInfoBean;
        //商品类型
        QGoodTypeBean _Q_good_type = QGoodTypeBean.goodTypeBean;
        return queryFactory
                .select(
                        _Q_good.id,
                        _Q_good.price,
                        _Q_good.title,
                        _Q_good.unit,
                        _Q_good_type.name,
                        _Q_good_type.id
                )
                .from(_Q_good, _Q_good_type)//构建两表笛卡尔集
                .where(_Q_good.typeId.eq(_Q_good_type.id))//关联两表
                .orderBy(_Q_good.order.desc())//倒序
                .fetch()
                .stream()
                //转换集合内的数据
                .map(tuple -> {
                    //创建商品dto
                    GoodDTO dto = new GoodDTO();
                    //设置商品编号
                    dto.setId(tuple.get(_Q_good.id));
                    //设置商品价格
                    dto.setPrice(tuple.get(_Q_good.price));
                    //设置商品标题
                    dto.setTitle(tuple.get(_Q_good.title));
                    //设置单位
                    dto.setUnit(tuple.get(_Q_good.unit));
                    //设置类型编号
                    dto.setTypeId(tuple.get(_Q_good_type.id));
                    //设置类型名称
                    dto.setTypeName(tuple.get(_Q_good_type.name));
                    //返回本次构建的dto
                    return dto;
                })
                //返回集合并且转换为List<GoodDTO>
                .collect(Collectors.toList());
    }

}

