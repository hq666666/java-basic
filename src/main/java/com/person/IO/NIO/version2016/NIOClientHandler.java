package com.person.IO.NIO.version2016;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOClientHandler  implements  Runnable{

    private String address;
    private int port;
    private Selector selector;
    private SocketChannel socketChannel;
    private volatile  boolean started;

    public NIOClientHandler(String address,int port)
    {
        this.address = address;
        this.port = port;
        try {
            //创建select实例
            selector =Selector.open();
            //创建socketChannel
            socketChannel = SocketChannel.open();
            //设置非阻塞模式
            socketChannel.configureBlocking(false);
            //设置服务器开启标识
            started =true;
        } catch (IOException e) {
            e.printStackTrace();
            //异常退出
            System.exit(1);
        }
    }
    public void stop(){
        started = false;
    }

    @Override
    public void run() {
        try {
            //发送连接操作
            doConnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //判断服务器是否开启
        while (started){
            try {
                //每间隔1s轮询在该select注册的channel是否有IO操作，true则获取
                selector.select(1000);
                //获取所有注册在selector发生IO操作的channel
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> its = keys.iterator();
                SelectionKey key = null;
                while (its.hasNext()){
                    key = its.next();
                    its.remove();
                    try {
                        handleInput(key);
                    } catch (Exception e) {
                        if(key != null){
                            key.cancel();
                            if(key.channel() != null){
                                key.channel().close();
                            }
                        }

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        if(selector != null){
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        //判断key是否有效
        if (key.isValid()) {
            //创建socketChannel实例
            SocketChannel sc = (SocketChannel) key.channel();
            //测试此密钥的通道是否已完成或未完成完成其套接字连接操作
            if (key.isConnectable()) {
                //判断是否完成连接套接字通道的过程
                if (sc.finishConnect()) ;
                else System.exit(1);
            }
            //读消息
            if (key.isReadable()) {
                //创建ByteBuffer，并开辟一个1M的缓冲区
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                //读取请求码流进入缓存区，返回读取到的字节数
                int readBytes = sc.read(buffer);
                //读取到字节，对字节进行编解码
                if (readBytes > 0) {
                    //将缓冲区当前的limit设置为position=0，用于后续对缓冲区的读取操作
                    buffer.flip();
                    //根据缓冲区可读字节数创建字节数组
                    byte[] bytes = new byte[buffer.remaining()];
                    //将缓冲区可读字节数组复制到新建的数组中
                    buffer.get(bytes);
                    String result = new String(bytes, "UTF-8");
                    System.out.println("客户端收到消息：" + result);
                }
                //没有读取到字节 忽略
//				else if(readBytes==0);
                //链路已经关闭，释放资源
                else if (readBytes < 0) {
                    key.cancel();
                    sc.close();
                }
            }
        }

    }

    private void doConnect() throws IOException {
        //连接连接此通道的套接字
        if(socketChannel.connect(new InetSocketAddress(address,port)));
        //将该socketChannel注册到指定selector，并设置为连接操作；SelectionKey.OP_CONNECT：表示连接操作
        else socketChannel.register(selector,SelectionKey.OP_CONNECT);
    }

    public void sendMsg(String msg) throws IOException {
        //开启阅读操作
        socketChannel.register(selector,SelectionKey.OP_READ);
        doWrite(socketChannel,msg);
    }

    private void doWrite(SocketChannel socketChannel, String request) throws IOException {
        //将消息编码为字节数组
        byte[] bytes = request.getBytes();
        //根据数组容量创建ByteBuffer
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        //将字节数组复制到缓冲区
        writeBuffer.put(bytes);
        //flip操作
        writeBuffer.flip();
        //发送缓冲区的字节数组
        socketChannel.write(writeBuffer);


    }
}
