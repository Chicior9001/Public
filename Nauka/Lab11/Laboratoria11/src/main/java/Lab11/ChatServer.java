package Lab11;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    private static ServerSocket serverSocket;
    private static List<ClientHandler> clients = new ArrayList<>();
    private static boolean isRunning;

    public ChatServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        isRunning = true;
        System.out.println("Server started at port " + port);
    }

    public void start() throws IOException {
        while(isRunning) {
            Socket socket = serverSocket.accept();
            ClientHandler client = new ClientHandler(socket);
            new Thread(client).start();
            clients.add(client);
        }
    }

    public static void broadcastMessage(String message, ClientHandler excludedClient) throws IOException {
        for(ClientHandler client : clients) {
            if(!client.equals(excludedClient)) {
                client.sendMessage(excludedClient.getLogin() + ": " + message);
            }
        }
    }

    public static void clientLeft(ClientHandler leavingClient) throws IOException {
        clients.remove(leavingClient);
        for(ClientHandler client : clients) {
            if(!client.equals(leavingClient)) {
                client.sendMessage(leavingClient.getLogin() + " has logged out.");
            }
        }
    }

    public static List<ClientHandler> currentlyLogged() {
        return clients;
    }

    public static void privateMessage(String message, String recipient, ClientHandler sender) throws IOException {
        boolean found = false;
        for(ClientHandler client : clients) {
            if(client.getLogin().equalsIgnoreCase(recipient)) {
                client.sendMessage("(private)" + sender.getLogin() + ": " + message);
                found = true;
            }
        }
        if(!found) {
            sender.sendMessage(recipient + " is offline");
        }
    }

    public void stop() throws IOException {
        isRunning = false;
        serverSocket.close();
    }

}
