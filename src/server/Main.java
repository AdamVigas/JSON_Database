package server;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;


public class Main {
    public static void main(String[] args) {
        String address = "127.0.0.1";
        final int PORT = 23456;
        try (ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(address));) {
            System.out.println("Server started!");

            Session session = new Session(server.accept());
            session.start(); // it does not block this thread

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}