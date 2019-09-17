package com.person.IO;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class IOServer {

    private static int DEFAULT_PORT = 5656;
    private static ServerSocket serverSocket = null;

    public static void main(String[] args) throws IOException {
     serverSocket = new ServerSocket(DEFAULT_PORT);
        Socket socket = serverSocket.accept();
        DataInputStream reader = new DataInputStream(socket.getInputStream());
        DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
        String accept = reader.readUTF();
        System.out.println("服务器收到的消息："+accept);
        Scanner scanner = new Scanner(System.in);
        String send = scanner.nextLine();
        System.out.println("服务器的发送的消息："+send);
        writer.writeUTF(send);
        writer.flush();
        reader.close();
        writer.close();
        socket.close();
        serverSocket.close();
    }
}
