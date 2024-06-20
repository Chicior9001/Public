package org.example.client;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner scanner;
        String username;
        String path;
        scanner = new Scanner(System.in);


        System.out.println("Gimme that username:");
        username = scanner.nextLine();

        System.out.println("Gimme that path:"); // D:\Studia\Semestr II\Programowanie obiektowe\Projects\eeg\eegpliki\tm01.csv
        path = scanner.nextLine();

        Client client = new Client();
        client.sendData(username, path);

    }

    public void sendData(String username, String path) {
        try {
            Socket socket = new Socket("localhost", 2137);
            PrintWriter printWriter = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()), true); // tworzy połączenie do socketa przez nakładkę PrinterWriter
            //BufferedReader reader = new BufferedReader(new FileReader("D:\\Studia\\Semestr II\\Programowanie obiektowe\\Projects\\eeg\\data.txt"));
            printWriter.println(username); // wysyła username na serwer
            Files.lines(Path.of(path)).forEach(line -> { // dla każdej linii pobranej z pliku o ścieżce path
                printWriter.println(line); // wyslij tą linie na serwer
                try {
                    Thread.sleep(200); // zaczekaj ileś milisekund 1000ms = 1s
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });

            socket.close(); // zamknij socket
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}