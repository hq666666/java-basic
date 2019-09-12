package com.person.Thread.current.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 设置Sesmaphore初始哈信号量为1，已达到互斥的作用；
 */
public class SemaphoreMutex {

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newCachedThreadPool();

        final Semaphore semaphore = new Semaphore(1);
        for(int i =0;i<20;i++){
            final  int id = i;
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        System.out.println(Thread.currentThread().getName()+"==="+id);
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        System.out.println("进入等待状态");
                    }finally {
                        semaphore.release();
                    }
                }
            };
            threadPool.execute(run);
        }
        threadPool.shutdown();

    }
}
