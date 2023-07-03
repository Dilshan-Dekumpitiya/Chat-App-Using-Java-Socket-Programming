package lk.ijse.chatApp.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{ //Singleton design pattern

    private static Server server;
    private final ServerSocket serverSocket;


    private Server() throws IOException {
        final int PORT=5000;
        serverSocket=new ServerSocket(PORT); //create server socket with port (To get connections)
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
                Socket accepted = serverSocket.accept(); //wait for request (Accept request of client (Listening)
                ClientHandler clientHandler = new ClientHandler(accepted);
                Thread thread = new Thread(clientHandler);
                thread.start();
                System.out.println("Client Connected!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
