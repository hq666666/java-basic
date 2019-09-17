package com.person.Thread;

import java.util.concurrent.TimeUnit;

/**
 * 测试main方法：
 *
 *     main方法就是一个java应用的入口，main方法被执行了，java虚拟机就启动了(即执行一个main，就有一个java虚拟机的实例)，
 *
 *     从测试得出以下结论：
 *
 *          java虚拟机有两种线程：守护线程和非守护线程
 *
 *               当main方法生命周期以及结束时，只要有非守护线程还在运行，则java虚拟机实例就不会退出；
 *
 *
 */
public class ThreadTest {

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println("睡了5s后打印,这是出main之外的非守护线程，这个推出后这个引用结束，jvm声明周期结束。任务管理的java/javaw.exe进程结束");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(10);
                    System.out.println("睡了10s后打印,这是出main之外的非守护线程，这个推出后这个引用结束，jvm声明周期结束。任务管理的java/javaw.exe进程结束");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        //将线程设置为守护线程
        t1.setDaemon(true);
        t1.start();
        System.out.println("mian线程直接打印，mian线程结束，电脑任务管理器的java/javaw.exe进程并没有结束。");
    }
}
