package com.person.IO.NIO.version2018;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class NioSeverHandler implements Runnable {
    private SelectionKey key;

    public NioSeverHandler(SelectionKey key){
        this.key =key;
    }
    @Override
    public void run() {
        if(key.isValid()){
            if(key.isReadable()){
                SocketChannel sc = null;
                ByteBuffer buffer = null;
                try {
                    //创建SocketChannel实例
                    sc = (SocketChannel) key.channel();
                    //创建ByteBuffer缓冲区，且容量为1KB
                    buffer = ByteBuffer.allocate(1024);
                    sc.read(buffer);
                    buffer.flip();
                    while(buffer.hasRemaining()){

                        System.out.print("读取到的数值："+(char)buffer.get()+"\t");
                    }
                    /**
                     * 就是将写模式转换成读模式，会将当前的position(位置也可以直接为索引)设置为0，这是使用read会从这个位置往前读取数据
                     */
                    buffer.flip();
                    System.out.println("收到客户端"+sc.socket().getInetAddress().getHostName()+"的数据："+new String(buffer.array()));
                    //将字节数组封装到缓冲区中
                    ByteBuffer outBuffer = ByteBuffer.wrap(buffer.array());
                    outBuffer.flip();
                    //将消息回送给客户端
                    sc.write(outBuffer);

                } catch (IOException e) {
                    e.printStackTrace();
                }  finally {
                    if(null != key){
                        key.cancel();
                    }
                }

                }

        }

    }
}
