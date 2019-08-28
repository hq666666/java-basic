package com.person.Thread;

public class ThreadExample implements Runnable{

    public  static ThreadExample example = new ThreadExample();
    public static int i =0;

    private static  synchronized  void staticIncrease(){
        i++;
    }
    private synchronized void  increase(){
        i++;
    }

    public void run() {
        //建议使用class锁对象；
         synchronized (this){
            for(int i=0;i<1000000;i++){
                // increase();
                //staticIncrease();
                this.i++;
            }
        }

    }


}
