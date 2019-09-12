package com.person.Thread.current.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 使用SemaphoreTest：
 *
 *      测试得出以下结论：
 *
 *              通过指定semaphore数量值，可以控制在同一时间可以访问共享资源的访问数量；
 */
public class SemaphoreTest {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(5);
        for(int index = 0;index<20;index++){

            final int number = index;
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    try {
                        //获取锁
                        semaphore.acquire();
                        System.out.println("Accessing"+number);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        System.out.println("线程进入等待状态");
                    }finally {
                        //释放锁
                        semaphore.release();
                    }
                }
            };
            pool.execute(run);
        }
        //退出线程池
        pool.shutdown();
    }
}
