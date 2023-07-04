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
    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;
    private final String clientName;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        inputStream = new DataInputStream(socket.getInputStream());
        outputStream = new DataOutputStream(socket.getOutputStream());
        clientName = inputStream.readUTF();
        clientHandlerList.add(this);
    }

    @Override
    public void run() {
        l1: while (socket.isConnected()) {
            try {
                String utf = inputStream.readUTF();
                if (utf.equals("*image*")) {
                //    receiveImage();
                } else {
                    for (ClientHandler handler : clientHandlerList) {
                        if (!handler.clientName.equals(clientName)) {
                         //   handler.sendMessage(clientName, utf);
                        }
                    }
                }
            } catch (IOException e) {

                clientHandlerList.remove(this);
//                System.out.println(clientName+" removed");
//                System.out.println(clientHandlerList.size());
                break;
            }
        }
    }




}
