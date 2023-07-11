package lk.ijse.chatApp.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable{

    public static final List<ClientHandler> clientHandlerList = new ArrayList<>();
    private final Socket socket;
    private final DataInputStream inputStream; //Read data from source
    private final DataOutputStream outputStream; //Write data to destination
    private final String clientName;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());
        clientName = inputStream.readUTF();
        clientHandlerList.add(this);
    }

    public static void broadcast(String msg) throws IOException {
        for (ClientHandler handler : clientHandlerList) {
            handler.sendMessage("SERVER", msg);
        }
    }

    public void sendMessage(String sender, String msg) throws IOException {
        outputStream.writeUTF(sender + ": " + msg); //write data
        outputStream.flush();
    }


    @Override
    public void run() {
        l1: while (socket.isConnected()) {
            try {
                String utf = inputStream.readUTF(); //UTF-8 encoded string from the dataInputStream.
                if (utf.equals("*image*")) {
                //    receiveImage();
                } else {
                    for (ClientHandler handler : clientHandlerList) {
                        if (!handler.clientName.equals(clientName)) {
                            handler.sendMessage(clientName, utf);
                        }
                    }
                }
            } catch (IOException e) {

                clientHandlerList.remove(this); //remove client
                break;
            }
        }
    }




}
