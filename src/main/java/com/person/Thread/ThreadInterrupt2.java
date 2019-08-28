package com.person.Thread;

import java.util.concurrent.TimeUnit;

/**
 * 分析：
 *
 *    01： 当前线程非阻塞状态下，使用interrupt方法进行中断，当前线程则不会响应继续执行，必须手动进行判断是否中断且使用代码结束当前线程执行的业务；
 *
 *    02：非阻塞转台下使用interrupt方法不会导致中断状态复位；
 *
 *
 */
public class ThreadInterrupt2 {

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread() {

            @Override
            public void run() {
                while (true) {
                    //判断当前线程是否被中断
                    if(this.isInterrupted()){

                        System.out.println("当前线程未被中断");
                        break;
                    }
                }
                System.out.println("当前线程已被中断");
            }
        };
        t1.start();
        TimeUnit.SECONDS.sleep(2);
        t1.interrupt();
    }
}
