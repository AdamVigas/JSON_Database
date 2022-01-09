package server;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    private final int PORT = 34522;
    private ServerSocket serverSocket;
    Database data;
    Application app = new Application();

    public Server() {
        this.data = new Database();
        try {
            this.serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run()   {
        System.out.println("Server started!");
        while (!serverSocket.isClosed()) {
            try {
                Session session = new Session(serverSocket.accept());
                session.start();
            } catch (IOException e) {}
        }
    }
    class Session extends Thread {
        private final Socket socket;

        public Session(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (
                    DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                    DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            ) {
                while (!socket.isClosed()) {
                    //get 1 or set 1 helllo
                    String str = inputStream.readUTF();
                    System.out.println("Received " + str);
                    String result = app.jsonMenu(str,data);
                    if(result.equals("exit")) {
                        result = "{\"response\":\"OK\"}";
                        outputStream.writeUTF(result);
                        socket.close();
                        serverSocket.close();
                    }else {
                        outputStream.writeUTF(result);
                        socket.close();
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
