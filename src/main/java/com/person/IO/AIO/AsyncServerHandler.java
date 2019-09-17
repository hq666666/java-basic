package com.person.IO.AIO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

public class AsyncServerHandler implements Runnable {

    private int port;
    public CountDownLatch latch;
    //AsynchronousServerSocketChannel(异步服务端套接字通道)实例
    public AsynchronousServerSocketChannel channel;

    public AsyncServerHandler(int port) {
        this.port = port;
        init();
    }

    @Override
    public void run() {
        latch = new CountDownLatch(1);
        channel.accept(this,new ServerAcceptHandler());
    }

    private void init() {
        try {
            //创建服务端通道
            channel = AsynchronousServerSocketChannel.open();
            //绑定端口
            channel.bind(new InetSocketAddress(port));
            System.out.println("服务器已启动，端口号："+port);
        } catch (IOException e) {
            System.out.println();
        }
    }
}
