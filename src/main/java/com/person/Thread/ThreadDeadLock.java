package com.person.Thread;

/**
 * 分析：
 *
 *      死锁：
 *
 */
public class ThreadDeadLock {

    private synchronized void lock1() throws InterruptedException {
        //sleep(2000);
        lock2();
    }
    private synchronized  void lock2() throws InterruptedException {
        //sleep(2000);
        lock1();
    }

    public static void main(String[] args) throws InterruptedException {

        final ThreadDeadLock lock = new ThreadDeadLock();
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    lock.lock1();
                } catch (InterruptedException e) {
                    System.out.println("haha");
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                try {
                    lock.lock1();
                } catch (InterruptedException e) {
                    System.out.println("haha");
                }
            }
        });
        Thread.sleep(5000);
        t1.start();
        t2.start();
    }

}
