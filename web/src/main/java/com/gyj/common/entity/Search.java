package com.gyj.common.entity;

import com.gyj.base.BasePage;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Gao on 2017/12/15.
 */
@Entity
@Table(name = "search")
@Data    //@ToString、@Getter、@Setter三个注解注释掉后添加@Data
public class Search extends BasePage implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "login_name")
    private String loginName;

    @Basic
    @Column(name = "ip")
    private String ip;

    @Column(name = "conditions")
    private Integer conditions;

    @Column(name = "sys_created")
    private Date sys_created;

    @Column(name = "sys_lastmodified")
    private Date sys_lastmodified;
}