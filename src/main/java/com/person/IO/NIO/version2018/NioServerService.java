package com.person.IO.NIO.version2018;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NioServerService  implements Runnable{
    private static Selector selector;
    private static ServerSocketChannel serverSocketChannel;
    private static  final int count = Runtime.getRuntime().availableProcessors();
    private static ExecutorService service = Executors.newFixedThreadPool(count*2);
    private  volatile boolean started;


    public NioServerService (int defaultPort) {
        try {
            //创建selector实例
            selector = Selector.open();
            //创建serverSocketChannel实例
            serverSocketChannel =  ServerSocketChannel.open();
            //设置非阻塞模式
            serverSocketChannel.configureBlocking(false);
            //serverSocketChannel绑定指定的端口，并设置挂起挂起连接的最大数目
            serverSocketChannel.bind(new InetSocketAddress(defaultPort),1024);
            //serverSocketChannel注册，并设置指定的操作标识 SelectionKey.OP_ACCEPT：接受
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            started =true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            //异常退出
            System.exit(1);
        }
    }

    @Override
    public void run() {
        while (started){

            try {
                //轮询注册该select的socketChannel，返回键的数目
                int events = selector.select();
                if(events>0){
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> its = keys.iterator();
                    SelectionKey key = null;
                    while (its.hasNext()){
                        key = its.next();
                        //清除元素
                        its.remove();
                        //判断key是否有效
                        if(key.isValid()){
                            //测试此密钥的通道是否准备好接受新的套接字连接
                            if(key.isAcceptable()){
                                accept(key);
                            }
                            if(key.isReadable()){
                                service.submit(new NioSeverHandler(key));
                            }

                        }


                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //关闭selector，将会释放所有资源
        if(selector != null){
            try {
                selector.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void accept(SelectionKey key) {
        //创建ServerSocketChannel实例
        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            try {
            //获取SocketChannel实例
            SocketChannel sc = ssc.accept();
            //设置非阻塞模式
            sc.configureBlocking(false);
            //注册为可读标识
            sc.register(selector,SelectionKey.OP_READ);
            System.out.println("接受的client端的连接："+sc.socket().getInetAddress().getHostName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        started =false;
    }
}
