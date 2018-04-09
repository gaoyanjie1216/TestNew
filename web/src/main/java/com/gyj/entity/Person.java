package com.gyj.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by Gao on 2017/11/30.
 */
@Entity
@Table(name = "person")
@Data    //@ToString、@Getter、@Setter三个注解注释掉后添加@Data
public class Person {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "address")
    private String address;

    @Basic
    @Column(name = "age")
    private Integer age;

}
