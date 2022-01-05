package client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        final String SERVER_ADDRESS = "127.0.0.1";
        final int SERVER_PORT = 23456;
        try (
                Socket socket = new Socket(InetAddress.getByName(SERVER_ADDRESS), SERVER_PORT);

                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output  = new DataOutputStream(socket.getOutputStream())
        ) {
            System.out.println("Client started!");
            Scanner scanner = new Scanner(System.in);
            //TODO je tu len cislo tri...tu to treba zmenit na input
            String msg = "3";
            output.writeUTF(msg); // sending message to the server
            System.out.println("Sent: Give me a record # "+ msg);
            String receivedMsg = input.readUTF(); // response message
            System.out.println("Received: A record # "+ receivedMsg+" was sent!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
