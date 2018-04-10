package com.gyj.Test.Baseknowledge;

/**
 * static(静态\修饰符)
 * 1. static修饰成员变量 ：如果有数据需要被共享给所有对象使用时，那么就可以使用static修饰。
 * 静态成员变量的访问方式：
 * 方式1： 可以使用对象进行访问。
 * 格式： 对象.变量名。
 * <p>
 * 方式二： 可以使用类名进行访问。（静态成员变量的数据）
 * 格式： 类名.变量名;
 * 注意：
 * 1. 非静态的成员变量只能使用对象进行访问，不能使用类名进行访问。
 * 2. 千万不要为了方便访问数据而使用static修饰成员变量，只有成员变量的数据是真正需要被共享的时候才使用static修饰。
 * static修饰成员变量的应用场景： 如果一个数据需要被所有对象共享使用的时候，这时候即可使用static修饰。
 * 2. static修饰成员函数:
 * Created by Gao on 2018/4/10.
 */
class Student {

    static String name;
    static String country;

    public Student(String name, String country) {
        this.name = name;
        this.country = country;
    }
}

class Student1 {

    static String name;
    String country;

    public Student1(String name, String country) {
        this.name = name;
        this.country = country;
    }
}

public class StaticTest1 {

    public static void main(String[] args) {

        /**
         * 静态的成员变量可以使用类名进行访问，并且静态变量修改的值以最后一次修改的值为准。
         非静态的成员变量不能使用类名进行访问，只能使用对象进行访问。
         如何才能把这个数据移动到数据共享区中共享呢？
         只需要使用static修饰该数据即可。数据移动到数据共享区中共享。
         静态的成员变量只会在数据共享区中维护一份，而非静态成员变量的数据会在每个对象中都维护一份的。
         */
        new Student("张三", "日本");
        new Student("张一", "韩国");
        System.out.println("姓名: " + Student.name + " 国籍: " + Student.country);
        Student.country = "美国";
        System.out.println("姓名: " + Student.name + " 国籍: " + Student.country);
        /*姓名: 张一 国籍: 韩国
        姓名: 张一 国籍: 美国*/


        Student1 s1 = new Student1("张三", "日本");
        Student1 s2 = new Student1("张一", "韩国");
        System.out.println("姓名: " + Student.name + " 国籍: " + s1.country);
        System.out.println("姓名: " + Student.name + " 国籍: " + s2.country);

        /*姓名: 张一 国籍: 日本
        姓名: 张一 国籍: 韩国*/
    }
}


