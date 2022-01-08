package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;


public class Main {

    private static Socket clientSocket;

    @Parameter(names = {"--type", "-t"})
    String request;
    @Parameter(names = {"--index", "-i"})
    String index;
    @Parameter(names = {"--modify", "-m"})
    String data;

    public static void main(final String[] args) {

        Main main = new Main();
        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(args);

        main.run();

    }

    private static void createSocket() {
        final String address = "127.0.0.1";
        final int port = 23456;
        while (true) {
            try {
                clientSocket = new Socket(InetAddress.getByName(address), port);
                return;
            } catch (Exception e) {
                System.out.println("\n" + e + "\n[CLIENT] Can't connect to the server");
            }

        }
    }

    private static void closeSocket() {
        try {
            clientSocket.close();
        } catch (Exception ignored) {
        }
    }

    public void run() {
        final String SERVER_ADDRESS = "127.0.0.1";
        final int SERVER_PORT = 34522;
        try (
                Socket socket = new Socket(InetAddress.getByName(SERVER_ADDRESS), SERVER_PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output  = new DataOutputStream(socket.getOutputStream())
        ) {
            System.out.println("Client started!");
            // Sending a request to the server
            final String message = request + " " + (index!=null ? index : "")
                    + " " + (data!=null ? data : "");
            output.writeUTF(message);
            System.out.println("Sent: " + message.trim());


            // Getting answer from server
            String receivedMessage = input.readUTF().trim();
            System.out.println("Received: " + receivedMessage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}