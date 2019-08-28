package com.person.Thread;

import java.util.concurrent.TimeUnit;

/**
 * 分析：
 *
 *   01：  当一个线程处于阻塞状态或者试图执行一个阻塞操作是，使用interrupt方法中断该线程；
 *
 *   02：注意：
 *
 *                  此时将会抛出一个InterruptException的异常，同时中断状态将会被复位(由中断状态改为非中断状态)；
 */
public class ThreadInterrupt1 {

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(){


            @Override
            public void run() {

                    try {
                        while (true){
                            //线程睡眠以至于阻塞当前线程
                        TimeUnit.SECONDS.sleep(2);

                        }
                    } catch (InterruptedException e) {
                        System.out.println("interrupt when currentThread is sleep");
                        boolean flag = this.isInterrupted();
                        System.out.println("interruptState:"+flag);
                    }

            }
        };
        //线程启动
        t1.start();
        //线程睡眠2s
        TimeUnit.SECONDS.sleep(2);
        //线程中断
        t1.interrupt();
    }
}
