package com.person.IO.AIO;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class ServerReadHandler implements CompletionHandler<Integer, ByteBuffer> {

    //用于读取半包消息和发送应答
    private AsynchronousSocketChannel channel;
    private static  final  String msg = "你好我是服务端";
    public ServerReadHandler(AsynchronousSocketChannel channel) {
        this.channel = channel;
    }


    @Override
    public void completed(Integer result, ByteBuffer attachment) {
        //将写模式转换成读模式
        attachment.flip();
        //通过remaining方法获取缓冲区可读的字节数
        byte[] bytes = new byte[attachment.remaining()];
        while(attachment.hasRemaining())
            System.out.println(attachment.get(bytes));;
        try {
            String express = new String(bytes,"utf-8");
            System.out.println("服务器收到的消息："+express);

            //向客户端发送消息；
            doWrite(msg);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }

    private void doWrite(String msg)
    {

        byte[] bytes = msg.getBytes();
        //创建字节缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
        //将数据添加到缓冲区
        buffer.put(bytes);
        buffer.flip();
        channel.write(buffer, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                //如果没有发送完，就继续发送直到完成
                if(buffer.hasRemaining())
                    channel.write(buffer,buffer,this);
                else {
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);              channel.read(byteBuffer,byteBuffer,new ServerReadHandler(channel));
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void failed(Throwable exc, ByteBuffer attachment) {
        try {
            this.channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
