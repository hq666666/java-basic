package com.person.Thread.current.atomic;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicArrayExample {

    static AtomicIntegerArray param1 =  new AtomicIntegerArray(10);
  /* static class Demo implements Runnable{

       @Override
       public void run() {
           for(int k=0;k<1000;k++){
               int reuslt = k % param1.length();
               //执行incrementAndGet方法参数为当前数组的索引值从而给对应的索引进行操作获取最终的结果；
               param1.incrementAndGet(k%param1.length());
           }
       }
   }*/
    public static void main(String[] args) throws InterruptedException {
        /*Thread[] threads =  new Thread[10];
        for(int i =0;i<threads.length;i++){
            threads[i] = new Thread(new Demo());
        }
        for(int j=0;j<10;j++)threads[j].start();
        for(int j=0;j<10;j++)threads[j].join();
        System.out.println(param1);*/
        //通过getAndIncrement自增1，返回旧值，参数为索引值；
        int result1 = param1.getAndIncrement(1);
        //通过incrementAndGet自增1，返回新值，参数为索引值；
        int reuslt2 = param1.incrementAndGet(1);
        //通过decrementAndGet自减1，返回新值，参数为索引值；
        int reuslt3 = param1.decrementAndGet(1);
        //通过getAndAdd获取并添加至，返回旧值，参数1：为旧值参照值，参数二：为要添加的新值
        int result4 = param1.getAndAdd(1, 2);
        System.out.println(result1+"\t"+reuslt2);
        System.out.println(reuslt3);
        System.out.println(result4);
        int reuslt5 = param1.get(1);
        System.out.println(reuslt5);
    }
}
