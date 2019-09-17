package com.person.IO;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class IOClinet {


    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1",5656);

        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream());
        Scanner scanner = new Scanner(System.in);
        System.out.println("发送消息内容");
        String send = scanner.nextLine();
        System.out.println("客户端发送的消息："+send);
        out.writeUTF(send);
        out.flush();
        String result = in.readUTF();
        System.out.println("客户端收到的消息："+result);
        in.close();
        out.close();
        socket.close();
    }



}
