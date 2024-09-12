package lab11.laboratoria12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable{
    Socket socket;
    BufferedReader in;
    PrintWriter out;
    String login;
    boolean isRunning = true;
    HelloController controller;

    public Client() throws IOException {
        socket = new Socket("localhost", 8080);
        System.out.println("Connected to the server.");
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void setController(HelloController controller) {
        this.controller = controller;
    }

    String clientMessage = "";
    public void send(String message) {
        out.println(message);
    }

    @Override
    public void run() {
        new Thread(() -> {
            while (isRunning) {
                // pobiera z konsoli
                /*BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

                while(!clientMessage.isEmpty()) {
                    if(clientMessage.equals("/quit")) {
                        stop();
                    }
                    // out.println(clientMessage);
                    out.println(clientMessage);
                }*/
            }
        }).start();

        String serverMessage;
        try {
            while(isRunning) {
                if (((serverMessage = in.readLine()) != null)) {
                    System.out.println(serverMessage);
                    controller.receive(serverMessage);
                }
            }
            stop();
        } catch (IOException e) {
            throw new RuntimeException(e);
            }
    }

    public void stop() {
        isRunning = false;
        try {
            if(socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
