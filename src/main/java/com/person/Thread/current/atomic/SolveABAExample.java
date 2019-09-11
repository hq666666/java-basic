package com.person.Thread.current.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * ABA问题：
 *      使用AtomicInteger与AtomicStampRefrence做对比
 */
public class SolveABAExample {
    /**
     * 实例化：
     *     01： AtomicInteger：
     *              参数：该数值
     *      02：AtomicStampedReference：
     *              参数1：该数值
     *              参数2：当前时间戳；
     */
    static AtomicInteger atIn =  new AtomicInteger(100);

    static AtomicStampedReference<Integer> atSr = new AtomicStampedReference<>(200,0);

    /**
     * 使用线程模拟操作AtomicInteger类型
     */
   static Thread t1 = new Thread(new Runnable() {
        @Override
        public void run() {
            //将当前变量修改成200；
            atIn.compareAndSet(100,200);
            //将当前变量修改成100；
            atIn.compareAndSet(200,100);
        }
    });
    static Thread t2 = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.out.println("线程睡眠阻塞");
            }
            boolean flag = atIn.compareAndSet(100, 500);
            System.out.println("修改是否成功"+flag+"新值"+atIn);
        }
    });
    static Thread t3 = new Thread(new Runnable() {
        @Override
        public void run() {
            int time = atSr.getStamp();
            Integer reference1 = atSr.getReference();
            //将当前变量修改成500，添加修改时间；
            boolean flag = atSr.compareAndSet(reference1, 500, time, time + 1);
            System.out.println(flag);//true

            int time2 = atSr.getStamp();
            Integer reference2 = atSr.getReference();
            //将当前变量修改成200，添加修改时间
            boolean flag2 = atSr.compareAndSet(reference2, 200, time2, time2 + 1);
            System.out.println(flag2);//true
        }
    });


    static Thread t4 = new Thread(new Runnable() {
        @Override
        public void run() {
            int time3 = atSr.getStamp();
            Integer reference = atSr.getReference();
            System.out.println("sleep 前 t4 time:"+time3+"当前线程名称："+Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean flag=atSr.compareAndSet(reference,500,time3,time3+1);
            System.out.println(flag);//false reason:睡眠了1S导致时间与之不匹配；
        }
    },"t4");

    public static void main(String[] args) throws InterruptedException {
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        t3.start();
        t4.start();
        t3.join();
        t4.join();
    }


}
