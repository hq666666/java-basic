package com.person.test;

import com.person.Thread.ThreadExample;
import com.person.Thread.ThreadExample2;
import com.person.Thread.current.lock.*;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;


public class ThreadTests {
        private final static Logger log = LoggerFactory.getLogger(ThreadTests.class);
    /**
     * synchronized修饰实例方法的测试：
     *
     *          从测试得出以下结论：
     *
     *                  01：synchronized修饰实例方法必须获取对应的实例对象(多线程中参数建议使用final修饰以保证安全性)；
     *                  02：线程的start方法是线程一切方法的开始方法(只有start方法运行，其他方法才有效)；
     *                  03：线程的join方法通过此方法可以让当前线程运行，阻塞其他线程，直到该线程运行完，其他线程才有竞争资格；
     *                  04：一切非原子性共享变量的操作，在多线程环境下都可能出现线程安全问题，这事建议使用锁机制(比如：synchronized等)；
     *                  05:注意：
     *
     *                       在多线程下，所有线程都是以竞争的形式，所以你不知当前的线程的执行顺序，当业务需求指定的一个线程必须在那一步骤先执行
     *                     可以通过join方法使其完成该线程的任务；
     * 注意：由于重排序功能，所以在多线程的环境下，执行的顺序不一定一致，最后的结果也不一定跟预想的结果一致；
     * @throws InterruptedException
     */


    @Test
    public void test1() throws InterruptedException {

      ThreadExample example = new ThreadExample();
      //注意：若非同一实例对象，则synchronized同步机制则无效果；
      //ThreadExample example1 = new ThreadExample();

      Thread t1 = new Thread(example);
      Thread t2 = new Thread(example);

      t1.start();
      t2.start();
      //通过join方法会让当前线程执行其它线程阻塞(使用join方法通常是代码执行顺序，以保证最后的正确结果)
      t1.join();
      t2.join();
      System.out.println(ThreadExample.i);//2000000 原因分析：有join方法阻塞了其他线程导致一个线程跑了1000000遍，总共2个线程所以为2000000
    }

    /**
     * synchronized修饰静态方法的测试:
     *
     *          从测试得出以下结论：
     *
     *                  01：synchronized修饰静态方法的锁对象是对应class对象(一个类有且只有一个class对象)，无关实例对象；
     *                  02：若调用synchronized修饰的实例方法，则synchronized则没有对应的效果；(建议看一下synchronized修饰的实例方法的锁对象要求)
     *
     * @throws InterruptedException
     */
    @Test
    public void test2() throws InterruptedException {

        Thread t2 = new Thread(new ThreadExample());
        Thread t1 = new Thread(new ThreadExample());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(ThreadExample.i);
    }

    /**
     * synchronized修饰代码块：
     *
     *      从测试得出以下结论：
     *
     *              01:synchronized修饰代码块锁的粒度小了，更加精准；
     *              02：建议使用class级锁对象；
     *              03：注意：
     *
     *                      局部变量与成员变量相同时，注意局部变量会覆盖成员变量(使用this进行区分)
     *              04:使用this锁对象：
     *
     *                      01：当前的锁对象与synchronized修饰实例方法类似有且只有一个锁对象，多个锁对象则synchronized失效；
     * @throws InterruptedException
     */
    @Test
    public void test3() throws InterruptedException {

        ThreadExample example = new ThreadExample();
        Thread t2 = new Thread(example);
        Thread t1 = new Thread(example);
        t2.start();
        t1.start();
        t1.join();
        t2.join();
        System.out.println(ThreadExample.i);
    }

    /**
     * synchronized可重入性：
     *
     *      从测试得出以下结论：
     *
     *              01：当一个线程已持有该对象锁，在访问其synchronized修饰的实例方法，在此方法中再次请求该对象锁的实例方法是允许的，也叫锁的可重入性；
     * @throws InterruptedException
     */
   @Test
    public void test4() throws InterruptedException {

       ThreadExample2 example2 = new ThreadExample2();
       Thread t1 = new Thread(example2);
       Thread t2 = new Thread(example2);
       t1.start();
       t2.start();
       t1.join();
       t2.join();
       System.out.println(example2.i+"============="+example2.j);
       System.out.println(example2.i==example2.j);


   }

