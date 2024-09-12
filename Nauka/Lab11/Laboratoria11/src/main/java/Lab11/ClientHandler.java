package Lab11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String login;

    public String getLogin() {
        return login;
    }

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    private void parseMessage(String message) throws IOException {
        if(message.equals("/online")) {
            out.println("Currently online:");
            for(ClientHandler client : ChatServer.currentlyLogged()) {
                out.println("\t" + client.getLogin());
            }
        } else if(message.startsWith("/w ")) {
            String[] parts = message.split(" ", 3);
            ChatServer.privateMessage(parts[2], parts[1], this);
        } else if(!message.isEmpty()) {
            ChatServer.broadcastMessage(message, this);
        }
    }

    public void leave() throws IOException {
        ChatServer.clientLeft(this);
        System.out.println(login + " left the server");
        in.close();
        out.close();
        socket.close();
    }

    @Override
    public void run() {
        try {
            // wejście/wyjście
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // login i ogłoszenie
            out.println("Enter login:");
            login = in.readLine();
            out.println("Logged in.");
            System.out.println(login + " entered the server.");

            ChatServer.broadcastMessage(" joined the chat.", this);


            String clientMessage;
            while((clientMessage = in.readLine()) != null) {
                System.out.println(login + " sent a message: " + clientMessage);
                parseMessage(clientMessage);
            }
            this.leave();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
