package com.gyj.Test.Other;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Gao on 2018/4/29.
 */
public class HashMapTest {


    /**
     * 自定义的类作为HashMap的key，key相同没有覆盖value
     */
    public static class Person {
        String id;
        String name;

        public Person(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String toString() {
            return "id=" + id + " name=" + name;
        }
    }

    /**
     * 重写hashCode()和equals()方法
     */
    public static class Person1 {
        String id;
        String name;
        public int hashCode() {
            return id.hashCode();
        }

        public Person1(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String toString() {
            return "id=" + id + " name=" + name;
        }

        public boolean equals(Object obj) {
            Person1 person1 = (Person1) obj;
            if (person1.id.equals(this.id)) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static void test() {
        HashMap<Person, String> map = new HashMap<>();
        Person person1 = new Person("111", "name1");
        Person person2 = new Person("111", "name1");
        map.put(person1, "address1");
        map.put(person2, "address2");

        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Person key = (Person) entry.getKey();
            String val = (String) entry.getValue();
            System.out.println("key=" + key + "  value=" + val);
        }
    }

    public static void test1() {
        HashMap<Person1, String> map = new HashMap<>();
        Person1 p1 = new Person1("111", "name1");
        Person1 p2 = new Person1("111", "name1");
        map.put(p1, "address1");
        map.put(p2, "address2");

        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Person1 key = (Person1) entry.getKey();
            String val = (String) entry.getValue();
            System.out.println("key=" + key + "  value=" + val);
        }
    }

    public static void main(String[] args) {
        test();
        test1();
    }
}

/**
 test()方法返回
 key=id=111 name=name1  value=address2
 key=id=111 name=name1  value=address1
 在向HashMap中添加键值对时，首先是调用Key的hashCode()方法生成一个hash值h1,如果这个h1在hashMap中不存在，
 则直接将键值对添加到HashMap中；如果h1存在，则找出HashMap中所有hash值为h1的key,然后分别调用key的equals()
 方法判断当前添加的key值是否与已经存在的key值相同，如果equals()方法返回true,说明当前添加的key值已经存在，
 则hashMap会使用新的值覆盖旧的value值，如果equals()方法返回false，说明新增的key在hashMap中不存在，则会在
 HashMap中创建新的映射关系。当新增的key的hash值已经在hashMap中存在时，会产生冲突。
 HashMap使用的是链地址法来解决冲突。


 使用自定义的类作为hashMap的key,没有重写hashCode和equals方法，默认使用的Object类的hashCode和equals方法，
 hashCode方法会返回对象存储的内存地址。上例中创建了2个对象，虽然内容相同但是存储的内存地址不同，
 test1()方法返回
 key=id=111 name=name1  value=address2
 */