    /**
     * 测试ReentrantLock：
     *
     *      测试重入锁
     */
    @Test
   public void test5() throws InterruptedException {

       ReentrantLockExample example = new ReentrantLockExample();
       Thread t1 = new Thread(example,"t1");
       Thread t2 = new Thread(example,"t2");
       t1.start();
       t2.start();
       t1.join();
       t2.join();
       System.out.println(example.result);
   }

    /**
     * 消费者与生产者模式:
     *
     *      使用condition。规则生产一个，消费一个
     */
   @Test
   public void test6() throws InterruptedException {
      final ConditionExample example = new ConditionExample();
      final   MultiProducer producer = new MultiProducer(example);
      final MultiConsumer consumer = new MultiConsumer(example);

      //生产线程
       Thread t1 = new Thread(producer,"Thread-1");
       Thread t2 = new Thread(producer,"Thread-2");
       //消费线程
       Thread t3 = new Thread(consumer,"Thread-3");
       Thread t4 = new Thread(consumer,"Thread-4");
       //线程启动
       t1.start();
       t2.start();
       t3.start();
       t4.start();
       t1.join();
       t2.join();
       t3.join();
       t4.join();
       System.out.println(example.getCount()+example.getName());
   }

    /**
     * 生产者-消费者模式：
     *  synchronized:
     *
     *
     */
   @Test
   public void test7() throws InterruptedException {
       SynchronizedExample example = new SynchronizedExample();

       MultiProducer producer = new MultiProducer(example);
       MultiConsumer consumer = new MultiConsumer(example);
       //生产线程
       Thread t1 = new Thread(producer, "thread-1");
       Thread t2 = new Thread(producer,"thread-2");
       //消费线程
       Thread t3 =new Thread(consumer,"thread-3");
       Thread t4 = new Thread(consumer,"thread-4");

       //线程启动
       t1.start();
       t2.start();
       t3.start();
       t4.start();
       //当前线程执行并阻塞其他线程
       t1.join();
       t2.join();
       t3.join();
       t4.join();
       System.out.println(example.getName()+example.getInventoryNum());
   }

    /**
     * 普通线程池创建:ThreadPoolExecutor
     *
     * @param1 corePoolSize: 核心线程数，一旦创建将不会释放
     * @param2 maximumPoolsize: 最大线程数，允许创建最大线程数量，如果最大线程数等于核心线程数，则无法创建非核心线程；如果非核心线程处于空闲时，超过设置时间，则将会被回收，释放占用的资源；
     * @param3 keepAliveTime 空闲线程的存活时间；
     * @param4 unit :时间单位(空闲线程存活时间单位)
     * @param5 workQueue:任务队列，存储暂时无法执行的任务，等待空闲线程来执行任务；
     * @param6 threadFactory:线程工厂，用于创建线程
     * @param7 handler：当线程边界和队列容量以及达到最大是，用于处理阻塞的程序；
     */
   @Test
   public void test8(){
       ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5,10,300l,TimeUnit.SECONDS,new LinkedBlockingDeque<>(512), Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
       for(int i =0;i<20;i++){

           poolExecutor.execute(()->{
               for(int j=0;j<100;j++){
                   System.out.println("第"+j+"组"+Thread.currentThread().getName()+"线程正在执行");
               }
           });
           System.out.println(poolExecutor.getActiveCount());
           System.out.println(poolExecutor.getQueue());
       }
   }

