package com.person.test;

import com.person.Thread.ThreadExample;
import com.person.Thread.ThreadExample2;
import org.junit.Test;

public class ThreadTests {

    /**
     * synchronized修饰实例方法的测试：
     *
     *          从测试得出以下结论：
     *
     *                  01：synchronized修饰实例方法必须获取对应的实例对象(多线程中参数建议使用final修饰以保证安全性)；
     *                  02：线程的start方法是线程一切方法的开始方法(只有start方法运行，其他方法才有效)；
     *                  03：线程的join方法通过此方法可以让当前线程运行，阻塞其他线程，直到该线程运行完，其他线程才有竞争资格；
     *                  04：一切非原子性共享变量的操作，在多线程环境下都可能出现线程安全问题，这事建议使用锁机制(比如：synchronized等)；
     *                  05:注意：
     *
     *                       在多线程下，所有线程都是以竞争的形式，所以你不知当前的线程的执行顺序，当业务需求指定的一个线程必须在那一步骤先执行
     *                     可以通过join方法使其完成该线程的任务；
     * 注意：由于重排序功能，所以在多线程的环境下，执行的顺序不一定一致，最后的结果也不一定跟预想的结果一致；
     * @throws InterruptedException
     */


    @Test
    public void test1() throws InterruptedException {

      ThreadExample example = new ThreadExample();
      //注意：若非同一实例对象，则synchronized同步机制则无效果；
      //ThreadExample example1 = new ThreadExample();

      Thread t1 = new Thread(example);
      Thread t2 = new Thread(example);

      t1.start();
      t2.start();
      //通过join方法会让当前线程执行其它线程阻塞(使用join方法通常是代码执行顺序，以保证最后的正确结果)
      t1.join();
      t2.join();
      System.out.println(ThreadExample.i);//2000000 原因分析：有join方法阻塞了其他线程导致一个线程跑了1000000遍，总共2个线程所以为2000000
    }

    /**
     * synchronized修饰静态方法的测试:
     *
     *          从测试得出以下结论：
     *
     *                  01：synchronized修饰静态方法的锁对象是对应class对象(一个类有且只有一个class对象)，无关实例对象；
     *                  02：若调用synchronized修饰的实例方法，则synchronized则没有对应的效果；(建议看一下synchronized修饰的实例方法的锁对象要求)
     *
     * @throws InterruptedException
     */
    @Test
    public void test2() throws InterruptedException {

        Thread t2 = new Thread(new ThreadExample());
        Thread t1 = new Thread(new ThreadExample());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(ThreadExample.i);
    }

    /**
     * synchronized修饰代码块：
     *
     *      从测试得出以下结论：
     *
     *              01:synchronized修饰代码块锁的粒度小了，更加精准；
     *              02：建议使用class级锁对象；
     *              03：注意：
     *
     *                      局部变量与成员变量相同时，注意局部变量会覆盖成员变量(使用this进行区分)
     *              04:使用this锁对象：
     *
     *                      01：当前的锁对象与synchronized修饰实例方法类似有且只有一个锁对象，多个锁对象则synchronized失效；
     * @throws InterruptedException
     */
    @Test
    public void test3() throws InterruptedException {

        ThreadExample example = new ThreadExample();
        Thread t2 = new Thread(example);
        Thread t1 = new Thread(example);
        t2.start();
        t1.start();
        t1.join();
        t2.join();
        System.out.println(ThreadExample.i);
    }

    /**
     * synchronized可重入性：
     *
     *      从测试得出以下结论：
     *
     *              01：当一个线程已持有该对象锁，在访问其synchronized修饰的实例方法，在此方法中再次请求该对象锁的实例方法是允许的，也叫锁的可重入性；
     * @throws InterruptedException
     */
   @Test
    public void test4() throws InterruptedException {

       ThreadExample2 example2 = new ThreadExample2();
       Thread t1 = new Thread(example2);
       Thread t2 = new Thread(example2);
       t1.start();
       t2.start();
       t1.join();
       t2.join();
       System.out.println(example2.i+"============="+example2.j);
       System.out.println(example2.i==example2.j);
   }
}
