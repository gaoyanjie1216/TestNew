package com.gyj.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;

/**
 * Created by Gao on 2017/11/21.
 */
@Entity
@Table(name = "girl")
@Data //@ToString、@Getter、@Setter三个注解注释掉后添加@Data
public class Girl {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "cup_size")
    private String cupSize;

    //表单验证@Min对年龄有最低的限制
    @Min(value = 18, message = "未成年不能进入！")
    @Basic
    @Column(name = "age")
    private Integer age;

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
    @Column(name = "cup_size")
    public String getCupSize() {
        return cupSize;
    }

    public void setCupSize(String cupSize) {
        this.cupSize = cupSize;
    }

    @Basic
    @Column(name = "age")
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Girl that = (Girl) o;

        if (id != that.id) return false;
        if (age != that.age) return false;
        if (cupSize != null ? !cupSize.equals(that.cupSize) : that.cupSize != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (cupSize != null ? cupSize.hashCode() : 0);
        result = 31 * result + age;
        return result;
    }*/
}