    /**
     * 均分策略
     */
   @Test
   public void test9(){
       Map<Integer, Long> maps = new HashMap<>();
       int threadNum = Runtime.getRuntime().availableProcessors();
       for(int i=0;i<100;i++){
           //采用取余的方式从而均匀的分配循环的数量；
           int j = i%threadNum;
           if (maps.containsKey(j)) {
               maps.put(j, maps.get(j) + 1);
           } else {
               maps.put(j, 1L);
           }
       }
     for(Map.Entry<Integer,Long> map :maps.entrySet()){
         System.out.println(map.getKey()+"============"+map.getValue());
     }
   }
   @Test
   public void test10() throws ExecutionException, InterruptedException, TimeoutException {

       ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(5);
       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       System.out.println(format.format(new Date()));
       ScheduledFuture<?> future = threadPool.schedule(new Runnable() {
           @Override
           public void run() {
               System.out.println("定时延迟执行" + format.format(new Date()));
           }
       }, 2l, TimeUnit.SECONDS);
       Object o = future.get(2,TimeUnit.SECONDS);
       boolean done = future.isDone();
       boolean cancelled = future.isCancelled();
       System.out.println(String.valueOf(o));
       System.out.println(done+"==="+ cancelled);
       /*threadPool.scheduleAtFixedRate(new Runnable() {
           @Override
           public void run() {
               System.out.println("定时延迟周期循环执行"+format.format(new Date()));
           }
       },2l,3,TimeUnit.SECONDS);*/

       /* threadPool.scheduleWithFixedDelay(new Runnable() {
           @Override
           public void run() {
               System.out.println("定时延迟周期性延时循环执行" + format.format(new Date()));
           }
       }, 2l, 4, TimeUnit.SECONDS);*/
   }

    /**
     * 使用C
     * @throws ExecutionException
     */
   @Test
   public void test11() throws ExecutionException {
       int threadNum = Runtime.getRuntime().availableProcessors();
       ExecutorService service = Executors.newFixedThreadPool(threadNum);
       List<Callable<Long>> tasks = new ArrayList<>();
       Callable<Long> task = new Callable<Long>() {
           @Override
           public Long call() throws Exception {
               Long result = 0l;
               for (int i = 0; i < 1000; i++) {
                   result = create(i);
               }
               return result;
           }
       };
      tasks.add(task);
       try {
           List<Future<Long>> all = service.invokeAll(tasks);
           Iterator<Future<Long>> iterator = all.iterator();
           while (iterator.hasNext()){
               System.out.println(iterator.next().get());
           }
       } catch (InterruptedException e) {
            log.warn("任务线程执行程序异常");
       }finally {
           //关闭线程池，并释放资源
           service.shutdown();
           service = null;
       }
   }

    private Long create(Integer param) {
        String name = Thread.currentThread().getName();
        System.out.println(name+"线程执行次数"+param);
        return Long.valueOf(param);
    }

    /**
     * 测是callable任务的执行
     */
    @Test
    public void test12(){
        //获取处理器数量
        int processors = Runtime.getRuntime().availableProcessors();
        //创建线程池(建议正常使用处理器个数或者两倍数量作为开启的线程数)
        final  ExecutorService service = Executors.newFixedThreadPool(processors*2);
        List<Callable<Integer>> list = new ArrayList<>();
        Callable<Integer> hello =null;
       for(int i =0;i<10;i++){
            //创建callable的任务
           hello = Executors.callable(new Runnable() {
               @Override
               public void run() {
                   System.out.println(Thread.currentThread().getName()+"hello");
               }
           }, 2);
           list.add(hello);
       }
        try {
           // List<Future<Integer>> results = service.invokeAll(list);
            Integer integer = service.invokeAny(list);
            System.out.println(integer);
            /*results.forEach((j)->{
                boolean cancel = j.cancel(true);
                boolean falg = j.isCancelled();
                System.out.println(cancel);
                System.out.println(falg);
            });*/
           // System.out.println(results.size());
           /* results.forEach((k)->{
                try {
                    Integer integer = k.get();
                    System.out.println(integer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            });*/
        } catch (InterruptedException | ExecutionException e) {
            log.error("线程中断","{}",list);
        }
            service.shutdown();


    }

    /**
     * FutureTask使用
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void test13() throws ExecutionException, InterruptedException, IOException {
        final int count = Runtime.getRuntime().availableProcessors();
        ExecutorService service = Executors.newFixedThreadPool(count * 2);
        FutureTask<String> task = null;
             task = new FutureTask<>(() -> {
                System.out.println("apple");
            }, "success");
        //任务的取消：若在任务执行之前使用cancel方法可以取消任务，使之之后的任务将无法执行下去
        /*boolean cancel = task.cancel(true);
        System.out.println(cancel);*/
        //任务的执行:使用FutureTask类的run方法进行执行
        //task.run();
        //使用多线程执行任务
        service.submit(task);
        System.out.println(task.get());


    }

}
