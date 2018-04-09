package com.gyj.Dao;

import com.gyj.entity.UserBean;

/**
 * Created by Gao on 2017/11/24.
 */
//我们在继承BaseJPA的时候用到了泛型，因为我们在BaseJPA内所继承的接口都需要我们传递一个具体的实体类的类型，
// 所以这块我们采用了泛型来处理，只有具体逻辑JPA继承BaseJPA的时候传递具体的实体类型就可以了
public interface UserJPA extends BaseJPA<UserBean> {

    //可以添加命名方法查询
}
