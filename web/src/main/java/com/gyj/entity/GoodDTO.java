package com.gyj.entity;

/**
 * Created by Gao on 2017/11/24.
 */

import lombok.Data;

import java.io.Serializable;

/**
 * 商品dto,两张商品表组合的实体
 * 码云：http://git.oschina.net/jnyqy
 */
//自定义返回的对象仅仅只是一个实体，并不对应数据库内的表，所以这里不需要配置@Entity、@Table等JPA注解
@Data
public class GoodDTO implements Serializable {

    //主键
    private Long id;

    //标题
    private String title;

    //单位
    private String unit;

    //价格
    private double price;

    //类型名称
    private String typeName;

    //类型编号
    private Long typeId;
}

