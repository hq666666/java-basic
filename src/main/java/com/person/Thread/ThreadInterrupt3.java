package com.person.Thread;

import java.util.concurrent.TimeUnit;


public class ThreadInterrupt3 {

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread() {

            @Override
            public void run() {

                try {
                    //判断当前线程是否已中断，执行后会对中断状态进行复位
                    while (!Thread.interrupted()){

                        TimeUnit.SECONDS.sleep(2);

                    }
                    System.out.println("当前线程已中断");
                } catch (InterruptedException e) {
                    System.out.println("当前线程被阻塞");
                    boolean flag = this.isInterrupted();
                    if (!flag) {

                        System.out.println("若为false表示中断状态已复位");
                    }
                }
            }
        };
        t1.start();
        TimeUnit.SECONDS.sleep(2);
        t1.interrupt();
    }
}
