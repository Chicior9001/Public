package org.example.webserver;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket ss;

    public Server() throws IOException {
        ss = new ServerSocket(2137);
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.listen();
    }

    public void listen() throws IOException {
        while(true) {
            Socket socket = ss.accept();
        }
    }
}