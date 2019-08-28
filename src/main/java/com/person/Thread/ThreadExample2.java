package com.person.Thread;

public class ThreadExample2 implements Runnable{

    public int i=0;
    public int j=0;


    public void run() {
        for(int j=1;j<=1000000;j++){
            //this，即当前对象为对象锁
            synchronized (this){
                i++;
                //synchronized可重入性，该方法是有synchronized修饰的
                increase();
            }
        }
    }

    private synchronized void increase() {
        j++;
    }
}
