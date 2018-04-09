package com.gyj.entity;

import com.gyj.base.BasePage;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Gao on 2017/11/21.
 */
@Entity
@Table(name = "user_info")
@Data    //@ToString、@Getter、@Setter三个注解注释掉后添加@Data
public class UserInfo extends BasePage implements Serializable{

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "address")
    private String address;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "age")
    private Integer age;

    @Basic
    @Column(name = "password")
    private String password;

    @Basic
    @Column(name = "create_time")
    private Date createTime;

    @Basic
    @Column(name = "lastmodified_time")
    private Date lastmodifiedTime;


    /*@Id
    @GeneratedValue
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserInfo that = (UserInfo) o;

        if (id != that.id) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }*/
}
