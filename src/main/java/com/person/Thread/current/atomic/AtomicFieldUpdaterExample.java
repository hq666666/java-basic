package com.person.Thread.current.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class AtomicFieldUpdaterExample {

     static class Candita {
         int id;
         volatile int score;
     }
     static class Game{
         int id;
         volatile String name;

         public Game(int id, String name) {
             this.id = id;
             this.name = name;
         }

         @Override
         public String toString() {
             return "Game{" +
                     "id=" + id +
                     ", name='" + name + '\'' +
                     '}';
         }

     }

    static AtomicIntegerFieldUpdater intField = AtomicIntegerFieldUpdater.newUpdater(Candita.class,"score");
     static AtomicReferenceFieldUpdater referenceFieldUpdater = AtomicReferenceFieldUpdater.newUpdater(Game.class,String.class,"name");
     static AtomicInteger result = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
       final Candita candita = new Candita();
       Thread [] threads = new Thread[100];
        for(int i=0;i<100;i++){
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    if(Math.random()>0.4){
                        intField.incrementAndGet(candita);
                        result.incrementAndGet();
                    }
                }
            });
            threads[i].start();
            threads[i].join();
        }
        System.out.println("最终分数score="+candita.score);
        System.out.println("校验分数allScore="+result);

        Game game1 = new Game(1, "lh");
        referenceFieldUpdater.compareAndSet(game1,game1.name,"hq");
        System.out.println("游戏号:"+game1.id+"人物名称："+game1.name);
    }
}
