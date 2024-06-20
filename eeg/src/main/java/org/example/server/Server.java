package org.example.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocket ss;
    private List<Client> clients = new ArrayList<>();

    public Server() throws IOException {
        ss = new ServerSocket(2137);
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.listen();
    }

    public void listen() throws IOException {
        while(true) {
            Socket socket = ss.accept(); // tu przyjmuje dane od klientów
            Client client = new Client(socket);
            new Thread(client).start(); // tu wtedy startuje obiekt klasy Client (musi on być Runnable)
            clients.add(client); // dodaj obiekt klasy do listy klientów
        }
    }
}