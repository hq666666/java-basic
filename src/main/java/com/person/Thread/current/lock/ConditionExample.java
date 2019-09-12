package com.person.Thread.current.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition使用案例-生产者与消费者模式:
 *
 *     卖烤鸭案例：
 *          t1、t2线程生产烤鸭；
 *          t3、t4线程消费烤鸭
 */
public class ConditionExample {

    //产品名称
    private String name;
    //库存
    private int count =1;
    //线程是否等待标识
    private boolean flag =false;
    //实例化锁
    Lock lock = new ReentrantLock();
    //通过已有的锁获取监视器：生产与消费
    Condition producer = lock.newCondition();
    Condition consumer = lock.newCondition();

    /**
     * 生产
     * @param name
     */
    public  void  product(String name){
       lock.lock();
       try {
         while (flag){
             try {
                 producer.await();
             } catch (InterruptedException e) {
                 System.out.println(Thread.currentThread().getName()+"线程进入等待状态");
             }
         }
         this.name = name+count;
         count++;
           System.out.println(Thread.currentThread().getName()+"生产者"+this.name);
           flag =true;
           //唤醒消费线程
           consumer.signal();
       } finally {
            lock.unlock();
       }
    }

    /**
     * 消费
     */
    public void consume(){
        lock.lock();
        try {
            while (!flag){
                try {
                    consumer.await();
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName()+"线程进入等待状态");
                }
            }
          count--;
          flag=false;
            System.out.println(Thread.currentThread().getName()+"消费者"+this.name);
            //唤醒生产线程
            producer.signal();
        }finally {
            lock.unlock();
        }
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
