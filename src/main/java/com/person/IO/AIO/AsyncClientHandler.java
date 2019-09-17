package com.person.IO.AIO;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

public class AsyncClientHandler implements Runnable, CompletionHandler<Void,AsyncClientHandler> {

    private String address;
    private int port;
    private static AsynchronousSocketChannel channel;
    private  static CountDownLatch latch;
    public AsyncClientHandler(String address, int port) throws IOException {
        this.address = address;
        this.port = port;
        //创建异步客户端通道
        channel = AsynchronousSocketChannel.open();
    }

    @Override
    public void run() {
        latch = new CountDownLatch(1);
        //发起异步连接操作，回调参数就是这个类本身，如果连接成功会回调completed方法
        channel.connect(new InetSocketAddress(address,port),this,this);
        try {
            latch.await();
            channel.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendMsg(String msg) {
        byte[] bytes = msg.getBytes();
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        buffer.put(bytes);
        buffer.flip();
        channel.write(buffer,buffer,new ClientWriteHandler(channel,latch));
    }

    @Override
    public void completed(Void result, AsyncClientHandler attachment)
    {
        System.out.println("客户端成功连接到服务器……");
    }

    @Override
    public void failed(Throwable exc, AsyncClientHandler attachment)
    {
        System.out.println("连接服务器失败……");
        exc.printStackTrace();
        try {
            channel.close();
            latch.countDown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
