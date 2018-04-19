package com.gyj.Test.Thread.ConcurrentHashMap;

/**
 * Created by Gao on 2018/4/19.
 */
public class Notice {

    /**
     ConcurrentHashMap是Java并发包中提供的一个线程安全且高效的HashMap实现，ConcurrentHashMap在并发编程的场景中使用频率非常之高

     HashMap是一个Entry对象的数组。数组中的每一个Entry元素，又是一个链表的头节点.HashMap是线程不安全的，
     在高并发环境下做插入操作，可能会形成环状链表（扩容时可能造成），
     导致get操作时，cpu空转，让下一次操作出现死循环，所以，在并发环境中使用HashMap是非常危险的。

     HashTable：HashTable和HashMap的实现原理几乎一样，差别无非是1.HashTable不允许key和value为null；2.HashTable是线程安全的。
     但是HashTable线程安全的策略实现代价却太大了，简单粗暴，get/put所有相关操作都是synchronized的，这相当于给整个哈希表加了一把大锁，多线程访问时候，
     只要有一个线程访问或操作该对象，那其他线程只能阻塞，相当于将所有的操作串行化，在竞争激烈的并发场景中性能就会非常差。

     ConcurrentHashMap不允许key或value为null值
     ConcurrentHashMap采用了非常精妙的"分段锁"策略，ConcurrentHashMap的主干是个Segment数组。
     Segment继承了ReentrantLock，所以它就是一种可重入锁（ReentrantLock)。在ConcurrentHashMap，一个Segment就是一个子哈希表，
     Segment里维护了一个HashEntry数组，并发环境下，对于不同Segment的数据进行操作是不用考虑锁竞争的。（就按默认的ConcurrentLeve为16来讲，
     理论上就允许16个线程并发执行，有木有很酷）
     所以，对于同一个Segment的操作才需考虑线程同步，不同的Segment则无需考虑。

     ConcurrentHashMap作为一种线程安全且高效的哈希表的解决方案，尤其其中的"分段锁"的方案，相比HashTable的全表锁在性能上的提升非常之大
     */


    /**
     想要避免hashMap线程安全的问题可以改用HashTable或Collections.synchronisedMap。但这两者性能比价低，无论是读操作还是写操作，都是给整个集合加锁，
     导致同一时间其他的操作阻塞。在并发情况下，兼顾性能安全和运行效率，concurrentHashMap
     Segment本身就相当于一个HashMap对象。
     同HashMap一样，Segment包含一个HashEntry数组，数组中的每一个HashEntry既是一个键值对，也是一个链表的头节点
     ConcurrentHashMap集合中有2的N次方个Segment对象，共同保存在一个名为segments的数组当中
     可以说，ConcurrentHashMap是一个二级哈希表。在一个总的哈希表下面，有若干个子哈希表。

     ConcurrentHashMap优势是采用了锁分段技术，每一个segment就好比一个自治区，读写操作高度自治，Segment之间互不影响
     ConcurrentHashMap当中每个Segment各自持有一把锁。在保证线程安全的同时降低了锁的粒度，让并发操作效率更高

     ConcurrentHashMap读写过程：
     Get方法：
     1.为输入的Key做Hash运算，得到hash值。
     2.通过hash值，定位到对应的Segment对象
     3.再次通过hash值，定位到Segment当中数组的具体位置。

     Put方法：
     1.为输入的Key做Hash运算，得到hash值。
     2.通过hash值，定位到对应的Segment对象
     3.获取可重入锁
     4.再次通过hash值，定位到Segment当中数组的具体位置。
     5.插入或覆盖HashEntry对象。
     6.释放锁。
     可以看出concurrentHashMap读写都是需要二次定位。首先定位到Segment，然后定位到Segment内的具体数组下标
     */


    /**
     既然每一个Segment都是各自加锁，在调用size方法的时候怎么解决一致性的问题？
     Size方法的目的是统计ConcurrentHashMap的总元素数量，自然需要把各个Segment内部的元素数量汇总起来。
     但是，如果在统计Segment元素数量的过程中，已统计过的Segment瞬间插入新的元素，这时候该怎么办呢？

     ConcurrentHashMap的Size方法是一个嵌套循环，大体逻辑如下：
     1.遍历所有的Segment。
     2.把Segment的元素数量累加起来。
     3.把Segment的修改次数累加起来。
     4.判断所有Segment的总修改次数是否大于上一次的总修改次数。如果大于，说明统计过程中有修改，重新统计，尝试次数+1；如果不是。说明没有修改，统计结束。
     5.如果尝试次数超过阈值，则对每一个Segment加锁，再重新统计。
     6.再次判断所有Segment的总修改次数是否大于上一次的总修改次数。由于已经加锁，次数一定和上次相等。
     7.释放锁，统计结束。

     这种思想和乐观锁悲观锁的思想如出一辙。
     为了尽量不锁住所有Segment，首先乐观地假设Size过程中不会有修改。当尝试一定次数，才无奈转为悲观锁，锁住所有Segment保证强一致性。
     */

    /**
     * HashMap的核心数据结构就是链表。在ConcurrentHashMap中就不一样了，如果链表的数据过长是会转换为红黑树来处理。当它并不是直接转换，
     * 而是将这些链表的节点包装成TreeNode放在TreeBin对象中，然后由TreeBin完成红黑树的转换
     */
}
