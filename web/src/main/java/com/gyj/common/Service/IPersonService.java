package com.gyj.common.Service;

import com.gyj.common.entity.Person;

/**
 * Created by Gao on 2017/11/30.
 */
public interface IPersonService {

    public Person save(Person person);

    public void remove(Integer id);

    public Person findOne(Integer id);
}
