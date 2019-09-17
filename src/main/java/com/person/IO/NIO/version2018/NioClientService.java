package com.person.IO.NIO.version2018;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioClientService implements Runnable {
        private static Selector selector;
        private static SocketChannel channel;
    public NioClientService(String adress,int port){
        try {
            //创建selector实例
            selector = Selector.open();
            //创建SocketChannel实例
            channel = SocketChannel.open();
            //设置channel非阻塞模式
            channel.configureBlocking(false);
            //注册并设置为连接操作；
            channel.register(selector, SelectionKey.OP_CONNECT);
            //连接该通道的socke；
            channel.connect(new InetSocketAddress(adress,port));
        } catch (IOException e) {
           System.exit(1);
        }
    }
    @Override
    public void run() {
        while (true){
            try {
                //无限轮询注册在该selector上的channel发生IO操作；
                int selectKeys = selector.select();
                if(selectKeys >0){
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> ites = keys.iterator();
                    SelectionKey key = null;
                    while (ites.hasNext()){
                        key = ites.next();
                        ites.remove();
                        if(key.isConnectable()){
                            connect(key);
                        }
                        System.out.println("可以阅读前一步");


                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void read(SelectionKey key) throws IOException {
        if(key.isReadable()){
            //创建SocketChannel实例
            System.out.println("可以阅读了");
            SocketChannel sc = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            sc.read(buffer);
            buffer.flip();
            //表示当前position和limit之间是否有任何元素
            while (buffer.hasRemaining()){
                System.out.println((char) buffer.get());
            }

        }
    }

    private void connect(SelectionKey key) throws IOException {
        SocketChannel sc = (SocketChannel) key.channel();
        //该通道是否正在连接操作
        if (sc.isConnectionPending()) {
            //完成连接操作
            sc.finishConnect();
        }
        sc.configureBlocking(false);
        //注册并设置为阅读模式
        sc.register(selector,SelectionKey.OP_READ);
        //将消息法发向服务端
        ByteBuffer outBuffer = ByteBuffer.wrap(("Hello this is " + Thread.currentThread().getName()).getBytes());
        sc.write(outBuffer);
        System.out.println("haha");
        sc.read(outBuffer);
        //将写模式转换成读模式
        outBuffer.flip();
        while(outBuffer.hasRemaining())
        System.out.print((char) outBuffer.get()+"\t");
    }
}
