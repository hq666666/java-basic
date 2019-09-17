package com.person.IO.AIO;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class ServerAcceptHandler implements CompletionHandler<AsynchronousSocketChannel,AsyncServerHandler> {
    @Override
    public void completed(AsynchronousSocketChannel result, AsyncServerHandler attachment) {

        AIOServer.clientCount++;
        System.out.println("连接的客户端数量："+AIOServer.clientCount);
        attachment.channel.accept(attachment,this);
        //创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        /**
         *
         * 异步读  第三个参数为接收消息回调的业务Handler
         * 将获取的数据放入缓冲区中：
         *          参数：
         *             01：要存数据的缓冲区；
         *             02：连接到I/O操作的对象;可以是{@code null}
         *             03：用于消费结果的处理程序
         */

        result.read(buffer,buffer,new ServerReadHandler(result));
    }

    @Override
    public void failed(Throwable exc, AsyncServerHandler attachment) {
                    exc.printStackTrace();
                    attachment.latch.countDown();
    }
}
