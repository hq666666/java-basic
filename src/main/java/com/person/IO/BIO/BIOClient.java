package com.person.IO.BIO;

import java.io.*;
import java.net.Socket;

public class BIOClient {
    
    private static int DEFAULT_PORT = 12345;
    
    private static String DEFAULT_ADDRESS = "127.0.0.1";
    
    public static void send(String express) throws IOException {
        send(DEFAULT_PORT,express);
    }

    private static void send(int defaultPort, String express) throws IOException {

        System.out.println("算术表达式为：" + express);
        Socket socket = null;
        DataInputStream reader = null;
        DataOutputStream writer = null;

        try {
            socket = new Socket(DEFAULT_ADDRESS,defaultPort);
            reader = new DataInputStream(socket.getInputStream());
            writer = new DataOutputStream(socket.getOutputStream());
            //发送消息
            writer.writeUTF(express);
            //接受消息
            System.out.println("结果为："+reader.readInt());
        } catch (IOException e) {
            System.out.println("异常信息："+e.getMessage());
        }finally {
            //释放资源
            reader.close();
            writer.close();
            socket.close();
            reader = null;
            writer = null;
            socket = null;
        }

    }
}
