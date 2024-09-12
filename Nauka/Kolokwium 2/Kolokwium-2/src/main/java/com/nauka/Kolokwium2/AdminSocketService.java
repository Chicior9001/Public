package com.nauka.Kolokwium2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class AdminSocketService {
    private static final String ADMIN_PASSWORD = "123"; // hasło admina
    private ServerSocket serverSocket;
    private Socket adminSocket;
    private boolean isRunning = true;

    public AdminSocketService(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("AdminSocket listens on port: " + port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() {
        new Thread(() -> {
            while (isRunning) {
                try {
                    System.out.println("Waiting for admin to connect...");
                    adminSocket = serverSocket.accept();
                    System.out.println("Admin connected!");

                    BufferedReader in = new BufferedReader(new InputStreamReader(adminSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(adminSocket.getOutputStream(), true);

                    out.println("Enter your admin password:");
                    String receivedPassword = in.readLine();

                    if (receivedPassword.equals(ADMIN_PASSWORD)) {
                        out.println("Connection successful, welcome admin");
                        handleAdminCommands(in, out);
                    } else {
                        out.println("Wrong password. Disconnecting...");
                        adminSocket.close();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private void handleAdminCommands(BufferedReader in, PrintWriter out) {
        try {
            String command;
            while((command = in.readLine()) != null) {
                if (command.equalsIgnoreCase("quit")) {
                    out.println("Disconnecting admin...");
                    adminSocket.close();  // Rozłącz administratora
                    break;  // Przerywamy pętlę, co kończy komunikację
                } else if (command.startsWith("ban ")) {
                    String tokenToBan = command.substring(4).trim();
                    int deletedRecords = banToken(tokenToBan);  // Funkcja do banu i usunięcia danych
                    out.println("Token " + tokenToBan + " has been banned. Deleted " + deletedRecords + " records.");
                } else if (command.equalsIgnoreCase("video")) {
                    out.println("Generating video...");
                    generateVideo(out);  // Uruchomienie generowania wideo
                    out.println("Video generated!.");
                } else {
                    // Tutaj obsługa innych komend (np. ban, video, etc.)
                    out.println("Received command: " + command);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            // Zamknięcie zasobów po zakończeniu sesji
            try {
                if (adminSocket != null && !adminSocket.isClosed()) {
                    adminSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Admin has been disconnected.");
        }
    }

    private void generateVideo(PrintWriter out) {
        try {
            // Komenda FFmpeg do generowania wideo
            // String command = "ffmpeg -framerate 30 -pattern_type glob -i E:\\Studia\\Semestr II\\Programowanie obiektowe\\Nauka\\Kolokwium 2\\Kolokwium-2\\src\\main\\resources\\Frames\\image_%d.png -c:v libx265 E:\\Studia\\Semestr II\\Programowanie obiektowe\\Nauka\\Kolokwium 2\\Kolokwium-2\\src\\main\\resources\\out.mp4";
            String command = "C:\\ffmpeg\\bin\\ffmpeg -framerate 30 -i C:\\Frames\\image_%d.png -c:v libx265 C:\\out.mp4";

            Process process = Runtime.getRuntime().exec(command);

            // Oczekuj na zakończenie procesu
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                out.println("Video has been generated as 'out.mp4'.");
                // Usuń pliki PNG po wygenerowaniu wideo
                deletePngFiles();
            } else {
                out.println("Error. Video could not be generated.");
            }
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void deletePngFiles() {
        File catalog = new File("E:\\Studia\\Semestr II\\Programowanie obiektowe\\Nauka\\Kolokwium 2\\Kolokwium-2\\src\\main\\resources\\Frames");
        for (File file : catalog.listFiles()) {
            if (file.getName().startsWith("image_") && file.getName().endsWith(".png")) {
                file.delete();
            }
        }
    }

    private int banToken(String token) {
        // dezaktywuj token
        TokenService.deactivateToken(token);

        // usuń go z bazy
        int deletedRecords = Database.deleteEntry(token);

        // zregeneruj obraz
        ImageController.loadImage();

        return deletedRecords;
    }

    public void stop() {
        isRunning = false;
        try {
            if (adminSocket != null) {
                adminSocket.close();
            }
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
