package com.person.IO.BIO;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 使用线程池控制并发量与重复线程创建销毁从而导致的系统资源的浪费；
 */
public class BIOServer02 {

    private static int DEFAULT_PORT = 12345;
    private static ServerSocket server  =null;
    private static ExecutorService service = Executors.newFixedThreadPool(8);
    public static void start() throws IOException {
        start(DEFAULT_PORT);
    }

    private synchronized static void start(int defaultPort) throws IOException {

        if(server !=null) return;

        try {
            server = new ServerSocket(defaultPort);
            System.out.println("服务器已启动，端口号："+defaultPort);
            //无限建立连接
            while(true) {
                Socket socket = server.accept();
                //执行任务
                Future<?> submit = service.submit(new ServerHandler(socket));
                System.out.println(submit.get());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            if(null !=service){
                service.shutdown();
                service =null;
            }
            if(null !=server){
                System.out.println("服务器已关闭");
                server.close();
                server = null;
            }
        }


    }
}
