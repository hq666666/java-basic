package com.person.Thread.current.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerExample  implements Runnable{

    public static AtomicInteger operation = new AtomicInteger();

    public static volatile  Integer result = 0;

    public void run() {
        synchronized (this){
            for(int i=0;i<10;i++){
                //info();
                operation.incrementAndGet();

            }
        }


        System.out.println(Thread.currentThread().getName()+"检测Atomic是否在多线程下可见"+operation);
        System.out.println(Thread.currentThread().getName()+"volatile关键字"+result);
    }

    private  void info() {
        if(operation.intValue() == 25){

            operation.decrementAndGet();
            System.out.println("减值操作"+operation);
        }else{
            for (int i=1;i<=5;i++){
                operation.incrementAndGet();

            }
        }
        System.out.println("价值操作"+operation);

    }

    public static void main(String[] args) throws InterruptedException {
        AtomicIntegerExample example = new AtomicIntegerExample();
        Thread[] threads = new Thread[100];
        for(int i=0;i<threads.length;i++)threads[i] = new Thread(example);
        for (int i=0;i<threads.length;i++)threads[i].start();
        for (int i=0;i<threads.length;i++)threads[i].join();
        System.out.println(operation);

        //通过addAndGet方法添加新值并获取该新值；
        operation.addAndGet(25);
        System.out.println(operation);

    }
}
