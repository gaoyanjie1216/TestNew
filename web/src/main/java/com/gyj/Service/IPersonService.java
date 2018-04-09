package com.gyj.Service;

import com.gyj.entity.Person;

/**
 * Created by Gao on 2017/11/30.
 */
public interface IPersonService {

    public Person save(Person person);

    public void remove(Integer id);

    public Person findOne(Integer id);
}
