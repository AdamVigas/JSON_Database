package server;

import java.io.*;
import java.net.Socket;


class Session extends Thread {
    private final Socket socket;

    public Session(Socket socketForClient) {
        this.socket = socketForClient;
    }

    public void run() {
        try (
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {

            String msg = input.readUTF();
            System.out.println("Received: Give me a record # "+ msg);
            output.writeUTF(msg);
            System.out.println("Sent: A record # "+ msg+" was sent!");
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}