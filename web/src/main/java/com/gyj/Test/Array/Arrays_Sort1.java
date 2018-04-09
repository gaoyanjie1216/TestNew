package com.gyj.Test.Array;

/**
 * Created by Gao on 2018/4/3.
 */

import java.util.Arrays;
import java.util.Comparator;

/*
class Dog {
    int size;

    public Dog(int s) {
        size = s;
    }
}

//sort方法的使用非常的简单明了,下面的例子中,先定义一个比较Dog大小的Comparator,
//然后将其实例对象作为参数传给sort方法,通过此示例,你应该能够快速掌握Arrays.sort()的使用方法。
class DogSizeComparator implements Comparator<Dog> {

    @Override
    public int compare(Dog o1, Dog o2) {
        return o1.size - o2.size;
    }
}

public class Arrays_Sort1 {

    public static void main(String[] args) {
        Dog d1 = new Dog(2);
        Dog d2 = new Dog(1);
        Dog d3 = new Dog(3);

        Dog[] dogArray = {d1, d2, d3};
        printDogs(dogArray);  // 2 1 3

        Arrays.sort(dogArray, new DogSizeComparator());
        printDogs(dogArray);  //1 2 3
    }

    private static void printDogs(Dog[] dogs) {
        for (Dog d : dogs)
            System.out.print(d.size + " ");

        System.out.println();
    }
}
*/

/**
 * 这是策略模式(Strategy pattern)的一个完美又简洁的示例,值得一提的是为什么这种场景下适合使用策略模式.
   总体来说,策略模式允许在程序执行时选择不同的算法.比如在排序时,传入不同的比较器(Comparator),就采用不同的算法.
   根据上面的例子,假设你想要根据Dog的重量来进行排序,可以像下面这样,创建一个新的比较器来进行排序:
 */
class Dog {
    int size;
    int weight;

    public Dog(int s, int w) {
        size = s;
        weight = w;
    }
}

class DogSizeComparator implements Comparator<Dog> {

    @Override
    public int compare(Dog o1, Dog o2) {
        return o1.size - o2.size;
    }
}

class DogWeightComparator implements Comparator<Dog> {

    @Override
    public int compare(Dog o1, Dog o2) {
        return o1.weight - o2.weight;
    }
}

public class Arrays_Sort1 {

    public static void main(String[] args) {
        Dog d1 = new Dog(2, 50);
        Dog d2 = new Dog(1, 30);
        Dog d3 = new Dog(3, 40);

        Dog[] dogArray = {d1, d2, d3};
        printDogs(dogArray);

        Arrays.sort(dogArray, new DogSizeComparator());
        printDogs(dogArray);

        Arrays.sort(dogArray, new DogWeightComparator());
        printDogs(dogArray);
    }

    private static void printDogs(Dog[] dogs) {
        for (Dog d : dogs)
            System.out.print("size=" + d.size + " weight=" + d.weight + " ");

        System.out.println();
    }
}

/**
 * size=2 weight=50 size=1 weight=30 size=3 weight=40
   size=1 weight=30 size=2 weight=50 size=3 weight=40
   size=1 weight=30 size=3 weight=40 size=2 weight=50
 */
