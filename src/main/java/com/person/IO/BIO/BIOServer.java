package com.person.IO.BIO;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BIOServer {

    private static int DEFAULT_PORT = 12345;
    private static ServerSocket server  =null;

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
                new Thread(new ServerHandler(socket)).start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }finally {
            if(null !=server){
                System.out.println("服务器已关闭");
                server.close();
                server = null;
            }
        }


    }

}
