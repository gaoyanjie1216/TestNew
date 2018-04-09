package com.gyj.Service.Impl;

import com.gyj.Dao.PersonRepository;
import com.gyj.Service.IPersonService;
import com.gyj.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Gao on 2017/11/30.
 */
@Transactional
@Service
//@CacheConfig注解是用来开启声明的类参与缓存,如果方法内的@Cacheable注解没有添加key值,那么会自动使用cahceNames配置参数并且追加方法名。
@CacheConfig(cacheNames = "person")
public class PersonServiceImpl implements IPersonService {

    @Autowired
    PersonRepository personRepository;

    //@CachePut表示缓存新添加的数据或者更新的数据到缓存中，两个参数value表示缓存的名称为people，key表示缓存的key为person的id
    @CachePut(value = "person", key = "#person.id")
    @Override
    public Person save(Person person) {
        Person p = personRepository.save(person);
        System.out.println("为id、key为" + p.getId() + "数据做了缓存");
        return p;
    }

    //@CacheEvict表示从缓存people中删除key为id的数据
    @CacheEvict(value = "person")
    @Override
    public void remove(Integer id) {
        System.out.println("删除了id、key为" + id + "的数据缓存");
        personRepository.delete(id);
    }

    //http://127.0.0.1:8081/person/findOnePerson?id=2可用再次请求不会显示sql
    //@Cacheable表示添加数据到缓存中，缓存名称为people，缓存key为person的id属性
    @Cacheable(value = "person")
    @Override
    public Person findOne(Integer id) {
        Person person = personRepository.findOne(id);
        System.out.println("为id、key为" + person.getId() + "数据做了缓存");
        return person;
    }
}

