package com.person.Thread.current.queue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * ArrayBlockingQueue：
 *          数组式阻塞队列；
 *              01：设置该队列的容量，当到达指定的容量则会阻塞添加元素的线程，
 *              直到该队列不满最大容量(即移出该队列的元素)，才会允许添加元素的线程进行添加操作；
 *
 *              02：可以通过take移出队列中的元素
 */
public class ArrayBlockingQueueExample {

    //private static ArrayBlockingQueue<App> queue = new ArrayBlockingQueue<>(16);
     private static LinkedBlockingQueue<App> queue = new LinkedBlockingQueue<>(16);
    public static void main(String[] args) {
        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue);
        new Thread(producer).start();
        new Thread(producer).start();
        new Thread(consumer).start();
        new Thread(consumer).start();
        Iterator<App> iterators = queue.iterator();
        while (iterators.hasNext()){
            System.out.println(iterators.next());
        }
        System.out.println(queue.size());
        List<App> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            list.add(new App());
        }
        //将队列中的可用元素添加到list中;
        int i = queue.drainTo(list);
        System.out.println(list.size());//26
        System.out.println(queue.element());
        int result = queue.remainingCapacity();
        System.out.println(result);
    }
   static  class App{
        public App(){

        }
   }
   static class Producer implements Runnable{
       private ArrayBlockingQueue<App> producer;
       private LinkedBlockingQueue<App> queue;
       public Producer(ArrayBlockingQueue<App> queue){
           this.producer = queue;
       }
       public Producer(LinkedBlockingQueue<App>  queue){
           this.queue = queue;
       }
       @Override
       public void run() {
           while (true)
               product();
       }
       private void product() {
           App app = new App();
           try {
               //producer.put(app);
               queue.put(app);
               System.out.println("生产"+app);
           } catch (InterruptedException e) {
               System.out.println("线程进入中断状态");
           }
       }
   }

   static class Consumer implements  Runnable{

       private ArrayBlockingQueue<App> consumer;
       private LinkedBlockingQueue<App> queue;
       public  Consumer(ArrayBlockingQueue<App> consumer){
           this.consumer = consumer;
       }
       public  Consumer(LinkedBlockingQueue<App> queue){
           this.queue = queue;
       }
       @Override
       public void run() {
            while (true){
                try {
                    TimeUnit.SECONDS.sleep(1);
                    consume();
                } catch (InterruptedException e) {
                    System.out.println("线程进入睡眠等待状态");
                }
            }

       }

       private void consume() throws InterruptedException {
          // App app = consumer.take();
           App app = queue.take();
           System.out.println("消费apple："+app);
       }


   }
}
