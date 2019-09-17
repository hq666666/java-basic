package com.person.IO.NIO.version2016;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 步骤：
 *     01：打开ServerSocketChannel，监听客户端连接
 *    02：绑定监听端口，设置连接为非阻塞模式
 *    03：创建Reactor线程，创建多路复用器并启动线程
 *    04：将ServerSocketChannel注册到Reactor线程中的Selector上，监听ACCEPT事件
 *    05：Selector轮询准备就绪的key
 *    06：Selector监听到新的客户端接入，处理新的接入请求，完成TCP三次握手，简历物理链路
 *    07： 设置客户端链路为非阻塞模式
 *    08：将新接入的客户端连接注册到Reactor线程的Selector上，监听读操作，读取客户端发送的网络消息
 *    09:异步读取客户端消息到缓冲区
 *    10:对Buffer编解码，处理半包消息，将解码成功的消息封装成Task
 *    11: 将应答消息编码为Buffer，调用SocketChannel的write将消息异步发送给客户端
 */
public class NIOServerHandler implements  Runnable {

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private volatile boolean started;

    public NIOServerHandler(int port){
        try {
            //创建选择器
            selector = Selector.open();
            //打开监听通道
            serverSocketChannel = ServerSocketChannel.open();
            /**
             * 设置通道的阻塞模式：
             *
             *          true：开启阻塞模式
             *          false：开启非阻塞模式
             */
            serverSocketChannel.configureBlocking(false);
            //绑定端口并设置挂起等待的最大的连接数
            serverSocketChannel.socket().bind(new InetSocketAddress(port),1024);
            /**
             *  01：该Channel通过指定selector进行注册，并通过SelectionKey.OP_ACCEPT表示该通道操作标识；
             *              SelectionKey.OP_ACCEPT：表示用于套接字接受操作；
             *  02：返回值为：SelectionKey
             *              表示在select注册的channel中的一个标识(即一个SelectionKey对应一个channel)
             */
            SelectionKey key = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("监听通道的主键："+key);
            //标记服务器已开启
             started =true;
            System.out.println("服务器已启动，端口号："+port);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            //终止虚拟机的运行，非零状态下异常终止；
            System.exit(1);
        }
    }
    public void stop(){
        //服务关闭
        started= false;
    }
    @Override
    public void run() {
        //监听服务是否开启执行以下任务
        while(started){
            try {
                /**
                 * selector(多路复用器)通过select进行轮询在该select注册的Channel，若某个channel发生IO操作，则会被轮询出来
                 *      参数：
                 *          表示表示阻塞time，单位millions;
                 *          作用：每个指定的时间轮询一次，若为0，则无限轮询
                 */
                selector.select(1000);
                /**
                 * selector(多路复用器)获取在该select注册的channel(发生IO操作)的集合
                 */
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                SelectionKey key = null;
                /**
                 * 注意：以下的操作
                 *      业务逻辑：
                 *              就是将要Set<SelectionKey>集合中SelectionKey进行处理，处理完之后进行清空；
                 *
                 *      01：先声明一个变量，将获取的SelectionKey(即代表着一个selector)赋值给这个变量；
                 *      02：将SelectionKey的集合清空；
                 *      03：将变量的用于下面的操作
                 *    以上步骤的好处：
                 *          将从集合中获取的数据转移到另外一个容器中(变量)，进行集合清除元素，然后可以通过之前以存储数据容器进行使用，以致于方便阅读，且具有复用性；
                 */
                while (iterator.hasNext()){
                      key =iterator.next();
                      iterator.remove();
                    try {
                        handleInput(key);
                    } catch (Exception e) {
                        if(null !=key){
                            //进行取消操作
                            key.cancel();
                            //关闭该通道资源
                            if(key.channel() != null){
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(selector != null){
            try {
                //关闭selector资源
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        //通过isValid进行验证该SelectionKey是否有效
        if(key.isValid()){
            //通过isAcceptable表示是否接受新套接字连接
            if(key.isAcceptable()){
                //创建ServerSocketChannel实例
                ServerSocketChannel channel = (ServerSocketChannel)key.channel();
                //创建SocketChannel实例
                //完成该操作意味着完成TCP三次握手，TCP物理链路正式建立
                SocketChannel sc = channel.accept();
                //设置该Channel非阻塞模式
                sc.configureBlocking(false);
                /**
                 * 该Channel通过指定selector进行注册，并设置该Channel的进行的操作标识；
                 *      SelectionKey.OP_READ ：用于读取操作的操作；
                 */
                sc.register(selector,SelectionKey.OP_READ);
            }
           //通过 isReadable测试此key的通道是否已准备好读取
            if(key.isReadable()){
                //创建Channel
                SocketChannel sc =(SocketChannel) key.channel();
                //创新一个字节缓冲区,并设置容量为1KB
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(buffer);
                if(readBytes>0){
                    //将缓冲区当前的limit(限制)设置为position=0，用于后续对缓冲区的读取操作
                    buffer.flip();
                    //根据缓冲区可读字节数创建字节数组
                    byte[] bytes = new byte[buffer.remaining()];
                    //将缓冲区可读字节数组复制到新建的数组中
                    buffer.get(bytes);
                    String expressing = new String(bytes,"utf-8");
                    System.out.println("服务器收到消息："+expressing);
                    doWrite(sc,expressing);
                }else if(readBytes<0){
                    key.channel();
                    sc.close();
                }
            }
        }
    }

    private void doWrite(SocketChannel sc, String response) throws IOException {
        byte[] bytes = response.getBytes();
        //创建字节缓冲区
        ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
        //将数据添加到字节缓冲区
        writeBuffer.put(bytes);
        //flip操作
        writeBuffer.flip();
        //发送缓冲区字节数组
        sc.write(writeBuffer);
    }
}
