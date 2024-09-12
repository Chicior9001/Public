package com.nauka.Kolokwium2;

import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class TokenService {
    private static final String PATH = "E:\\Studia\\Semestr II\\Programowanie obiektowe\\Nauka\\Kolokwium 2\\Kolokwium-2\\src\\main\\resources\\tokens.txt";
    private static final List<Token> tokens = new ArrayList<>();

    public TokenService() throws IOException {
        readTokensFromFile();
    }

    // Generowanie unikalnego tokena UUID
    public Token generateToken() throws IOException {
        String tokenID = UUID.randomUUID().toString();
        Token token = new Token(tokenID, LocalDateTime.now());
        tokens.add(token);
        saveTokenToFile(token);
        return token;
    }

    // Zwraca listę wygenerowanych tokenów
    public static List<Token> getTokens() {
        return tokens;
    }

    public static boolean isTokenActive(String token) {
        for (Token t : tokens) {
            if(Objects.equals(t.token(), token) && t.isActive()) return true;
        }
        return false;
        // return true;//tokens.contains(token) && token.isActive();
    }

    public static void deactivateToken(String token) {
        for (Token t : tokens) {
            if(Objects.equals(t.token(), token) && t.isActive()) t.deactivate();
        }
    }

    // Zapisywanie tokena do pliku
    private void saveTokenToFile(Token token) throws IOException {
        File file = new File(PATH);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
        writer.write(token.token() + " " + token.creationTime() + "\r\n");
        writer.close();
    }

    // Wczytanie tokenów z pliku do pamięci
    private final void readTokensFromFile() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(PATH));
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(" ");
            Token token = new Token(parts[0], LocalDateTime.parse(parts[1]));
            token.deactivate();
            tokens.add(token);
            // tokens.add(parts.length == 2 ? new Token(parts[0], LocalDateTime.parse(parts[1])) : null);
        }
    }
}
