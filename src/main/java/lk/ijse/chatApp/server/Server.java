package lk.ijse.chatApp.server;

import lk.ijse.chatApp.client.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{
    //singleton
    private static Server server;
    private final ServerSocket serverSocket;


    private Server() throws IOException {
        final int PORT=3000; //Port --> 3000
        serverSocket = new ServerSocket(PORT);
        System.out.println("Server up & running on port : "+PORT);
    }

    public static Server getServerSocket() throws IOException {
        return server == null ? server = new Server() : server;
    }

    @Override
    public void run() {
        while (!serverSocket.isClosed()) {
            System.out.println("listening.......");
            try {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
