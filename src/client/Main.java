package client;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.gson.Gson;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;


public class Main {

    private static Socket clientSocket;

    @Parameter(names = {"--type", "-t"})
    String request;
    @Parameter(names = {"--index", "-k"})
    String index;
    @Parameter(names = {"--modify", "-v"})
    String data;
    @Parameter(names = {"--input", "-in"}, description = "file name")
    String fileName;

    public static void main(final String[] args) {

        Main main = new Main();
        JCommander.newBuilder()
                .addObject(main)
                .build()
                .parse(args);

        main.run();

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
            String fileTmp = null;
            Gson gson = new Gson();
            String tmp = null;
            if(fileName == null) {
                //toJson
                Map<String, String> temp = new LinkedHashMap<>();
                temp.put("type",request);
                temp.put("key", index);
                temp.put("value", data);
                tmp = gson.toJson(temp);
            }else {
                try {
                    File file = new File("C:\\Users\\Adam\\IdeaProjects\\JSON Database\\JSON Database\\task\\src\\client\\data\\" + fileName);
                    Scanner scanner = new Scanner(file);
                    tmp = scanner.nextLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }



            output.writeUTF(tmp);
            System.out.println("Sent: " + tmp);


            // Getting answer from server
            String receivedMessage = input.readUTF();
            System.out.println("Received: " + receivedMessage);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}